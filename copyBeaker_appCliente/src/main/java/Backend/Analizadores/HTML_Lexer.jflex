//Configuración
package Backend.Analizadores;
import java_cup.runtime.*;
import Backend.Objetos.Token;
import static Backend.Analizadores.HTML_ParserSym.*;

%%
//Código de usuario
%class HTML_Lexer
%unicode
%line
%column
%public
//%standalone
//%int
%caseless
%cupsym HTML_ParserSym
%cup //recuerda reemplazarlo por %cup, al hacer eso tampoco se hace nec add el %int...

//macros para identificador
digito = [0-9]
letra = [a-zA-Z\u00f1\u00d1]//recuerda que estos últimos 2 son caracteres de escape del formato unicode...

numero = {digito}+

variable = ("$"|{letra})|("_"|"$"|{letra})("_"|"$"|{letra}|{digito})+
inicioIdentificador = "$"{espacioEnBlanco}*"$"{espacioEnBlanco}*"("
finIdentificador = ")"{espacioEnBlanco}*"$"{espacioEnBlanco}*"$"

//macros para formar ER de etiquetas...
etiquetas = "html"|"h1"|"h2"|"table"|"tr"|"th"|"td"
etiquetasApertura = {etiquetas}|"br"
etiquetasCierre = {etiquetas}|"for"

//etiquetas
etiquetaApertura = "<"{espacioEnBlanco}*{etiquetasApertura}{espacioEnBlanco}*">"
etiquetaCierre = "</"{espacioEnBlanco}*{etiquetasCierre}{espacioEnBlanco}*">"
//etiqueta for
inicioEtiquetaFor = "<"{espacioEnBlanco}*"for"

//macros auxiliares
finDeLinea = \r|\n|\r\n
tabulacion = [ \t\f]
espacioEnBlanco = {finDeLinea} | {tabulacion}

//matros para comentarios
ComentarioMultiLinea = {comentarioTradicional} | {comentarioDocumentacion}

comentarioTradicional   = "</" [^*] ~"/>" | "</" "/"+ ">"  
    
comentarioDocumentacion = "<//" {contenidoComentario} "/"+ ">"
contenidoComentario = ( [^*] | "/"+ [^"</"] )*

//cuerpoComentario = [^\r\n]
//comentario = "</" {cuerpoComentario}* {finDeLinea}?"/>"
//no es porque esté malo sino porque es multilínea el comentario solicitado xD

%{          
    boolean etiquetaAntApetura = false;//Esta solo podrá ser cambiada por las eti de cierre o apertura, no por $$()$$, en el caso de la etiqueta for, la podrá modificar hasta que se haya corroborado que todo está bien con el for... xD
    int estadoEntrada = -5;//para evitar que haga matchs que no corresponden...
    
    //aquí no se requiere del manejador de errores, puesto que todo lo que no sea palabras reservadas y esté dentro de dos tag de ini y fin o esté fuer [antes de una tag de ini], lo toma como cont int o ext respectivamente xD

    boolean requeriaCompania = false;
    Token tokenAnterior = null;

    StringBuffer contenido = new StringBuffer();
  
    private Symbol symbol(int tipo, String valor, boolean conCompania){//Dejé el valor como string, como no requiero que alguno de los lexemas sea diferente...
        Token tokenActual = new Token ((yyline+1), (yycolumn+1), valor, (conCompania)?tokenAnterior:null);        

        if(tokenAnterior != null && requeriaCompania){
            tokenAnterior.setSiguiente(tokenActual);
        }

        tokenAnterior = tokenActual;
        requeriaCompania = conCompania;
        return new Symbol(tipo, yyline+1, yycolumn+1, tokenActual);
    }

     private Symbol accionComentario(){        
        String comentarioLleno = yytext().replaceAll("^\\s*","").toLowerCase();
        String cierres[] = new String[]{"</html>", "</h1>", "</h2>", "</table>", "</tr>", "</th>", "</td>", "</for>"};//no add a br, porque con ese o hya conflictos xD

        for(int actual = 0; actual < cierres.length; actual++){
            if(comentarioLleno.startsWith(cierres[actual])){
                System.out.println("[L] close label: "+ cierres[actual]);         
                yypushback((yylength()-(cierres[actual].length())));
                etiquetaAntApetura = false;//Aquí no importa si es la de HTML o no xD

                return symbol(((actual == 0)?HTML_C:((actual == 1)?H1_C:((actual == 2)?H2_C:
                ((actual == 3)?TABLE_C:((actual == 4)?TR_C:((actual == 5)?TH_C:
                ((actual == 6)?TD_C:FOR_C))))))), cierres[actual], false);
            }            
        }//quizá además de esto requiera hacer una eli en el string duplicado de esta parte, para eliminar los coment, conforme los vaya encontrando...

        System.out.println("[L] comentario: "+ yytext() + "\n");
        return symbol(COMENTARIOS, yytext(), false);
    }

    private Symbol acccionReservada(int tipo){
        System.out.println("[L] reservada: "+ yytext());                

        return symbol(tipo, yytext(), false);    
    }
   
    private Symbol axnEtiquetasAperturaEstandar(){    
        System.out.println("[L] open label: "+ yytext() +" "+ ((yytext().toLowerCase().contains("html"))?"HTML_A":((yytext().toLowerCase().contains("h1"))?
        "H1_A":((yytext().toLowerCase().contains("h2"))?"H2_A":((yytext().toLowerCase().contains("table"))?"TABLE_A":
        ((yytext().toLowerCase().contains("tr"))?"TR_A":((yytext().toLowerCase().contains("th"))?"TH_A":((yytext().toLowerCase().contains("td"))?"TD_A":"BR"))))))));        

        return symbol((((yytext().toLowerCase().contains("html"))?HTML_A:((yytext().toLowerCase().contains("h1"))?H1_A:
                       ((yytext().toLowerCase().contains("h2"))?H2_A:((yytext().toLowerCase().contains("table"))?TABLE_A:
                       ((yytext().toLowerCase().contains("tr"))?TR_A:((yytext().toLowerCase().contains("th"))?TH_A:
                       ((yytext().toLowerCase().contains("td"))?TD_A:BR)))))))), new String(yytext()), false);                
    }//en realidad BR creo que no debería setear a true la var en la que se almacena si antes hubo una eti de apertura, pero hasta donde sé y pienso, no provoca daños ni perjuicios xD

    private Symbol axnEtiquetasCierreEstandar(){
        System.out.println("[L] close label: "+ yytext() +" "+ ((yytext().toLowerCase().contains("html"))?"HTML_C":((yytext().toLowerCase().contains("h1"))?
        "H1_C":((yytext().toLowerCase().contains("h2"))?"H2_C":((yytext().toLowerCase().contains("table"))?"TABLE_C":
        ((yytext().toLowerCase().contains("tr"))?"TR_C":((yytext().toLowerCase().contains("th"))?"TH_C":((yytext().toLowerCase().contains("td"))?"TD_C":"FOR_C"))))))));   

        return symbol((((yytext().toLowerCase().contains("html"))?HTML_C:((yytext().toLowerCase().contains("h1"))?H1_C:
                       ((yytext().toLowerCase().contains("h2"))?H2_C:((yytext().toLowerCase().contains("table"))?TABLE_C:
                       ((yytext().toLowerCase().contains("tr"))?TR_C:((yytext().toLowerCase().contains("th"))?TH_C:
                       ((yytext().toLowerCase().contains("td"))?TD_C:FOR_C)))))))), new String(yytext()), false);                
    }

    private void accionProcesarError(){
        estadoEntrada = (yystate()!=ERROR)?yystate():estadoEntrada;//puesto que error no es un estado con el que deba trabajar el lenguaje, sino que es un aux, un detector xD, si no hacías esto se creaba un bucle infinito, xd [edscubierto en el alone xD]
        //  System.out.println((estadoEntrada == ERROR)?"ERROR":"ESTADO");
        
        if(yystate() != ERROR){
            contenido.setLength(0);//no provoca problemas el usar la variable string que tb usa SSTRING para concatenar, puesto que al estar en ese estado no se entrará aquí puesto que esta expre reg tiene la menor precedencia y las reglas de allá impiden que este caso suceda... puesto que se absorben todos los caracteres posibles hasta llegar a la otra "
            yypushback(yylength());//iba a colocar 1, en lugar de yylength pero no se si la unidad de medida varíe porque podría se que cada caracter tenga un tamaño diferente dependiendo de la cdad de bits que requiera para ser plasmado p.ej
            yybegin(ERROR);
        }else if(yystate() == ERROR){
            contenido.append(yytext());
        }
    }

%}

%state IDENTIFICADOR FOR ERROR

%%

//Reglas léxicas
<IDENTIFICADOR, FOR, ERROR> "RESULT"                {if(yystate() == ERROR){
                                                        if(estadoEntrada == IDENTIFICADOR || estadoEntrada == FOR){
                                                            yypushback(yylength());
                                                            yybegin(estadoEntrada);
                                                        }else{
                                                            contenido.append(yytext());
                                                        }
                                                    }else{
                                                        return acccionReservada(RESULT);
                                                    }}
<IDENTIFICADOR, FOR, ERROR> "Score"                 {if(yystate() == ERROR){
                                                        if(estadoEntrada == IDENTIFICADOR || estadoEntrada == FOR){
                                                            yypushback(yylength());
                                                            yybegin(estadoEntrada);
                                                        }else{
                                                            contenido.append(yytext());
                                                        }
                                                    }else{
                                                        return acccionReservada(SCORE);
                                                    }}    
<IDENTIFICADOR, FOR, ERROR> "Clases"                {if(yystate() == ERROR){
                                                        if(estadoEntrada == IDENTIFICADOR || estadoEntrada == FOR){
                                                            yypushback(yylength());
                                                            yybegin(estadoEntrada);
                                                        }else{
                                                            contenido.append(yytext());
                                                        }
                                                    }else{
                                                        return acccionReservada(CLASES);
                                                    }}  
<IDENTIFICADOR, FOR, ERROR> "Nombre"                {if(yystate() == ERROR){
                                                        if(estadoEntrada == IDENTIFICADOR || estadoEntrada == FOR){
                                                            yypushback(yylength());
                                                            yybegin(estadoEntrada);
                                                        }else{
                                                            contenido.append(yytext());
                                                        }
                                                    }else{
                                                        return acccionReservada(NOMBRE);
                                                    }}
<IDENTIFICADOR, FOR, ERROR> "Variables"            {if(yystate() == ERROR){
                                                        if(estadoEntrada == IDENTIFICADOR || estadoEntrada == FOR){
                                                            yypushback(yylength());
                                                            yybegin(estadoEntrada);
                                                        }else{
                                                            contenido.append(yytext());
                                                        }
                                                    }else{
                                                        return acccionReservada(VARIABLES);
                                                    }}   
<IDENTIFICADOR, FOR, ERROR> "Tipo"                  {if(yystate() == ERROR){
                                                        if(estadoEntrada == IDENTIFICADOR || estadoEntrada == FOR){
                                                            yypushback(yylength());
                                                            yybegin(estadoEntrada);
                                                        }else{
                                                            contenido.append(yytext());
                                                        }
                                                    }else{
                                                        return acccionReservada(TIPO);
                                                    }} 
<IDENTIFICADOR, FOR, ERROR> "Funcion"               {if(yystate() == ERROR){
                                                        if(estadoEntrada == IDENTIFICADOR || estadoEntrada == FOR){
                                                            yypushback(yylength());
                                                            yybegin(estadoEntrada);
                                                        }else{
                                                            contenido.append(yytext());
                                                        }
                                                    }else{
                                                        return acccionReservada(FUNCION);
                                                    }}     
<IDENTIFICADOR, FOR, ERROR> "Metodos"                {if(yystate() == ERROR){
                                                        if(estadoEntrada == IDENTIFICADOR || estadoEntrada == FOR){
                                                            yypushback(yylength());
                                                            yybegin(estadoEntrada);
                                                        }else{
                                                            contenido.append(yytext());
                                                        }
                                                    }else{
                                                        return acccionReservada(METODOS);
                                                    }}      
<IDENTIFICADOR, FOR, ERROR> "Parametros"            {if(yystate() == ERROR){
                                                        if(estadoEntrada == IDENTIFICADOR || estadoEntrada == FOR){
                                                            yypushback(yylength());
                                                            yybegin(estadoEntrada);
                                                        }else{
                                                            contenido.append(yytext());
                                                        }
                                                    }else{
                                                        return acccionReservada(PARAMETROS);
                                                    }}     
<IDENTIFICADOR, FOR, ERROR> "Comentarios"           {if(yystate() == ERROR){
                                                        if(estadoEntrada == IDENTIFICADOR || estadoEntrada == FOR){
                                                            yypushback(yylength());
                                                            yybegin(estadoEntrada);
                                                        }else{
                                                            contenido.append(yytext());
                                                        }
                                                    }else{
                                                        return acccionReservada(COMENTARIOS);
                                                    }}  
<IDENTIFICADOR, FOR, ERROR> "Texto"                 {if(yystate() == ERROR){
                                                        if(estadoEntrada == IDENTIFICADOR || estadoEntrada == FOR){
                                                            yypushback(yylength());
                                                            yybegin(estadoEntrada);
                                                        }else{
                                                            contenido.append(yytext());
                                                        }
                                                    }else{
                                                        return acccionReservada(TEXTO);
                                                    }}    
<FOR, ERROR> "Iterator"                             {if(yystate() == ERROR){
                                                        if(estadoEntrada == FOR){
                                                            yypushback(yylength());
                                                            yybegin(estadoEntrada);
                                                        }else{
                                                            contenido.append(yytext());
                                                        }
                                                    }else{
                                                        return acccionReservada(ITERATOR);
                                                    }}      
<FOR, ERROR> "Hasta"                                {if(yystate() == ERROR){
                                                        if(estadoEntrada == FOR){//estadoEntrada == IDENTIFICADOR, puesto que en realidad palabra reservada como tal solo dentro del estado léxico del for...
                                                            yypushback(yylength());
                                                            yybegin(estadoEntrada);
                                                        }else{
                                                            contenido.append(yytext());
                                                        }
                                                    }else{
                                                        return acccionReservada(HASTA);
                                                    }}

<YYINITIAL, ERROR>{
    
    {etiquetaApertura}                             {if(yystate() == ERROR){
                                                        yypushback(yylength());
                                                        yybegin(YYINITIAL);
                                                        if(etiquetaAntApetura){
                                                            System.out.println("[L] contenido in [aper]: "+ contenido.toString() + "\n");                                                        
                                                            return symbol(CONTENIDO, new String(contenido), false);     
                                                        }else{
                                                            System.out.println("[L] contenido ext [aper]: "+ contenido.toString() + "\n");                                                      
                                                        }                                                                                                                                                                       
                                                    }else{//es decir es = YYINITIAL, puesto que solo con estado error y YYINI, se puede entrar a esta sección
                                                      etiquetaAntApetura = ((yytext().toLowerCase().contains("html"))?false:true);//Siemore y cuando NO SEA LA DE HML
                                                      return axnEtiquetasAperturaEstandar();}}
    
    {inicioEtiquetaFor}                             {if(yystate() == ERROR){//puesto que aquí no se ha completado la etiqueta, no se puede hacer el envío del contenido, o si? solo lo que no se puede hacer es colocar el valor de la var que se mencionó, en false...
                                                      yypushback(yylength());
                                                      yybegin(YYINITIAL);
                                                      if(etiquetaAntApetura){//puesto que no importa si la etiqueta está bien formada, sino que se halló con un algo que corresp a una de apertura y eso le basta para hacer el respectivo retorno...
                                                           System.out.println("[L] contenido in [ini_for]: "+ contenido.toString() + "\n");                                                           
                                                           return symbol(CONTENIDO, new String(contenido), false);
                                                      }else{
                                                           System.out.println("[L] contenido ext [ini_for: "+ contenido.toString() + "\n");                                                      
                                                      }                                                         
                                                    }else{//es decir es = YYINITIAL, puesto que solo con estado error y YYINI, se puede entrar a esta sección
                                                        System.out.println("[L] inicio_FOR\n");                                                    
                                                        etiquetaAntApetura = true;//puesto que de no colocarlo, suponeindo que antes de la etiqueta de def de las var del for hubiera una etiqueta de cierre o nada, al leer el contenido, lo tomaría como contenido externo y eso no lo enviaría al parser y por lo tanto no se reportaría el error por la desinformación que éste último tendría...
                                                        yybegin(FOR);
                                                        return symbol(FOR_A, yytext(), false);}}//mejor los voy a enviar para evitar posibles incongruencias al existir errores, con tal que el parser tenga todos los token que se req para decir si es válida o no la entrada...
        
    {inicioIdentificador}                          {if(yystate() == ERROR){//puesto que aquí no se ha completado la etiqueta, no se puede hacer el envío del contenido, o si? solo lo que no se puede hacer es colocar el valor de la var que se mencionó, en false...
                                                      yypushback(yylength());
                                                      yybegin(YYINITIAL);
                                                      if(etiquetaAntApetura){//puesto que no importa si la etiqueta está bien formada, sino que se halló con un algo que corresp a una de apertura y eso le basta para hacer el respectivo retorno...
                                                           System.out.println("[L] contenido in [ini_ID]: "+ contenido.toString() + "\n");                                                           
                                                           return symbol(CONTENIDO, new String(contenido), false);
                                                      }else{
                                                           System.out.println("[L] contenido ext [ini_ID]: "+ contenido.toString() + "\n");                                                      
                                                      }                                                         
                                                    }else{//es decir es = YYINITIAL, puesto que solo con estado error y YYINI, se puede entrar a esta sección                                                      
                                                      //etiquetaAntApetura = true;//NO debe tener esto, pues podría estar en el exterior y hacer que todo lo que aparezca despés de esa etiqueta se tome como contini lo cula no es cierto y mejor dicho, en el exterior esta etiqueta no tendría por qué identificarse como un ID... sino como un contenido
                                                      yybegin(IDENTIFICADOR);
                                                      return symbol(VAR_A, yytext(), false);}}//hago este retorno por razones eqq a FOR_A, aunque en realidad en esta ER, sería imposible, pero por si, mejor de una vez xD

    {etiquetaCierre}                                {if(yystate() == ERROR){
                                                        yypushback(yylength());
                                                        yybegin(YYINITIAL);
                                                        if(etiquetaAntApetura){
                                                            System.out.println("[L] contenido in [cierr]: "+ contenido.toString() + "\n");                                                            
                                                            return symbol(CONTENIDO, new String(contenido), false);
                                                        }else{
                                                            System.out.println("[L] contenido ext [cierr]: "+ contenido.toString() + "\n");                                                      
                                                        }                                 
                                                    }else{//es decir es = YYINITIAL, puesto que solo con estado error y YYINI, se puede entrar a esta sección
                                                        etiquetaAntApetura = false;//Aquí no importa si es la de HTML o no xD
                                                        return axnEtiquetasCierreEstandar();}}
}

{ComentarioMultiLinea}                              {if(yystate() == ERROR){
                                                        yypushback(yylength());
                                                        yybegin(estadoEntrada);
                                                        if(etiquetaAntApetura){
                                                            System.out.println("[L] contenido in [Com]: "+ contenido.toString() + "\n");                                                            
                                                            return symbol(CONTENIDO, new String(contenido), false);
                                                        }else{
                                                            System.out.println("[L] contenido ext [Com]: "+ contenido.toString() + "\n");                                                      
                                                        }    
                                                        estadoEntrada = -5 ;
                                                    }else{
                                                        return accionComentario();
                                                    }//sino no hay nada que hacer xD
                                                    }

<YYINITIAL, IDENTIFICADOR, FOR> {espacioEnBlanco}                             {/*se ignora*/}//lo comenté por el llamado a error, aunque si lo tengo así comentado, qiere decir que como contenido podría enviar puros ws...
                                                                               //PERO podría llegar a ser útil si se empleara solo en YYINI, para que de esa forma no se vyaa por eso al error para add contenido... entonces
                                                                               //sería útil que los otros dos estados léxicos tengan a esta ER... xD

<FOR, IDENTIFICADOR> "["                                       {System.out.println("[L] agrup_arr: "+yytext());
                                                                return symbol(CORCHETE_A, yytext(), false);}
                                    
<FOR, IDENTIFICADOR> "]"                                       {System.out.println("[L] agrup_arr: "+yytext());
                                                                return symbol(CORCHETE_C, yytext(), false);}

<FOR, IDENTIFICADOR> "."                                       {System.out.println("[L] invocacion: "+yytext());
                                                                return symbol(PUNTO, yytext(), false);}

<FOR, IDENTIFICADOR> {numero}                                  {System.out.println("[L] entero: "+yytext());
                                                                return symbol(ENTERO, yytext(), false);}//quedamos en que íbamos a enviar Integers para así evitar e casteo a String y al igual que cuando se usuaran objetos, manejar la Excepción si es que el contenido de la var enviada no corresp a la esperada, a menos que se haga una revisión previa en la TS, para saber el tipo, si creo que eso va a ser puesto que hay que saber en 1er lugar si existe, sino entonces error, y luego ver el tipo, si concuerda entonces todo nice xD
//no coloco a ERROR aquí tb puesto que si lo hago, arruinaría la concat de contenido en general, puetso que si llegaran a haber números los tomaría como tal y no como contenido, tal y como debe ser...
//y coloqué a for como a identificador, puesto que en ambos se puede utilizar el result...

<IDENTIFICADOR, FOR, ERROR> {variable}                          {if(yystate() == ERROR){
                                                                    if(estadoEntrada == IDENTIFICADOR || estadoEntrada == FOR){
                                                                        yypushback(yylength());
                                                                        yybegin(estadoEntrada);//lo dejo así,puesto que solo puede ser cualquiera de los 3 que esperaría que fuera...                                                                                    
                                                                        if(etiquetaAntApetura){
                                                                            System.out.println("[L] contenido in [var]: "+ contenido.toString() + "\n");                                                                            
                                                                            return symbol(CONTENIDO, new String(contenido), false);
                                                                        }else{
                                                                            System.out.println("[L] contenido ext [var]: "+ contenido.toString() + "\n");                                                      
                                                                        }     

                                                                        estadoEntrada = -5 ;
                                                                    }else{//Es decir que es YYINITIAL
                                                                        contenido.append(yytext());
                                                                    }//puesto que en el estado YYINI, no existe una ER como identificador
                                                                  }else{//Dejo solo el else, porque aquí solo se puede entrar por ERROR, FOR o IDENTIFICADOR y esos últimos 2 deberían exe este bloque
                                                                    System.out.println("[L] variable: "+yytext());                                                                    
                                                                    return symbol(VARIABLE, yytext(), false);}                                                                    
                                                                }//conn este else se da a notar que no se enviará una variable a menos que lo que haya escrito el usuario haga que se pueda entrar a los estados IDEN | FOR                                  

<IDENTIFICADOR, ERROR>{

    {finIdentificador}                               {if(yystate() == ERROR){
                                                        if(estadoEntrada == IDENTIFICADOR){
                                                            yypushback(yylength());
                                                            yybegin(estadoEntrada);//lo dejo así,puesto que solo puede ser cualquiera de los 3 que esperaría que fuera...                                                                                    
                                                            if(etiquetaAntApetura){
                                                                System.out.println("[L] contenido in [fin_ID]: "+ contenido.toString() + "\n");                                                                
                                                                return symbol(CONTENIDO, new String(contenido), false);
                                                            }else{
                                                                System.out.println("[L] contenido ext [fin_ID]: "+ contenido.toString() + "\n");                                                      
                                                            }     

                                                            estadoEntrada = -5 ;//lo pongo aquí porque después de esto el estado actual cb, por lo tanto debe dejarle limpio para que se add el estado que corresp xD xD...
                                                        }else{//Es decir que es YYINITIAL|FOR
                                                            contenido.append(yytext());
                                                        }//puesto que en el estado YYINI y en el FOR, no existe una ER como identificador                                                        
                                                      }else{//Dejo solo el else, porque aquí solo se puede entrar por ERROR, FOR o IDENTIFICADOR y esos últimos 2 deberían exe este bloque
                                                         System.out.println("[L] fin identificador [IDENT -> YYINI]");
                                                         yybegin(YYINITIAL);
                                                         return symbol(VAR_C, yytext(), false);}}//deb retornar esto, por razones eqq a las expuestas en la RP eqq a esta, del for...                                                        
}

<FOR, ERROR>{

    ";"{espacioEnBlanco}*">"                        {if(yystate() == ERROR){
                                                        if(estadoEntrada == FOR){
                                                            yypushback(yylength());
                                                            yybegin(FOR);//pongo el YYINI, puesto que al llegar aquí sin importar que haya sido desde el estado de error, esta ER siempre indicará fin de etiqueta de ini del for, por lo tanto, que debería salirse de este estado...
                                                            if(etiquetaAntApetura){
                                                                System.out.println("[L] contenido in [fin_FOR]: "+ contenido.toString() + "\n");                                                                
                                                                return symbol(CONTENIDO, new String(contenido), false);
                                                            }else{
                                                                System.out.println("[L] contenido ext [fin_FOR]: "+ contenido.toString() + "\n");                                                      
                                                            }     

                                                            estadoEntrada = -5 ;                                                                                
                                                        }else{//Es decir que es YYINITIAL|FOR
                                                            contenido.append(yytext());
                                                        }//puesto que en el estado YYINI y en el IDEN, no se tiene nada más que a sus propias etiquetas como palabras a identificar xD
                                                    }else{//Dejo solo el else, porque aquí solo se puede entrar por ERROR, FOR o IDENTIFICADOR y esos últimos 2 deberían exe este bloque
                                                        System.out.println("[L] fin FOR [FOR -> YYINI]");
                                                        yybegin(YYINITIAL);
                                                        return symbol(FOR_CA, yytext(), false);}}//DEBE retornarse puesto que supongamos que después de la var de HASTA apareciera un </for> o cualquier otra etiqueta de cierre, sin antes haber aparecido una de cieraa de la apertura [CA], lo tomarúa como bueno el Parser, porque nunca se le dijo que después de la var de HASTA tendría que aparecer ese FOR_CA xD

    ":"                                             {if(yystate() == ERROR){
                                                        if(estadoEntrada == FOR){
                                                            yypushback(yylength());
                                                            yybegin(FOR);//antes tenía estadoEntrada, pero ya que sé cual es mejor lo pongo de una vez xD
                                                            if(etiquetaAntApetura){
                                                                System.out.println("[L] contenido in [:]: "+ contenido.toString() + "\n");                                                                
                                                                return symbol(CONTENIDO, new String(contenido), false);
                                                            }else{
                                                                System.out.println("[L] contenido ext [:]: "+ contenido.toString() + "\n");                                                      
                                                            }     

                                                            estadoEntrada = -5;                                                                                  
                                                        }else{//Es decir que es YYINITIAL|FOR
                                                            contenido.append(yytext());
                                                        }//puesto que en el estado YYINI y en el IDEN, no se tiene nada más que a sus propias etiquetas como palabras a identificar xD
                                                        //el estado se debería cambiar hasta que haya un yybegin, hacia una parte dirferente, o mejor dicho cuando se detecte otro error/contenido                                                        
                                                    }else{//Dejo solo el else, porque aquí solo se puede entrar por ERROR, FOR o IDENTIFICADOR y esos últimos 2 deberían exe este bloque
                                                         System.out.println("[L] for: "+"DOS_PUNTOS");                                                                                                                  
                                                         return symbol(DOS_PUNTOS, yytext(), false);}}
}

[^]                             {accionProcesarError();}


/*return axnEtiquetasAperturaEstandar();*/

//no hice que cuando apareciera un < o un </, se enviara a un estado léxico, como en el caso de identificador y el for, puesto que aunque si llegaría a
//funcionar, si hubiera algún error, no se podría tomar de manera sencilla todo lo reunido y pasarlo a contenido, puesto que en primer lugar
//si se creara ese estado léxico, por cada match que se hiciera, se haría un retorno de manera automática, entonces aunque todo lo que se estuviera leyendo
//se concatenara en una var string, como en el caso de STRING, con anterioridad, ya se habría enviado al lexer los token de esas palabras en su forma
//natural y a parte se enviarían las mismas solo que en forma de contenido... entonces así NO! xD :v xD 

    //con identificador y el for, si lo hice, puesto que no son etiquetas nativas del HTML, entonces no tiene por qué rxn de igual manera que ellas, al 
    //Encontrar un error y volverlo contenido, por eso en el caso de ellas, el parser recibiría los tokens retornados en el estado léxico en cuestión
    //además de la concatenación de contenido, puesto que esto es el eqq de error en el HTML... xD
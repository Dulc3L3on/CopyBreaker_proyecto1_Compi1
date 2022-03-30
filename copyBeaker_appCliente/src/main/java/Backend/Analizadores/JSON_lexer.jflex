//Configuración
package Backend.Analizadores;
import java_cup.runtime.*;
import Backend.Objetos.Token;
import static Backend.Analizadores.JSON_ParserSym.*;
import Backend.Manejadores.ManejadorErrores;
//import Objetos.ReporteError;//yo supongo que si se debe importar para usar el eqq de ctes static, aunque sea kotlin... solo era para probar que si jala cosas de kotlin en Java xD

%%
//Código de usuario
%class Lexer
%unicode
%line
%column
%public
//%standalone
//%int
%caseless
%cupsym JSON_ParserSym
%cup //recuerda reemplazarlo por %cup, al hacer eso tampoco se hace nec add el %int...

//macros [símbolos aceptados]
simbolosAceptados = ":"|","|"{"|"}"|"["|"]"

//macros fundamentales
digito = [0-9]
numero = {digito}+  
decimal = 0|1("."{numero})?

//macros auxiliares
finDeLinea = \r|\n|\r\n
tabulacion = [ \t\f]
espacioEnBlanco = {finDeLinea} | {tabulacion}

%{          
    ManejadorErrores manejadorErrores;

    boolean requeriaCompania = false;
    Token tokenAnterior = null;

    StringBuffer contenido = new StringBuffer();
  
    private Symbol symbol(int tipo, String valor, boolean conCompania){//Dejé el valor como string, como no requiero que alguno de los lexemas sea diferente...
        Token tokenActual = new Token (yyline+1, yycolumn+1, valor, (conCompania)?tokenAnterior:null);        

        if(tokenAnterior != null && requeriaCompania){
            tokenAnterior.setSiguiente(tokenActual);
        }

        tokenAnterior = tokenActual;
        requeriaCompania = conCompania;
        return new Symbol(tipo, yyline+1, yycolumn+1, tokenActual);
    }

    private Symbol acccionReservada(int tipo){//por el momneot es void xD    
        System.out.println("[L] reservada: "+ yytext());                

        return symbol(tipo, yytext(), false);    
    }    

    private Symbol accionSimbolosAceptados(){        
        if(yystate() == ERROR){
            accionParadaParaError();
        }

        System.out.println("[L] símbolo "+ yytext() +" T: " +((yytext().equals(":"))?"DOS_PUNTOS":((yytext().equals(","))?"COMA":((yytext().equals("{"))?"LLAVE_A":((yytext().equals("}"))?"LLAVE_C":((yytext().equals("["))?"CORCHETE_A":"CORCHETE_C"))))));        
        return symbol(((yytext().equals(":"))?DOS_PUNTOS:((yytext().equals(","))?COMA:((yytext().equals("{"))?LLAVE_A:((yytext().equals("}"))?LLAVE_C:((yytext().equals("["))?CORCHETE_A:CORCHETE_C))))), yytext(), false);    
    }//por si acaso miras que si te es posible add SA a ERROR sin generar problemas al formar los tokens aquí y analizar las RP en el parser
   
    private void accionProcesarError(){
        if(yystate() != ERROR){//no coloco tb a CADENA, porque se supone que no debería hacer match con el [^] cuando esté dentro de ese estado léxico...
            //estadoAnteriorError = yystate();//esta es la alternativa a la que me refiero en axnParadaError, para dar msje cuando un string tiene error...
            contenido.setLength(0);//no provoca problemas el usar la variable string que tb usa SSTRING para concatenar, puesto que al estar en ese estado no se entrará aquí puesto que esta expre reg tiene la menor precedencia y las reglas de allá impiden que este caso suceda... puesto que se absorben todos los caracteres posibles hasta llegar a la otra "
            yypushback(yylength());//iba a colocar 1, en lugar de yylength pero no se si la unidad de medida varíe porque podría se que cada caracter tenga un tamaño diferente dependiendo de la cdad de bits que requiera para ser plasmado p.ej
            yybegin(ERROR);
        }else if(yystate() == ERROR){
            contenido.append(yytext());
        }
    }

    private void accionParadaParaError(){//aquí es donde se imprime todo lo concatenado que se clasificó como error...          
        System.out.println("[L] error: "+ contenido.toString() + ((contenido.length() != 0)?" INVALID WORD":"Las cadenas solo pueden ocupar una línea")+"\n");
        manejadorErrores.setError(new Token(yyline+1, yycolumn+1, contenido.toString(), null), ((contenido.length() != 0)?false:true));//aquí no se requiere de un token previo... y esta vez nisiqueira en las operaciones xD

        yybegin(YYINITIAL);//ese operador ternario lo puse para que se justifique el hecho de que después de error no se muestre el contenido errado, puesto que contenido va a tener length = 0 si el error surgió en STRING [puesto que se llegará a error cuando haya salto de línea o retorno de carro no explícito] entonces puedo utilizar eso para personalizar el msje [cabe reslatar que si el error surge en el YYINI, siempre tendrá maś de algo contenido, puesto que desde ese estado se puede llegar a error si se encuentra con algo que no es aceptado y ahí el \n y \r son ignorados, es decir técnicamente aceptados xD]
    }//si la justificación por la cual uso el operador ternario no funciona, entonces guarda el stado y si ese es == SSTRING entonces pones ese msje xD, ahí si el msje estaría correcto siempre xD

    public void setInfoNecesaria(ManejadorErrores elManejadorErrores){
        manejadorErrores = elManejadorErrores;
    }//el RESULT ya tendrá seteada la clase respectiva sin problemas xD
%}

%state SSTRING ERROR

%%
//Reglas léxicas
<YYINITIAL> "score"               {return acccionReservada(SCORE);}
<YYINITIAL> "clases"              {return acccionReservada(CLASES);}
<YYINITIAL> "variables"           {return acccionReservada(VARIABLES);}
<YYINITIAL> "metodos"             {return acccionReservada(METODOS);}
<YYINITIAL> "comentarios"         {return acccionReservada(COMENTARIOS);}
<YYINITIAL> "nombre"              {return acccionReservada(NOMBRE);}
<YYINITIAL> "tipo"                {return acccionReservada(TIPO);}
<YYINITIAL> "funcion"             {return acccionReservada(FUNCION);}
<YYINITIAL> "parametros"          {return acccionReservada(PARAMETROS);}
<YYINITIAL> "texto"               {return acccionReservada(TEXTO);}

<YYINITIAL, ERROR> {simbolosAceptados}    {return accionSimbolosAceptados();}

<YYINITIAL>{    
    {numero}                          {System.out.println("[L] numero: "+ yytext());return symbol(NUMERO, yytext(), false);}//son los signos de operación en sí quienes requieren del anterior

    \"                                {contenido.setLength(0); yybegin(SSTRING);}//tengo que hacer que la entrada se convierta a un tipo específico, sino podría detectar como errónea la codificación de un caracter equivalente en otro "sistema" o modo de codificación, como sucedió con las comillas                

    {espacioEnBlanco}                 {/*se ignora*/}
}

 <SSTRING> {
      \"                       { yybegin(YYINITIAL);System.out.println("[L] cadena: "+ contenido.toString() + " T: "+CADENA);return symbol(CADENA, new String(contenido), false);}//devulve el contenido dentro de las "" puesto que eso es lo que interesa xD      

      [^\n\r\"\\]+             { contenido.append( yytext()); }

      //Esto es para cuando add literalmente estos símbolos, de tal forma que puedan cumplir su función xD    
      \\t                      { contenido.append('\t'); }
      \\n                      { contenido.append('\n'); }
      \\r                      { contenido.append('\r'); }
      \\\"                     { contenido.append('\"'); }
      \\                       { contenido.append('\\'); }      
}//si impide que hayan saltos de línea entre cadenas y eso es controlado por la expre que niega los escapes...

<ERROR>{    
    {espacioEnBlanco}           {accionParadaParaError();}//aquí se invoca a la función que se encarga de recisar lo de substring de reservadas xD
}

[^]                             {accionProcesarError();}        
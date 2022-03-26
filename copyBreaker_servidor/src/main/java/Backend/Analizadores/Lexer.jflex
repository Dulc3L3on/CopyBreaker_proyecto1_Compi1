//Configuración
package Backend.Analizadores;
import java_cup.runtime.*;
import Backend.Objetos.Token;
import Backend.Objetos.Resultado.RESULT;
import Backend.Objetos.Resultado.Clase;
import Backend.Objetos.Resultado.Comentario;
import static Backend.Analizadores.ParserSym.*;
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
%cupsym ParserSym
%cup //recuerda reemplazarlo por %cup, al hacer eso tampoco se hace nec add el %int...

//macros [símbolos aceptados]
simbolosAceptados = ":"|","|"{"|"}"|";"|"."
operadoresAritmeticos = "+"|"-"|"*"|"/"|"("|")"
operadoresCondicionales = "&"|"|"|"!"|">"|"<"|"="

//macros fundamentales
digito = [0-9]
numero = {digito}+("."{digito}+)?  
letra = [a-zA-Z]

//macro para char, a ver si no da error por la ' encerrada en "", si si, entonces colocas \'
caracter = "'"[{letra} ]"'"

//macro para los identificadores [nombre]
identificador = ("$"|{letra})|("_"|"$"|{letra})("_"|"$"|{letra}|{digito})+

//macros auxiliares
finDeLinea = \r|\n|\r\n
tabulacion = [ \t\f]
espacioEnBlanco = {finDeLinea} | {tabulacion}

//macros para comentarios
CommentarioMultiLinea = {comentarioTradicional} | {comentarioDocumentacion}

cuerpoComentario = [^\r\n]//no debería ser ^\r ^\n y ^\r\n??, también podría ser útil para definir cadena... solo que harbía que incluir ^\"
comentarioLinea = "//" {cuerpoComentario}* {finDeLinea}?//puesto que puede venir al final del archivo (lo cual no es un LT!!), pero no se supone que al encontrar uno de esos debería parar :v es decir si es expre reg solo debería terner declaradoel cuerpo aceptado, no el no aceptado, si fuera estado ahí si; digo yo...

comentarioTradicional   = "/*" [^*] ~"*/" | "/*" "*"+ "/"    
    
comentarioDocumentacion = "/**" {contenidoComentario} "*"+ "/"
contenidoComentario = ( [^*] | \*+ [^/*] )*

%{      
    RESULT result;    
    String proyecto = "";//este será útil para rellenar la info que req Error
    //no se req un campo para el nombre de la clase, puesto que esta info ya la posee el RESULT
    String[] nombreClases = {"clasePrueba"};//quizá se reeemplzace con una lista... ahí te acuerdas de poner el seter...
    ManejadorErrores ManejadorErrores;

    boolean requeriaCompania = false;
    Token tokenAnterior = null;

    private boolean fueReservadaImport = false;    

    String operadorAnterior = null;
    Symbol endOperator = null;
    String[] operadoresCondicionales = {"&&", "||", "==", ">=", "<=", "!="};//solo los que estén formados por dos operadores, sino pues queda en single xD
    String[] operadoresCondicionalesSimples = {"&", "|", ">", "<", "=", "!"};
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
        fueReservadaImport = (tipo == IIMPORT);//para que así media vez se llegue a class o public [suponiendo una sintaxis correcta], se haga las revisiones que se tienen en el método del identificador...        

        return symbol(tipo, yytext(), false);    
    }    

    private Symbol accionSimbolosAceptados(){        
        if(yystate() == ERROR){
            accionParadaParaError();
        }

        System.out.println("[L] símbolo "+ yytext() +" T: " +((yytext().equals(":"))?"DOS_PUNTOS":((yytext().equals(","))?"COMA":((yytext().equals("{"))?"LLAVE_A":((yytext().equals("}"))?"LLAVE_C":((yytext().equals(";"))?"PUNTO_COMA":"PUNTO"))))));        
        return symbol(((yytext().equals(":"))?DOS_PUNTOS:((yytext().equals(","))?COMA:((yytext().equals("{"))?LLAVE_A:((yytext().equals("}"))?LLAVE_C:((yytext().equals(";"))?PUNTO_COMA:PUNTO))))), yytext(), false);    
    }//por si acaso miras que si te es posible add SA a ERROR sin generar problemas al formar los tokens aquí y analizar las RP en el parser

    private Symbol groupConditionalOperators(){        
        for(int indice = 0; indice < operadoresCondicionales.length; indice ++){
            if((operadorAnterior+yytext()).equals(operadoresCondicionales[indice])){
                System.out.println("[L] operador: "+ ((indice < 2)?"logico":"relacional") +"compuesto: "+ operadorAnterior+yytext());
                Symbol simbolo = symbol(((indice < 2)?OPERADOR_LOGICO:OPERADOR_RELACIONAL), (operadorAnterior+yytext()), false);//se setea el operador compuesto de dos operadores condicionales                    
                operadorAnterior = null;
                return simbolo;//se retorna el objeto
            }
        }
        return sendSingleOperator();        
    }//debo add un enof val por si en dado caso un símbolo de estos se quedara solo al final, puesto que de ser así nunca sería enviado al parser...

    private Symbol sendSingleOperator(){        
        yypushback(yylength());//para que vuelva a leer ese caracter condicional que no permitía formar paraeja alguna de ese tipo de operadores

        return sendOperatorConditionalSymbol();        
    }

    private Symbol sendOperatorConditionalSymbol(){
        Symbol simbolo = null;

        for(int indice = 0; indice < operadoresCondicionalesSimples.length; indice ++){
            if((operadorAnterior).equals(operadoresCondicionalesSimples[indice])){
                System.out.println("[L] operador: " + ((indice <= 3)?((indice<2)?"logico: ":"relacional: "):((indice==4)?"igual: ":"no: ")) + operadorAnterior);
                //((indice <= 3)?("operador"+ ((indice < 2)?"logico":((indice < 4)"relacional")) +"simple: "):((indice == 4)?"igual: ":"no: "))
                simbolo = symbol(((indice <= 3)?((indice < 2)?OPERADOR_LOGICO:OPERADOR_RELACIONAL):((indice == 4)?IGUAL:NO)), operadorAnterior, false);//se setea el objeto para el operador simple, según lo que contenga la var...
                //((indice <= 3)?((indice < 2)?OPERADOR_LOGICO:((indice < 4)OPERADOR_RELACIONAL)):((indice == 4)?IGUAL:NO))
                operadorAnterior = null;
                break;//lo add para ya no seguir buscando pues no tiene chiste xD
            }
        }
        return simbolo;//nunca retornará null, puesto que siempre se invocará cuando tenga algo que devolver...
    }

    private Symbol accionIdentificador(){
        if(!fueReservadaImport){
            for(int claseActual = 0; claseActual < nombreClases.length; claseActual++){
                if(yytext().equals(nombreClases[claseActual])){
                    System.out.println("[L] OBJETO: "+ yytext());                            
                    return symbol(OBJETO, yytext(), false);
                }
            }
        }//con esto se logra que el import tenga solo nombres,con eso se permite que la dirección pueda estar mala, al no especificar que un OBJ puede venir solo al final, pero como no se analizará semánticamente, entonces se dejará así...
        System.out.println("[L] identificador: "+ yytext());
        return symbol(NOMBRE, yytext(), false);
    }

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
        System.out.println("[L] error: "+ contenido.toString() + ((contenido.length() != 0)?" INVALID WORD":"Las cadenas solo pueden ocupar una línea")/*ReporteError.LEXER_INVALID_WORD*/ +"\n");
        yybegin(YYINITIAL);//ese operador ternario lo puse para que se justifique el hecho de que después de error no se muestre el contenido errado, puesto que contenido va a tener length = 0 si el error surgió en STRING [puesto que se llegará a error cuando haya salto de línea o retorno de carro no explícito] entonces puedo utilizar eso para personalizar el msje [cabe reslatar que si el error surge en el YYINI, siempre tendrá maś de algo contenido, puesto que desde ese estado se puede llegar a error si se encuentra con algo que no es aceptado y ahí el \n y \r son ignorados, es decir técnicamente aceptados xD]
    }//si la justificación por la cual uso el operador ternario no funciona, entonces guarda el stado y si ese es == SSTRING entonces pones ese msje xD, ahí si el msje estaría correcto siempre xD

    public void setInfoNecesaria(RESULT elResult, String elProyecto, 
    String[] listaClases, ManejadorErrores elManejadorErrores){

        result = elResult;
        proyecto = elProyecto;        
        nombreClases = listaClases;
        ManejadorErrores = elManejadorErrores;
    }//el RESULT ya tendrá seteada la clase respectiva sin problemas xD
%}

%eof{
    if(operadorAnterior!=null){
        System.out.println("[L] end-operator: "+operadorAnterior);
        endOperator = sendSingleOperator();//no se va a exe el return depués de hacer este != null, quiere decir que no se va a enviar al parser el operador... bueno eso sucedio en el lexer standalone, de todos modos en una clase bien hecha jamás tendría por qué exe esta axn, entonces no importa xD
    }//creo que va a dar error... porque desde aquí no se puede retornar algo, entonces mira yo creo que no provoca una incongruencia si haces el pushBack aquí...    
%eof}//debería estar si es que vas a agrupar esos operadores, puesto que no ha forma de que se exe para un símbolo que se encuentre solo...

%state SSTRING ERROR

%%
//Reglas léxicas
<YYINITIAL> "import"               {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(IIMPORT);}}
<YYINITIAL> "new"                  {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(NEW);}}
<YYINITIAL> "this"                 {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(THIS);}}
<YYINITIAL> "public"               {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(PUBLIC);}}
<YYINITIAL> "protected"            {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(PROTECTED);}}
<YYINITIAL> "private"              {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(PRIVATE);}}
<YYINITIAL> "final"                {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(FINAL);}}
<YYINITIAL> "class"                {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(CLASS);}}
<YYINITIAL> "int"                  {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(INT);}}
<YYINITIAL> "boolean"              {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(BOOLEAN);}}
<YYINITIAL> "String"               {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(STRING);}}
<YYINITIAL> "char"                 {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(CHAR);}}
<YYINITIAL> "double"               {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(DOUBLE);}}
<YYINITIAL> "Object"               {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(OBJECT);}}
<YYINITIAL> "void"                 {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(VOID);}}
<YYINITIAL> "true"                 {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(BOOLEANO);}}
<YYINITIAL> "false"                {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(BOOLEANO);}}
<YYINITIAL> "if"                   {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(IF);}}
<YYINITIAL> "else"                 {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(ELSE);}}
<YYINITIAL> "for"                  {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(FOR);}}
<YYINITIAL> "while"                {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(WHILE);}}
<YYINITIAL> "switch"               {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(SWITCH);}}
<YYINITIAL> "case"                 {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(CASE);}}
<YYINITIAL> "default"              {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(DEFAULT);}}
<YYINITIAL> "break"                {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(BREAK);}}
<YYINITIAL> "return"               {if(operadorAnterior != null){return sendSingleOperator();}else{return acccionReservada(RETURN);}}

<YYINITIAL, ERROR> {simbolosAceptados}    {if(operadorAnterior != null){return sendSingleOperator();}else{return accionSimbolosAceptados();}}

<YYINITIAL>{
    {comentarioLinea}                 {if(operadorAnterior != null){return sendSingleOperator();}else{System.out.println("[L] comentario-línea: "+ yytext().substring(2).trim());
                                       result.addComentario(new Comentario(yytext().substring(2).trim()));}}//debe establecerse el comentario cuando se entre al else, sino se estaría enviando 2 veces...

    {numero}                          {if(operadorAnterior != null){return sendSingleOperator();}else{System.out.println("[L] numero: "+ yytext());return symbol(NUMERO, yytext(), false);}}//son los signos de operación en sí quienes requieren del anterior

    {identificador}                   {if(operadorAnterior != null){return sendSingleOperator();}else{return accionIdentificador();}}

    {operadoresAritmeticos}           {if(operadorAnterior != null){return sendSingleOperator();}else{System.out.println("[L] simbolo: "+ yytext());
                                         return symbol(((yytext().equals("+"))?MAS:((yytext().equals("-"))?RESTA:((yytext().equals("*"))?ASTERISCO:((yytext().equals("/"))?DIV:((yytext().equals("("))?PARENTESIS_A:PARENTESIS_C))))), yytext(), true);}}

    {operadoresCondicionales}         {if(operadorAnterior == null){if(endOperator==null){operadorAnterior = yytext();}else{System.out.println("endOperador retornado");return endOperator;}}else{return groupConditionalOperators();}}

    \"                                {if(operadorAnterior != null){return sendSingleOperator();}else{contenido.setLength(0); yybegin(SSTRING);}}//tengo que hacer que la entrada se convierta a un tipo específico, sino podría detectar como errónea la codificación de un caracter equivalente en otro "sistema" o modo de codificación, como sucedió con las comillas            

    {caracter}                        {if(operadorAnterior != null){return sendSingleOperator();}else{System.out.println("[L] caracter: "+ yytext().substring(1,2));return symbol(CARACTER, yytext().substring(1,2), false);}}

    {CommentarioMultiLinea}           {if(operadorAnterior != null){return sendSingleOperator();}else{System.out.println("[L] comentario-multiLinea: "+ yytext().substring(2, yytext().length()-2).trim());
                                       result.addComentario(new Comentario(yytext().substring(2, yytext().length()-2).trim()));}}

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

[^]                            {if(operadorAnterior != null){return sendSingleOperator();}else{accionProcesarError();}}        

//Nota: NO vas a usar el método para establecer MENOS o RESTA,simplemente se irá como RESTA y la gramática con el %prec se encargará de lo demás...
//de ahí todo lo demás que empleaste en el lexer de práctica 1 sí, o su eqq xD

//este SI es case sensitive

//Qué vas a hacer?
//con respecto a agrupaciones del mismo tipo, puesto que no se requiere hacer que funcionen los statement
//bien podrías hacer que desde aquí se haga lo necesario para que por ejemplo cuando se tenga un == se setee como operador relacional
//al igual que cuando aparezca un !=, eso sí tendrías que hacer un retroceso por si acaso el símbolo actual no hace que al ser concat con el 
//anterior forme un elemento de alguno de los grupos (en el cao de los símbolos), por eso debes ver bien qué es lo que haces con el retroceso
//esto lo puedes hacer revisando el estado de error
//también podrías enviar el nombre según correso, si es = entonces IGUAL y si viene otro entonces envias otro IGUAL y pones explicitamente las
//formas de combinación posibles en las reglas del parser...
    //con incremento si debería enviar los símbolos individuales
    //Esto lo puedes hacer con los operadores lógico y relacionales, con los objetos no lo puedes HACER, pensaba que sí pero no porque 
    //aunque haga un listado, tendría que saber el nombre de todas previo a empezar el análisis con cualquiera de las clases existentes
    //y eso solo sería posible si por medio del método hava que me permite leer archivos, puedo recoger la lista de los nombres de las 
    //clases, lo cual si mal no recuerdo lo mencionó Thony, sino
        //imagino que tendré que arreglar mi regla de producción
        //para que en lugar de "Objeto" tenga nombre...

        //de todos modos, si haces esto de colocar nombre, no estaría malo, puesto que el inge Moisés dijo que no hay que estar evaluando NADA
        //de manera semántica, lo que esté escrito en las clases Java a revisar, bueno solo aquello que se pueda a partir de la gramática...


//BUENO con respecto a la axn que permite nombrar a los operadores relacionales según el tip ode grupo, si sale y funciona, pero al decidir hacer eso
//tendrías que alargar un poco el proceso del lexer
    //1. crear un arreglo con los operadors no repetidos que estén involucrados en las condiciones (incluido el igual), para que cuando un caracter de 
    //la entrada coincida con este, se pueda revisar si la variables es null, si si entonces se procede a almacenarla, sino entonces se concatena
    //lo recibido a lo que esté en esa var String, y se procede a revisar si esa concat concuerda con alguna de las combinaciones posibles de los
    //operadores lógicos, por medio de un for que recorre el arreglo con los símbolos bien formados, si no entonces
        //se procede a hacer pushBak y a enviar lo que contiene esa var que almacena el símbolo anterior, en un objeto con el nombre del símbolo individual
        //y a limpiar esa var String
    //ahora suponiendo que depués de que se reciba el primer símbolo de esos (es decir después de hacer la var != null xD), se reicba un símbolo que nada que ver entonces
        //si la var no es null, entonces
            //se hace pushBak, se crea el objeto con el nombre individual y se hace la var = null
        //si si, se hace lo que corresp para ese match con la expre reg en cuestión

    //pero tendrías que ver si provoca algún problema ese yypushBakc (lo cual lo dudo puesto que eso hiciste en la práctica 1 solo para los errores)
    //o si hay problema con el hecho de estar haciendo esperar y no enviar el token, es decir más específico, si de alguna manera se provoca que se
    //Envíe un token incorrecto
        //m+as que todo por aquellos token que utilizan tambieńe a estos, como los de autoincremento [creo que solo esos xD]

    //si no haces esto, lo único que deberías hacer es tener en lugar de un arreglo con los símbolos bien formados, tener la expre reg con estos xD

    //par alo de los objetos tendrías ue hacer la revisión de esa lista cada vez que se haga match con la expre reg para identificador (nombre)

    //ahí te recuerdas de add el cuerpo de los comentarios a la lista corresp
    
    
    //ya tiene actualizado lo del caracter [ER, separación de '' y == a != [eso lo había puesto por error xD]], ya incluye void
    
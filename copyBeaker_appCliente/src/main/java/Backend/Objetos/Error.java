/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos;

import Backend.Objetos.Enums.LexerError;
import Backend.Objetos.Enums.SintaxError;


/**
 *
 * @author phily
 */

public class Error {    
    private String lexema;
    private String linea;//las coloqué string, para hacer algunas cosas peculiares, más que todo para cuando no se sepa el #línea y/o #col del error
    private String columna;    
    private final String tipo;
    private String descripcion;
    
    public Error(String lexema, String linea, String columna, LexerError tipo, String extra){//Ese extra será para el maybe xD        
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;
        this.tipo = "Lexico";
        this.descripcion = this.lexerErrors[tipo.ordinal()]+extra;//Se enviará "" cuando no se req, entonces NO problem xD
    }
    
    public Error(String lexema, String linea, String columna, SintaxError tipo, String extra){//a menos que veas como evitar el ArrayIndexOutBounds al intentar leer la lista de símbolos, este cnstrct no se utilizará xD
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;        
        this.tipo = "Sintactico";
        this.descripcion = this.sintaxErrors[tipo.ordinal()] + extra;        
    }
    
    public Error(String lexema, String linea, String columna, SintaxError tipo){        
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;        
        this.tipo = "Sintactico";
        this.descripcion = this.sintaxErrors[tipo.ordinal()];        
    }
    
    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }//puede que tal vez me sirva para dar un rango de dónde puede estar la línea de error, esto al usar la línea del último obj add al RESULT y el sig justo después del error... pero APRESÚRATE si quieres hacer eso...

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }    

    public String getLexema() {
        return lexema;
    }

    public String getLinea() {
        return linea;
    }

    public String getColumna() {
        return columna;
    }

    public String getErrorType() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    private final String lexerErrors[] = new String[] {
        "Palabra no aceptada en el lenguaje",
        "Quizá quisiste decir ",//y se concatena la recomendación xD        
        "Cadena sin comilla de cierre"
    };

    private final String sintaxErrors[] = new String[]{
        "Error fatal, imposible realizar el análisis",
        "Error sintáctico, se esperaba: ",
        "Estructura general de JSON con errores; esperado-> { <lista_secciones> }",
        "Existe una sección del mismo tipo en la línea ",
        "Lista de secciones de JSON con errores",
        "Falta nombre de sección [score|clases|metodos|variables|comentarios]",
        "Sección score con errores; esperado-> score : <cadena>",
        "Sección de clases con errores; esperado-> clases : [ <clase> ]",
        "Lista de objetos clase con errores, esperado-> {<clase>},... {<clase>}",        
        "Sección de variables con errores, esperado-> variables : [ <variable>* ]",
        "Lista de objetos variable con errores, esperado-> {<variable>},... {<variable>}",
        "Objeto variable con errores; esperado-> <tipo_atributo> : CADENA",
        "Un objeto variable necesita 3 atributos: <tipo>, <nombre>, <funcion>",
        "Sección de métodos con errores, se esperaba-> métodos : [ <metodo>* ]",
        "Lista de objetos método con errores; esperado-> {<metodo>},... {<metodo>}",
        "Objeto método con errores; esperado-> <tipo_atributo> : CADENA|NUMERO",
        "Un objeto metodo necesita 3 atributos: <tipo>, <nombre>, <parametros>",
        "Sección de comentarios con errores; esperado-> comentarios : [ <comentario>* ]",
        "Lista de comentarios con errores; esperado-> {<comentario>}, ... {<comentario>}",
        
        "Sentencia de asignación o asignación mal formada",
        "Delaración de variable mal formada, se esperaba-> <<variable>> = <<<contenido>> ;",
        "Lista de variables con errores, se esperaba <<variable>> (, <<variable>>)* ",
        "Expresión numérica con errores, esperado-> entero o variable en una operacion arit.", 
        "Expresión de asignación con errores; esperaba-> <<variable>> = <<valor>> ;",
        
        "HTML mal estructurado; esperado -> <HTML> <<body>> </HTML>",
        "Declaración inválida de etiqueta, esperado-> < <tag> > <<content>> < </tag> >",
        "Definición de for mal formada; esperado -> <for iterator: <<var>> hasta: <<var>> />",
        "El for generador de filas, está mal formado",
        "El for generador de celdas, está mal formado",
        "Contenido variable inválido, se esperaba-> <contenido>| <variable>| <result>",
        "Invocación de atributo result, mal hecha; esperado-> RESULT <<atributosRESULT>>+",
        "Invocación de atributos de RESULT.CLASES tienen errores",
        "Invocación de atributos de RESULT.METODOS tienen errores",
        "Invocación de atributos de RESULT.VARIABLES tienen errores",
        "Invocación de atributos de RESULT.COMENTARIOS tienen errores",
        "Invocación de RESULT.PARAMETROS con errores",
        "Valor numérico inválido, se esperaba -> <<entero>> | <<var#>> | <<result#>>"
    };
    
}

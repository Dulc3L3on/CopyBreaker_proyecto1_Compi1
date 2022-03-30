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
    private int linea;
    private int columna;    
    private final String tipo;
    private String descripcion;
    
    public Error(String lexema, int linea, int columna, LexerError tipo, String extra){//Ese extra será para el maybe xD        
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;
        this.tipo = "Lexico";
        this.descripcion = this.lexerErrors[tipo.ordinal()]+extra;//Se enviará "" cuando no se req, entonces NO problem xD
    }
    
    public Error(String lexema, int linea, int columna, SintaxError tipo, String listaEspera){       
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;        
        this.tipo = "Sintactico";
        this.descripcion = this.sintaxErrors[tipo.ordinal()] + listaEspera;        
    }
    
    public Error(String lexema, int linea, int columna, SintaxError tipo){        
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;        
        this.tipo = "Sintactico";
        this.descripcion = this.sintaxErrors[tipo.ordinal()];        
    }
    
    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }    

    public String getLexema() {
        return lexema;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
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
        "Estructura general de JSON con errores { <lista_secciones> }",
        "Lista de secciones de JSON con errores",
        "Falta nombre de sección [score|clases|metodos|variables|comentarios]",
        "Sección score con errores, se esperaba-> score : <cadena>",
        "Sección de clases con errores, se esperaba-> clases : [ <objetos_clase> ]",
        "Lista de objetos clase con errores, se esparaba-> {<objeto_clase>},... {<objeto_clase>}",        
        "Sección de variables con errores, se esperaba-> variables : [ <objetos_variable> ]",
        "Lista de objetos variable con errores, se esparaba-> {<objeto_variable>},... {<objeto_variable>}",
        "Cuerpo de objeto variable con errores, se esperaba-> <tipo_atributo> : CADENA",
        "Atributo de objeto variable incorrecto",
        "Sección de métodos con errores, se esperaba-> métodos : [ <objetos_variable> ]",
        "Lista de objetos método con errores, se esparaba-> {<objeto_variable>},... {<objeto_variable>}",
        "Cuerpo de objeto método con errores, se esperaba-> <tipo_atributo> : CADENA",
        "Atributo de objeto método incorrecto",
        "Sección de comentarios con errores, se espraba-> comentarios : [ <objetos_comentario> ]",
        "Lista de comentarios con errores, se esperaba-> {<objetos_comentario>}, ... {<objeto_comentario>}"
    };
    
}

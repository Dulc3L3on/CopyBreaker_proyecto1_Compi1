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
    private String tipo;
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
        "Quizá quisiste decir "//y se concatena la recomendación xD
        
    };

    private final String sintaxErrors[] = new String[]{
        "Error sintáctico, se esperaba: ",
        "Uno o más imports están mal formados",
        "\"import\" malformado, se esperaba: import (<dir>)+.AST?; ", 
        "Clase mal creada, se esperaba: <visibilidad> class <class_name> { <body>? }",
        "Creación de variable errada, se esperaba: <tipo> <var>+",
        "Variable numérica [int|double] mal formada",
        "Variable [String] mal formada", "Variable [char] mal formada",
        "Variable [boolean] mal formada", "Variable [Object] mal formada",
        "Variable personalizada [Objeto] mal formada",
        "Definición de método|constructor con errores", 
        "Cuerpo de método [int|double] con errores", 
        "Cuerpo de método [String] con errores", "Cuerpo de método [char] con errores",
        "Cuerpo de método [boolean] con errores", "Cuerpo de método [Object] con errores",
        "Cuerpo de método de tipo personalizado [Objeto] con errores",
        "Cuerpo de método [void] con errores", 
        "Declaración de parámetro con errores, se esperaba <param>|<param-list>", 
        "Tipo de contenido asignado incorrecto", 
        "+= solo es aplicable a contenido de tipo String|int|double",
        "Estructura ciclo for|while|do while con errores",
        "Sección de asignación de ciclo for con errores",
        "Sección de condición de ciclo for con errores", 
        "Sección de incremento de ciclo for con errores", 
        "Invocación a variable mal formada",
        "Uno o más argumentos están mal declarados",
        "Un switch necesita un int|double|string para evaluar los casos",
        "Cuerpo de bloque \"switch\" con errores",
        "Uno o más \"case\" con errores", 
        "Cuerpo de ciclo o sentencia de control con errores"
    };
    
}

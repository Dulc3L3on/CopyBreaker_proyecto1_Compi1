/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos;

import java.io.Serializable;

/**
 *
 * @author phily
 */
public class Token implements  Serializable{//como lo que utilizaría es el lexema en el caso de los anterior y sig, entonces no tengo por qué almacenar el id...  
    private final int linea;
    private final int columna;
    private final String lexema;//lo dejo como string porque aquí no tengo por qué operar xD y aunque este tb lo vaya a utilizar en los otros lexer, de todos modos lo que puedo hacer es o ya sea poner otro parámetro, para los double o enviar un double convertirlo a String y al tener que trabajar con él pasarlo de nuevo a un double...
    private final Token anterior;
    private Token siguiente = null;
    
    public Token(int linea, int columna, String lexema, Token previous){//pienso que con esta herencia, bastaría, pues tendría la rep de cada elemento, así ya solo tendría que recuperar 
        this.linea = linea;
        this.columna = columna;
        this.lexema = lexema;
        this.anterior = previous;
    }     
    
    public void setSiguiente(Token next){
        this.siguiente = next;
    }
    
    public String getLexema(){
        return this.lexema;
    }
    
    public static Token parseToken(Object objeto){
        return (Token) objeto;                
    }
}

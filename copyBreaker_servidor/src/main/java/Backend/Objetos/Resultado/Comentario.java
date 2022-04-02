/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.Resultado;

import java.io.Serializable;

/**
 *
 * @author phily
 */
public class Comentario implements Serializable{
    private String texto;
    
    public Comentario(String texto){
        this.texto = texto;
    }
    
    public String getTexto(){
        return this.texto;
    }
    
    //Estos métodos asString es para que se pueda formar
    //lo que contiene cada {} del JSON
    public String asString(){
        texto = texto.replace( "\n", "\"\n+ \"");//si no llegara a funcionar el cb en el lexer, entonces harás que se reemplace por "" <nada> xD
        return "{ Texto: \""+ this.texto + "\" }";
    }    
    
}

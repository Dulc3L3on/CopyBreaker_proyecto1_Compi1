/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.Resultados;

import java.io.Serializable;

/**
 *
 * @author phily
 */
public class Variable implements Serializable{
    private String tipo;
    private String nombre;    
    private String funcion = "";//ámbito de uso xD   
    
    public Variable(String tipo, String nombre, String funcion){//puesto que por la existencia del RESULT, todos los valores, podrán ser "subidos" xD, hacia la RP dónde se tiene el listado de la estructura que debría tener un objeto variable, que cabe resaltar, contiene el número esperado de variables xD
        this.tipo = tipo;
        this.nombre = nombre;
        this.funcion = funcion;
    }
    
    public void setTipo(String tipo){
        this.tipo = tipo;
    }   
    
    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getFuncion() {
        return funcion;
    }
    
    //asString útil para formar subconjunto de JSON
    public String asString(){
        return "{ Nombre: \"" + this.nombre + "\","
             + " Tipo: \"" +this.tipo + "\","
             + " Funcion: \"" + "\""+ this.funcion+"\" }";        
    }
}

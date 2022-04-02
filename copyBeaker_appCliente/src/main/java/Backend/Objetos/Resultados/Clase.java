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
public class Clase implements Serializable{
    private String nombre;

    public Clase(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return this.nombre;
    }    
    
    public String asString(){
        return "{ Nombre: \""+nombre+"\" }";
    }//será útil cuando en los rep, solicite un objeto o para armar la lista solicitada
}

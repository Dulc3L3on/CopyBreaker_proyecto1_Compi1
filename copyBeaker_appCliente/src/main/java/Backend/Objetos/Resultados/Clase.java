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
    
    //esto es para crea el JSON, porque los reportes, req el valor en sí, sin formatos xD
    public String asString(){
        return "{ Nombre: \""+nombre+"\" }";
    }//puesto que cunado piden RESULT.Clase, piden el obj, entonces soy libre de mostrarlo así xD
    
}

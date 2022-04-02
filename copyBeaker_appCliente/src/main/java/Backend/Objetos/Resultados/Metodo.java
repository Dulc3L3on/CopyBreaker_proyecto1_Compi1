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
public class Metodo implements Serializable{
    private final String tipo;
    private final String nombre;        
    private final int numeroParametros;
    
    public Metodo(String tipo, String nombre, int numeroParametros){
        this.tipo = tipo;
        this.nombre = nombre;        
        this.numeroParametros = numeroParametros;
    }

    public String getTipo() {
        return tipo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public int getNumeroParametros(){
        return this.numeroParametros;
    }
    
    public String asString(){
        return "{ Nombre: \""+ this.nombre + "\", "
              + "Tipo: \"" + this.tipo + "\","
              + "Parametros: " + this.numeroParametros + " }";
    }
}

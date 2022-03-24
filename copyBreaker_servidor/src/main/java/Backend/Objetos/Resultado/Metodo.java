/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.Resultado;

import java.util.ArrayList;

/**
 *
 * @author phily
 */
public class Metodo {
    private String tipo;
    private String nombre;    
    private ArrayList<Variable> parametros;//a estos obj simplemente no se les seteará la función puesto que es obvi de donde son xD
    private int numeroParametros;
    
    public Metodo(String tipo, String nombre, ArrayList<Variable> parametros){
        this.tipo = tipo;
        this.nombre = nombre;        
        this.parametros = parametros;
        this.numeroParametros = this.parametros.size();
    }
    
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

    public ArrayList<Variable> getParametros() {
        return parametros;
    }
    
    public int getNumeroParametros(){
        return this.numeroParametros;
    }
    
    public String asString(){
        return "{ Nombre: \""+ this.nombre + "\", "
              + "Tipo: \"" + this.tipo + "\","
              + "Parametros: " + "\"" + String.valueOf(this.parametros.size())+"\" }";
    }
    
}

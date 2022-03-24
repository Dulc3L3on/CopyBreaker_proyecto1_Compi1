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
    private String nombre;
    private String tipo;
    private ArrayList<Variable> parametros;//a estos obj simplemente no se les seteará la función puesto que es obvi xD
    
    public Metodo(String nombre, String tipo, ArrayList<Variable> parametros){
        this.nombre = nombre;
        this.tipo = tipo;
        this.parametros = parametros;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public ArrayList<Variable> getParametros() {
        return parametros;
    }
    
    public String asString(){
        return "Nombre: \""+ this.nombre + "\", "
              + "Tipo: \"" + this.tipo + "\","
              + "Parametros: " + String.valueOf(this.parametros.size());
    }
    
}

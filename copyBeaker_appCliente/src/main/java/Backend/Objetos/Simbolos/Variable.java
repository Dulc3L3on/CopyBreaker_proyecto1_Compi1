/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.Simbolos;

/**
 *
 * @author phily
 */
public class Variable {
    private String tipo;
    private String nombre;
    private String valor;
    
    
    public Variable(String tipo, String nombre, String valor){
        this.tipo = tipo;
        this.nombre = nombre;
        this.valor = valor;
    }
    
    public String getTipo(){
        return this.tipo;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public String getValor(){
        return this.valor;
    }
    
}

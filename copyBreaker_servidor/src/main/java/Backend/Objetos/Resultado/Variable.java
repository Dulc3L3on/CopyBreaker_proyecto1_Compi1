/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.Resultado;

/**
 *
 * @author phily
 */
public class Variable {
    private String nombre;
    private String tipo;
    private String funcion;//ámbito de uso xD
    
    public Variable(String nombre, String tipo){
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    public void setFuncion(String funcion){
        this.funcion = funcion;
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
        return "Nombre: \"" + this.nombre + "\","
             + " Tipo: \"" +this.tipo + "\","
             + " Funcion: \"" + this.funcion;        
    }

}

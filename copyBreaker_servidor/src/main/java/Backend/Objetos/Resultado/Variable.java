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
    private String tipo;
    private String nombre;    
    private String funcion = "";//ámbito de uso xD
    
    public Variable(String tipo, String nombre){        
        this.tipo = tipo;
        this.nombre = nombre;
    }
    
    
    //Este será útil al momento de formar la lista de variables
    //a partir de las que se encuentren en la clase actual del 
    //proyecto2, para así no tener que estar formando cada vez 
    //el string que muestra los lugares donde se les da uso a
    //estas variables
    public Variable(String nombre, String tipo, String funcion){        
        this.tipo = tipo;
        this.nombre = nombre;
        this.funcion = funcion;
    }
    
    public void setFuncion(String funcion){
        this.funcion += " "+funcion;
    }//puesto que se debe tener todas las apariciones en el caso de las var del proy2...

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

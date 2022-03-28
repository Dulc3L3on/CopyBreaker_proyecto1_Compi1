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
public class Variable implements Serializable{
    private String tipo;
    private String nombre;    
    private String funcion = "";//ámbito de uso xD
   
    public Variable(String nombre){//esto es por las variables de tipo numéricas, como el cuepro lo comparten ambas...
        //no sé que hacer con las var que se crean en for, add a la función que se creó en for y aparte el nombre del método?? o solo dejo el nombre del método??, parecería error si solo dejo el método pero ahora todo depende de la facilidad...
        this.nombre = nombre;
    }
    
    public Variable(String tipo, String nombre){        
        this.tipo = tipo;
        this.nombre = nombre;
    }    
    
    //Se emplea al formar la variable que se add al result fnla
    //que tiene la función de la var de la clase1 y el de la var
    //De match...
    public Variable(String tipo, String nombre, String funcion){        
        this.tipo = tipo;
        this.nombre = nombre;
        this.funcion = funcion;
    }//y tb por si acaso decides hacer lo del for que preguntaba arribita...
    
    public void setTipo(String tipo){
        this.tipo = tipo;
    }   
    
    public void setFuncion(String separador, String funcion){
        this.funcion += separador+funcion;//por si acaso vas a setear que se creó en un ciclo, p.ej
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

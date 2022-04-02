/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.Resultado;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author phily
 */
public class Metodo implements Serializable{
    private final String tipo;
    private final String nombre;    
    private ArrayList<Variable> parametros;//a estos obj simplemente no se les seteará la función puesto que es obvi de donde son xD
    private int numeroParametros;
    
    public Metodo(String tipo, String nombre, ArrayList<Variable> parametros){
        this.tipo = tipo;
        this.nombre = nombre;        
        this.parametros = parametros;
        this.numeroParametros = this.parametros.size();
    }

    //este se va a utilizar cuando se cree un obj Método para addlo al RESULT
    //final [el que queda después de haber hecho las comparaciones]
    public Metodo(String tipo, String nombre, int numeroParametros){
        this.tipo = tipo;
        this.nombre = nombre;        
        this.numeroParametros = numeroParametros;
    }
    
    public void inicializarParametros(){
        this.parametros = new ArrayList<>();
    }//será útil en caso se req add paramas usando 2do cnstrc... aunque para el contexto del proy, esto no se utilizará xD

    public String getTipo() {
        return tipo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public ArrayList<Variable> getParametros() {
        return parametros;
    }//devolverá null cuando se use el 2do cnstrct, pensaba no crear una nueva lista, porque sería en vano y porque eso podría ser posible al usar el 1er cnstrct, pero quizá sea mejor así...
    
    public int getNumeroParametros(){
        return this.numeroParametros;
    }
    
    public String asString(){
        return "{ \"Nombre\": \""+ this.nombre + "\", "
              + "\"Tipo\": \"" + this.tipo + "\","
              + "\"Parametros\": " + numeroParametros + " }";//puesto que cuando el objeto sea creado con el cnstruct 2, el listado será nulo
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.Resultados;

import java.util.ArrayList;

/**
 *
 * @author phily
 */
public class RESULT {        
    private String score = "";
    private ArrayList<Clase> clases;
    private ArrayList<Comentario> comentarios;
    private ArrayList<Variable> variables;
    private ArrayList<Metodo> metodos;    

    public RESULT(){    
        this.clases = new ArrayList<>();
        this.comentarios = new ArrayList<>();
        this.variables = new ArrayList<>();
        this.metodos = new ArrayList<>();
    }    
    
    public void addClase(Clase clase){
        this.clases.add(clase);
    }
    
    public void addComentario(Comentario comentario){
        this.comentarios.add(comentario);
    }
    
    public void addVariable(Variable variable){
        this.variables.add(variable);
    }
    
    public void addMetodo(Metodo metodo){
        this.metodos.add(metodo);
    }

    public void setScore(String score){
        this.score = score;
    }   
    
    //estos getter individuales, serán útiles para cuando soliciten ya sea UN objeto o alguno de los atrib de dicho objeto
    public Clase getClase(int posicion){
        return this.clases.get(posicion);
    }    

    public Comentario getComentario(int posicion){
        return this.comentarios.get(posicion);
    }    

    public Variable getVariable(int posicion) {
        return this.variables.get(posicion);
    }    

    public Metodo getMetodo(int posicion){
        return this.metodos.get(posicion);
    }        

    public String getScore() {
        return this.score;
    }    

    public int getNumeroClases(){
        return this.clases.size();
    }
    
    public int getNumeroComentarios(){
        return this.comentarios.size();
    }
    
    public int getNumeroVariables(){
        return this.variables.size();
    }
    
    public int getNumeroMetodos(){
        return this.metodos.size();
    }
    
    //aquí en el cliente, esto solo me será útil para los objetos que soliciten en los Reportes xD
    public String getClassObjects(){
        String clasesRepetidas = "";
        
        for (Clase clase : clases) {
            clasesRepetidas += clase.asString() + "\n";//puesto que ya tienen incluidos las {} xD
        }        
        return clasesRepetidas;
    }
    
    public String getCommentsObjects(){
        String cometariosRepetidos = "";
        
        for (Comentario comentario : comentarios) {
            cometariosRepetidos += comentario.asString() + "\n";//puesto que ya tienen incluidos las {} xD
        }        
        return cometariosRepetidos;
    }
    
    public String getVariablesObjects(){
        String variablesRepetidas = "";
        
        for (Variable variable : variables) {
            variablesRepetidas += variable.asString() + "\n";//puesto que ya tienen incluidos las {} xD
        }        
        return variablesRepetidas;
    }
    
    public String getMethodObjects(){
        String metodosRepetidos = "";
        
        for (Metodo metodo : metodos) {
            metodosRepetidos += metodo.asString() + "\n";//puesto que ya tienen incluidos las {} xD
        }        
        return metodosRepetidos;
    }
    
    public boolean isEpmty(){
        return (this.score.isBlank());
    }//ya no tiene mucho sentido xD xD
}

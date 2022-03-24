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
public class RESULT {
    private double score = 0;
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

    public void addSubScore(int repetidos, int total){
        if(total > 0){
            this.score += ((repetidos/total)*0.25);
        }        
    }
    
    public Clase getClase(int posicion){
        return this.clases.get(posicion);
    }
    
    public ArrayList<Clase> getClases() {
        return clases;
    }

    public Comentario getComentario(int posicion){
        return this.comentarios.get(posicion);
    }
    
    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public Variable getVariable(int posicion) {
        return this.variables.get(posicion);
    }
    
    public ArrayList<Variable> getVariables(){
        return this.variables;
    }

    public Metodo getMetodo(int posicion){
        return this.metodos.get(posicion);
    }
    
    public ArrayList<Metodo> getMetodos(){
        return this.metodos;
    }    

    public double getScore() {
        return this.score;
    }        
    
    public String getClassObjects(boolean esParaReporte){
        String clasesRepetidas = "";
        
        for (Clase clase : clases) {
            clasesRepetidas += clase.asString() + ((esParaReporte)?"\n":"");//puesto que ya tienen incluidos las {} xD
        }        
        return clasesRepetidas;
    }
    
    public String getCommentsObjects(boolean esParaReporte){
        String cometariosRepetidos = "";
        
        for (Comentario comentario : comentarios) {
            cometariosRepetidos += comentario.asString() + ((esParaReporte)?"\n":"");//puesto que ya tienen incluidos las {} xD
        }        
        return cometariosRepetidos;
    }
    
    public String getVariablesObjects(boolean esParaReporte){
        String variablesRepetidas = "";
        
        for (Variable variable : variables) {
            variablesRepetidas += variable.asString() + ((esParaReporte)?"\n":"");//puesto que ya tienen incluidos las {} xD
        }        
        return variablesRepetidas;
    }
    
    public String getMethodObjects(boolean esParaReporte){
        String metodosRepetidos = "";
        
        for (Metodo metodo : metodos) {
            metodosRepetidos += metodo.asString() + ((esParaReporte)?"\n":"");//puesto que ya tienen incluidos las {} xD
        }        
        return metodosRepetidos;
    }
    
}//a partir de esta clase se crerá el JSON, otambién lo que podrías hacer es que de forma simultánea
//escribes en el JSON mientras formas el RESULT, pero veo más organizado por así decirlo, que crees el 
//JSON a partir de éste, ya sea cuando esté totalmente formado o cuando se esté formando
    //yo diría que después de addle el ele al result, se pida que el RESULT exe su método getClases
    //pero la cuestión son los {}... ya veremos cómo xD, pero la base es esta xD

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
public class RESULT implements Serializable{    
    private String JSON;//para que así no haya problemas al momento de enviar la respuesta hacia el cliente...
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
    
    public void setJSON(String JSON){
        this.JSON = JSON;
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
    
    public String getJSON(){
        return this.JSON;
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
        
        for (int actual = 0; actual < (clases.size()-1); actual++) {
            clasesRepetidas += ((esParaReporte)?"\t\t\t":"")+clases.get(actual).asString()
                    + ((esParaReporte)?"\n":",\n");//puesto que ya tienen incluidos las {} xD
        }                       
        return (clasesRepetidas+= ((esParaReporte)?"\t\t\t":"") + clases.get((clases.size()-1)));
    }
    
    public String getCommentsObjects(boolean esParaReporte){
        String cometariosRepetidos = "";
        
        for (int actual = 0; actual < (comentarios.size()-1); actual++) {
            cometariosRepetidos += ((esParaReporte)?"\t\t\t":"") + comentarios.get(actual).asString()
                    + ((esParaReporte)?"\n":",\n");//puesto que ya tienen incluidos las {} xD
        }        
        return (cometariosRepetidos+= ((esParaReporte)?"\t\t\t":"") + comentarios.get((comentarios.size()-1)));
    }
    
    public String getVariablesObjects(boolean esParaReporte){
        String variablesRepetidas = "";
        
        for (int actual = 0; actual < (variables.size()-1); actual++) {
            variablesRepetidas += ((esParaReporte)?"\t\t\t":"") + variables.get(actual).asString()
                    + ((esParaReporte)?"\n":",\n");//puesto que ya tienen incluidos las {} xD
        }        
        return (variablesRepetidas += ((esParaReporte)?"\t\t\t":"") + (variables.get((variables.size()-1))));
    }
    
    public String getMethodObjects(boolean esParaReporte){
        String metodosRepetidos = "";
        
        for (int actual = 0; actual < (metodos.size()-1); actual++) {
            metodosRepetidos += ((esParaReporte)?"\t\t\t":"") + metodos.get(actual).asString()
                    + ((esParaReporte)?"\n":",\n");//puesto que ya tienen incluidos las {} xD
        }        
        return (metodosRepetidos += ((esParaReporte)?"\t\t\t":"") + (metodos.get((metodos.size()-1))));
    }
    
    public boolean isEpmty(){
        return (this.score==0);
    }
    
}//a partir de esta clase se crerá el JSON, otambién lo que podrías hacer es que de forma simultánea
//escribes en el JSON mientras formas el RESULT, pero veo más organizado por así decirlo, que crees el 
//JSON a partir de éste, ya sea cuando esté totalmente formado o cuando se esté formando
    //yo diría que después de addle el ele al result, se pida que el RESULT exe su método getClases
    //pero la cuestión son los {}... ya veremos cómo xD, pero la base es esta xD

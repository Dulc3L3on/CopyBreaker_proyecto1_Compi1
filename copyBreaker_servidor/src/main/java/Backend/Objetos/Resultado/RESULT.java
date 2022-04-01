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
    //private String JSON;//ya no será nec, puesto que se enviará el JSON, no el RESULT, para evitar tener clases exactas...
    private double score = 0.000;
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

    public void addSubScore(double repetidos, double total){
        if(total > 0){
            double scoreParcial = ((repetidos/total)*0.25);
            this.score += Math.round(scoreParcial*1000d)/1000d;
            //this.score += (repetidos/total)*0.25d;//con esta daba 0, por no tener a todos los números en double, entonces los decimales se truncaban xD
            System.out.println("score: "+score);
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
    
    public String getClassObjects(){
        String clasesRepetidas = "";
        
        if(!clases.isEmpty()){
            for (int actual = 0; actual < (clases.size()-1); actual++) {
                clasesRepetidas += "\t"+clases.get(actual).asString()
                        + ",\n";//puesto que ya tienen incluidos las {} xD
            }                       
            return (clasesRepetidas+= "\t" + (clases.get((clases.size()-1)).asString()));
        }
        return clasesRepetidas;
    }
    
    public String getCommentsObjects(){
        String comentariosRepetidos = "";
        
        if(!comentarios.isEmpty()){
            for (int actual = 0; actual < (comentarios.size()-1); actual++) {
                comentariosRepetidos += "\t" + comentarios.get(actual).asString()
                        + ",\n";//puesto que ya tienen incluidos las {} xD
            }        
            return (comentariosRepetidos+= "\t" + (comentarios.get((comentarios.size()-1)).asString()));
        }
        return comentariosRepetidos;
    }
    
    public String getVariablesObjects(){
        String variablesRepetidas = "";
        
        if(!variables.isEmpty()){
            for (int actual = 0; actual < (variables.size()-1); actual++) {
                variablesRepetidas += "\t" + variables.get(actual).asString()
                        + ",\n";//puesto que ya tienen incluidos las {} xD
            }        
            return (variablesRepetidas += "\t" + (variables.get((variables.size()-1))).asString());
        }
        return variablesRepetidas;
    }
    
    public String getMethodObjects(){
        String metodosRepetidos = "";
        
        if(!metodos.isEmpty()){
            for (int actual = 0; actual < (metodos.size()-1); actual++) {
                metodosRepetidos += "\t" + metodos.get(actual).asString()
                        + ",\n";//puesto que ya tienen incluidos las {} xD
            }        
            return (metodosRepetidos += "\t" + (metodos.get((metodos.size()-1)).asString()));
        }
        return metodosRepetidos;
    }
    
    public boolean isEpmty(){
        return (this.score==0);
    }
    
}//a partir de esta clase se crerá el JSON, otambién lo que podrías hacer es que de forma simultánea
//escribes en el JSON mientras formas el RESULT, pero veo más organizado por así decirlo, que crees el 
//JSON a partir de éste, ya sea cuando esté totalmente formado o cuando se esté formando
    //yo diría que después de addle el ele al result, se pida que el RESULT exe su método getClases
    //pero la cuestión son los {}... ya veremos cómo xD, pero la base es esta xD

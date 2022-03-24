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
    private int score;
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

    public int getScore() {
        return score;
    }

    public Clase getClase(int posicion){
        return clases.get(posicion);
    }

    public Comentario getComentario(int posicion){
        return comentarios.get(posicion);
    }

    public Variable getVariable(int posicion) {
        return variables.get(posicion);
    }

    public Metodo getMetodo(int posicion){
        return metodos.get(posicion);
    }
    
    public ArrayList<Metodo> getMetodos(){
        return metodos;
    }
        
    public String getClases_RESULT(){
        String clasesRepetidas = "";
        
        for (int index = 0; index < clases.size(); index++) {
            clasesRepetidas += "["+(index+1)+"] " + clases.get(index).asString() + "\n";//quizá deba colocarse o hacerse directamente en el html, por el br, y la forma en que se especificará cómo se mostrarán los datos...
        }        
        return clasesRepetidas;
    }    
    
    public String getComentarios_RESULT(){
        String comentariosRepetidos = "";
        
        for (int index = 0; index < comentarios.size(); index++) {
            comentariosRepetidos += "["+(index+1)+"] " + comentarios.get(index).asString() + "\n";//quizá deba colocarse o hacerse directamente en el html, por el br, y la forma en que se especificará cómo se mostrarán los datos...
        }        
        return comentariosRepetidos;
    }
    
    public String getVariables_RESULT(){
        String variablesRepetidas = "";
        
        for (int index = 0; index < variables.size(); index++) {
            variablesRepetidas += "["+(index+1)+"] " + variables.get(index).asString() + "\n";//quizá deba colocarse o hacerse directamente en el html, por el br, y la forma en que se especificará cómo se mostrarán los datos...
        }        
        return variablesRepetidas;
    }
    
    public String getMetodos_RESULT(){
        String metodosRepetidos = "";
        
        for (int index = 0; index < metodos.size(); index++) {
            metodosRepetidos += "["+(index+1)+"] " + metodos.get(index).asString() + "\n";//quizá deba colocarse o hacerse directamente en el html, por el br, y la forma en que se especificará cómo se mostrarán los datos...
        }        
        return metodosRepetidos;
    }

    //método para formar el JSON
    public String getClases_JSON(){
        String clasesRepetidas = "";
        
        for (Clase clase : clases) {
            clasesRepetidas += clase.asString();//puesto que ya tienen incluidos las {} xD
        }        
        return clasesRepetidas;
    }
    
    public String getComentarios_JSON(){
        String cometariosRepetidos = "";
        
        for (Comentario comentario : comentarios) {
            cometariosRepetidos += comentario.asString();//puesto que ya tienen incluidos las {} xD
        }        
        return cometariosRepetidos;
    }
    
    public String getVariables_JSON(){
        String variablesRepetidas = "";
        
        for (Variable variable : variables) {
            variablesRepetidas += variable.asString();//puesto que ya tienen incluidos las {} xD
        }        
        return variablesRepetidas;
    }
    
    public String getMetodos_JSON(){
        String metodosRepetidos = "";
        
        for (Metodo metodo : metodos) {
            metodosRepetidos += metodo.asString();//puesto que ya tienen incluidos las {} xD
        }        
        return metodosRepetidos;
    }
    
}//a partir de esta clase se crerá el JSON, otambién lo que podrías hacer es que de forma simultánea
//escribes en el JSON mientras formas el RESULT, pero veo más organizado por así decirlo, que crees el 
//JSON a partir de éste, ya sea cuando esté totalmente formado o cuando se esté formando
    //yo diría que después de addle el ele al result, se pida que el RESULT exe su método getClases
    //pero la cuestión son los {}... ya veremos cómo xD, pero la base es esta xD

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.Resultado.Clase;
import Backend.Objetos.Resultado.Comentario;
import Backend.Objetos.Resultado.Metodo;
import Backend.Objetos.Resultado.RESULT;
import Backend.Objetos.Resultado.Variable;
import java.util.ArrayList;

/**
 *
 * @author phily
 */
public class EscritorJSON {//Esta clase no será útil en el cliente, puesto que se va a escribir a partir de la gramática
    private String JSON ="";//Aquí la estructura que engloba todo [bueno al menos es inicio xD... así como hicsite en la práctica de EDD del año pasado...    
        
    public void escribirJSON(RESULT result){
        
        JSON = "{\n";
        
        this.addScore(result.getScore());
        this.addClases(result.getClassObjects(false));        
        this.addVariables(result.getVariablesObjects(false));
        this.addMetodos(result.getMethodObjects(false));
        this.addComentarios(result.getCommentsObjects(false));
        
        JSON += "}";
    }
    
    private void addScore(double score){
        JSON += "\tScore: \""+score+"\",\n";
    }
    
    private void addClases(String clases){
        JSON += "\tClases: [\n";                
        JSON += clases;                
        JSON += "\t\t\n],\n";
    }
    
    private void addVariables(String variables){
        JSON += "Variables: [\n";        
        JSON += variables;
        JSON += "\t\t\n],\n";
    }
    
    private void addMetodos(String metodos){
        JSON += "Metodos: [\n";
        JSON += metodos;
        JSON += "\t\t\n],\n";
    }
    
    private void addComentarios(String comentarios){
        JSON += "Comentarios: [\n";
        JSON += comentarios;
        JSON += "\t\t\n],\n";
    }
    
    public String getJSON(){
        return this.JSON;
    }
    
}

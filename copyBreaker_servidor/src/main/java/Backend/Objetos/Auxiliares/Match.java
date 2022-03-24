/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.Auxiliares;

import Backend.Objetos.Resultado.Comentario;
import Backend.Objetos.Resultado.Variable;

/**
 *
 * @author phily
 */
public class Match {
    private Variable variable;
    private Comentario comentario;
    private int involucrados = 0;
    private boolean utilizado;
    
    public Match(Variable laVariable){
        this.variable = laVariable;
    }
    
    public Match(Comentario elComentario){
        this.comentario = elComentario;
    }
    
    public void addInvolucrado(){
        this.involucrados++;
    }
    
    public void setUtilizado(){
        this.utilizado = true;
    }

    public Variable getVariable() {
        return variable;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public int getInvolucrados() {
        return involucrados;
    }

    public boolean fueUtilizado() {
        return utilizado;
    }   
    
}

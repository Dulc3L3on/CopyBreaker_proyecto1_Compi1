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
public class Comparador {
    
    //si alguno de los criterios son TRUE, se devolverá un nuevo objeto Clase
    //con los datos corresp y las fusiones nec, para ser add al obj RESULT_Repintencia
    //Creado en el analizadorRepitencia [de forma eqq con las demás estructuras
    
    public Clase compararClases(RESULT resultClase1, RESULT resultClase2){
        if(resultClase1.getClase(0).getNombre().equals(resultClase2.getClase(0).getNombre())){
            return new Clase(resultClase1.getClase(0).getNombre());
        }
        
        else if(compararMetodosClase(resultClase1.getMetodos(),resultClase2.getMetodos())){
            return new Clase(resultClase1.getClase(0).getNombre());
        }        
        
        return null;
    }
    
    private boolean compararMetodosClase(ArrayList<Metodo> listaRevisada, ArrayList<Metodo> listaRevisora){//no importa cual se envíe a cual, puesto que no se sobreapasará de sus índices...
        for (int actualRevisada = 0; actualRevisada < listaRevisada.size(); actualRevisada++) {
            for (int actualRevisora = 0; actualRevisora < listaRevisora.size(); actualRevisora++) {
                if(listaRevisada.get(actualRevisada).getNombre().equals(listaRevisora.get(actualRevisora).getNombre())){
                    return true;//si es que no nec deben tener solo esos métodos repetidos basta con 1 que sea igual, de lo contrario si deben tener iguales los métodos en número y nombre, revisarás primero si son del mismo tamaño, sino entonces false, si si entrarás a este método para ver si TODOS son iguales y así devolver TRUE
                }
            }
        }
        return false;
    }
    
    public Comentario compararComentarios(Comentario comentario1, Comentario comentario2){
        if(comentario1.getTexto().equals(comentario2.getTexto())){
            return new Comentario(comentario1.getTexto());
        }
        
        return null;
    }
    
    public Variable compararVariables(Variable variable1, Variable variable2){
    
        
        return null;
    }
    
    public Metodo compararMetodo(Metodo metodo1, Metodo metodo2){
        
        return null;
    }
    
}

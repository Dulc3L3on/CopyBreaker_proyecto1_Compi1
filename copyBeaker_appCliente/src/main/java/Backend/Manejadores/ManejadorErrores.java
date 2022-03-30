/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.Enums.LexerError;
import java.util.ArrayList;
import Backend.Objetos.Error;
import Backend.Objetos.Token;

/**
 *
 * @author phily
 */
public class ManejadorErrores {
    private ManejadorErroresExtra manejadorErroresExtra;        
    ArrayList<Error> listaErrores;
    
    public ManejadorErrores(){
        listaErrores = new ArrayList<>();
        this.manejadorErroresExtra = new ManejadorErroresExtra();
    }
    
    /**
     * Creado para los errores que se puedan generar
     * en el lexer
     * @param error
     */
    public void setError(Token error, boolean esPorCadena){
        if(!esPorCadena){
            String coincidencia = this.manejadorErroresExtra.esReservadaMalFormada(error);
        
            if(coincidencia != null){
                this.listaErrores.add(new Error(error.getLexema(), error.getLinea(),
                  error.getColumna(), LexerError.MAYBE_YOU_MEANT, coincidencia));
            }else{
               this.listaErrores.add(new Error(error.getLexema(), error.getLinea(),
                  error.getColumna(), LexerError.INVALID_WORD, ""));
            }
        }else{
            this.listaErrores.add(new Error(error.getLexema(), error.getLinea(),
                  error.getColumna(), LexerError.UNCLOSE_STRING, ""));
        }        
    }

    /**
     * Creado para los errores que se puedan generar
     * en el parser
     * @param error
     */
    public void setError(Error error) {
        this.listaErrores.add(error);
    }
    
    public ArrayList<Error> getListaErrores() {
        return listaErrores;
    } 
}

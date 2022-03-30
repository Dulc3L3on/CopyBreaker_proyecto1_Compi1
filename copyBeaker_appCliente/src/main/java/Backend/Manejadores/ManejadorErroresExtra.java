/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.Token;

/**
 *
 * @author phily
 */
public class ManejadorErroresExtra {
    String[] palabrasReservadas = new String[]{"score, clases, variables, metodos,"
                                             + "comentarios, nombre, tipo, funcion"
                                             + "parametros, texto"};    
    
    public String esReservadaMalFormada(Token error){
       if(error.getLexema().length()>1){//pongo esto aunque yo diría que si entró a error, por el hecho de ingnorar los espacios en blanco, debería ser > 1 :v xD
            for (String reservada : palabrasReservadas) {
                if(reservada.length()*0.7 <= error.getLexema().length()){//pues si es más pequeña la palabra errada, no tiene sentido revisar...
                    if((error.getLexema().toLowerCase()).contains(reservada.substring(0, 
                            ((reservada.length() == 2)?2:(int) Math.ceil(reservada.length()*0.7))))){//más que todo por el if y el int, sino se recomendaría mal...
                        
                        return reservada;
                    }
                }
            }
       }
       return null;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import java.util.ArrayList;
import Backend.Objetos.Error;

/**
 *
 * @author phily
 */
public class ManejadorErrores {
    
    ArrayList<Error> listaErrores = new ArrayList<>();
    
    public ManejadorErrores(){
        listaErrores = new ArrayList<>();
    }

    public void setError(Error error) {
        this.listaErrores.add(error);
    }
    
    public ArrayList<Error> getListaErrores() {
        return listaErrores;
    }    
    
}

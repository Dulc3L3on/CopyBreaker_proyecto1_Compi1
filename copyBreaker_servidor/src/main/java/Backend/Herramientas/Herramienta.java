/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Herramientas;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author phily
 */
public class Herramienta {
    
    public String[] getNombreArchivos(ArrayList<File> listaArchivos){
        String[] listaNombres = new String[listaArchivos.size()];
        
        for (int actual = 0; actual < listaArchivos.size(); actual++) {
            listaNombres[actual] = listaArchivos.get(actual).getName();
        }        
        return listaNombres;
    }
    
}

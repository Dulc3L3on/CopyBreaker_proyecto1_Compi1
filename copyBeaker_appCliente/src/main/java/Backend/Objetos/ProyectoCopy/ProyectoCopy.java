/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.ProyectoCopy;

import java.io.Serializable;

/**
 *
 * @author phily
 */
public class ProyectoCopy implements Serializable{
    private final String root;
    private Archivo archivoJSON;
    private Archivo archivoDEF;
    
    public ProyectoCopy(String root, Archivo archivoJSON, Archivo archicoDEF){
        this.root = root;
        this.archivoJSON = archivoJSON;
        this.archivoDEF = archicoDEF;
    }
    
    public void resetContent(String newContent, boolean enJSON){
        if(enJSON){
            this.archivoJSON.saveContent(newContent);
        }else{
            this.archivoDEF.saveContent(newContent);
        }
    }    
    
    public Archivo getJSON(){
        return this.archivoJSON;
    }
    
    public Archivo getDEF(){
        return this.archivoDEF;
    }
    
}

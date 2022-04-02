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
public class Archivo implements Serializable{
    private final String absolutePath;
    private final String name;
    private String contenido;    
    
    /**
     * Se empleará tanto para el .def como el
     * JSON, para el caso del .def se inicializará
     * con etiquetas generales, para que pueda comenzar a escribir...
     * @param absolutePath
     * @param name
     * @param contenido
     */
    public Archivo(String absolutePath, String name, String contenido){
        this.absolutePath = absolutePath;
        this.name = name;
        this.contenido = contenido;
    }
    
    public void saveContent(String newContent){
        this.contenido = newContent;
    }
    
    public String getPath(){
        return this.absolutePath;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getContenido(){
        return this.contenido;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author phily
 */
public class ManejadorArchivos {
    private final String ACCEPTED_EXTENSION = ".java";
    private ArrayList<File> listaArchivos = null;//lo pongo null, porque la carpeta bien podría estar vacía, entonces que eso suceda no tiene por qué evitarse el proceso de análisis, porque en realidad ya cargo la carpeta, solo que esa estaba vacía... xD
    private String[] nombreArchivos;
    
    public void setFiles(){
        this.listaArchivos = new ArrayList<>();
        File archivo = new File(this.getPath());
        
        if(archivo.isDirectory()){            
            File[] archivos = archivo.listFiles();
            this.getAllFiles(archivos);
            this.setNombreArchivos(listaArchivos);
        }
    }
    
    private String getPath(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona la carpeta del Proyecto");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int respuesta = fileChooser.showOpenDialog(null);
        return (respuesta == JFileChooser.APPROVE_OPTION ?String.format("%1$s/", fileChooser.getSelectedFile()):null);
    }
    
    private void getAllFiles(File[] archivos){
        for (File archivo : archivos) {
            if(!archivo.isDirectory()){
                if(archivo.isFile() && archivo.getName().endsWith(ACCEPTED_EXTENSION)){
                    listaArchivos.add(archivo);
                }
            }else{
                File[] auxiliar = new File(archivo.getAbsolutePath()).listFiles();
                getAllFiles(auxiliar);
            }
        }
    }//permite obtener todos los archivos, sin importar que aparezcan carpetas anidadas o no
    
    private boolean esExtensionAceptada(String laExtension){
        return (laExtension.endsWith(ACCEPTED_EXTENSION));    
    }
    
    private void setNombreArchivos(ArrayList<File> listaArchivos){
        nombreArchivos = new String[listaArchivos.size()];//Si un arreglo es de tamaño 0, es nulo??? NO xD, comprobado jaja
        
        for (int actual = 0; actual < listaArchivos.size(); actual++) {
             nombreArchivos[actual] = listaArchivos.get(actual).getName();                         
        }                
    }//aquí en el cliente será útil para llenar los JList del proceso de carga...
    
    public String getFileAsString(int indice){
        String string = "";//para que no haya problemas al momento de mostrarlo en el txtA...
        
        if(!this.listaArchivos.isEmpty()){            
            try {            
                string =  Files.readString(this.listaArchivos.get(indice).toPath());
            } catch (IOException ex) {
                System.out.println("filesAsSring: Archivo no encontrado");
            }
        }        
        return string;
    }
    
    public ArrayList<File> getFiles(){
        return this.listaArchivos;
    }
    
    public String[] getNombreArchivos(){
        return this.nombreArchivos;
    }
    
}

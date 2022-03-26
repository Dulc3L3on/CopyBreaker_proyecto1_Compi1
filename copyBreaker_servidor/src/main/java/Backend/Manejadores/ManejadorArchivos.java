/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author phily
 */
public class ManejadorArchivos {
    private final String ACCEPTED_EXTENSION = ".java";
    private ArrayList<File> listaArchivos;
    private String[] nombreArchivos;
    
    public ArrayList<File> getFiles(){
        this.listaArchivos = new ArrayList<>();
        File archivo = new File(this.getPath());
        
        if(archivo.isDirectory()){            
            File[] archivos = archivo.listFiles();
            this.getAllFiles(archivos);
            this.getNombreArchivos(listaArchivos);
        }
                 
        return listaArchivos;
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
    
    private String[] getNombreArchivos(ArrayList<File> listaArchivos){
        nombreArchivos = new String[listaArchivos.size()];
        
        for (int actual = 0; actual < listaArchivos.size(); actual++) {
             nombreArchivos[actual] = listaArchivos.get(actual).getName().replace(".java", "");//para eliminar así la extensión xD
             System.out.println("nombre clase: "+nombreArchivos[actual]);             
        }
        
        return nombreArchivos;
    }
    
    public String[] getNombreArchivos(){
        return this.nombreArchivos;
    }
    
}

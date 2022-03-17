/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import javax.swing.JFileChooser;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author phily
 */
public class ManejadorInterfaz {
    
      public void cambiarApariencia(){
        try{ 
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("GTK+".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            //javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName()); //aunque si jalaras el GTK+ también estaría genial y no cambiaría con SO xD
        }catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ){ 
           System.err.println("Imposible cargar interfaz, se usará la interfaz por defecto de Java.\nError: "+e);
        }         
    }          
    
    public String mostrarFileChooser(){
       JFileChooser fileChooser = new JFileChooser();        
        
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Solo archivos .java", new String[]{"java"});//esto para evitar que pueda seleccionar cualquier otra extesión que no sea la requerida
        fileChooser.setFileFilter(filtro);        
        
        if(fileChooser.showOpenDialog(fileChooser)==(JFileChooser.APPROVE_OPTION)){
            return fileChooser.getSelectedFile().getAbsolutePath();            
        }        
        return null;
      }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author phily
 */
public class ManejadorInterfaz {
    private JTextArea txtA_JSON;
    private JTextArea txtA_Reportes;
    
    public void setComponents(JTextArea txtA_JSON, JTextArea txtA_Reportes){//no lo hago en el cnstrct, porque al igual que en la appServidor, esta clase se ini en el cuerpo de la clase process, y para no tener que estar enviando parámetros que no tiene nada que ver con esa clase process, entonces mejor hago este set xD
        this.txtA_JSON = txtA_JSON;
        this.txtA_Reportes = txtA_Reportes;
    }    
    
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
    
    public void addArchivosALista(JList<String> lista, String[] archivosCargados){
        lista = new JList<>(archivosCargados);
        lista.updateUI();
    }  
      
    public void addResultados_JSON(String JSON){
        this.txtA_JSON.setText(JSON);
        this.txtA_JSON.updateUI();
    }
    
    
    public String abrirProyecto(){
       JFileChooser fileChooser = new JFileChooser();        
        
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //se va a poner una carpeta por defecto
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Solo archivos .java", new String[]{"java"});//esto para evitar que pueda seleccionar cualquier otra extesión que no sea la requerida
        fileChooser.setFileFilter(filtro);        
        
        if(fileChooser.showOpenDialog(fileChooser)==(JFileChooser.APPROVE_OPTION)){
            return fileChooser.getSelectedFile().getAbsolutePath();            
        }        
        return null;
      }
    
    
}

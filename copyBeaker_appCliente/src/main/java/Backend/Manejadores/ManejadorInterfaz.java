/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author phily
 */
public class ManejadorInterfaz {
    private ManejadorDeLinea manejadorDeLinea_JSON, manejadorDeLinea_reportes,
            manejadorLinea_Proyecto1, manejadorLinea_Proyecto2;
    private JTextArea txtA_Proyecto1, txtA_proyecto2, txtA_JSON, txtA_Reportes;
    
    
    public void setComponents(JScrollPane scroll_proyecto1, JTextArea txtA_proyecto1,
            JScrollPane scroll_proyecto2, JTextArea txtA_proyecto2,
            JScrollPane scroll_txtA_JSON, JTextArea txtA_JSON,
            JScrollPane scroll_txtA_Reportes, JTextArea txtA_Reportes){//no lo hago en el cnstrct, porque al igual que en la appServidor, esta clase se ini en el cuerpo de la clase process, y para no tener que estar enviando parámetros que no tiene nada que ver con esa clase process, entonces mejor hago este set xD
        this.txtA_JSON = txtA_JSON;
        this.txtA_Reportes = txtA_Reportes;
        this.txtA_Proyecto1 = txtA_proyecto1;
        this.txtA_proyecto2 = txtA_proyecto2;
        
        this.manejadorDeLinea_JSON = new ManejadorDeLinea(this.txtA_JSON);
        this.manejadorDeLinea_reportes = new ManejadorDeLinea(this.txtA_Reportes);
        this.manejadorLinea_Proyecto1 = new ManejadorDeLinea(txtA_proyecto1);
        this.manejadorLinea_Proyecto2 = new ManejadorDeLinea(txtA_proyecto2);
        
        scroll_txtA_JSON.setRowHeaderView(this.manejadorDeLinea_JSON);        
        scroll_txtA_Reportes.setRowHeaderView(this.manejadorDeLinea_reportes);        
        scroll_proyecto1.setRowHeaderView(this.manejadorLinea_Proyecto1);        
        scroll_proyecto2.setRowHeaderView(this.manejadorLinea_Proyecto2);
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
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        lista.setModel(modeloLista);
                
        for (String archivoCargado : archivosCargados) {
            modeloLista.addElement(archivoCargado);
        }        
    }  
      
    public void addResultados_JSON(String JSON){
        this.txtA_JSON.setText(JSON);
        this.txtA_JSON.updateUI();
    }
    
    public int getRow(){
        return 0;
    }
    
    public int getColumn(){
        return 0;
    }
    
    //ahora que lo pienso creo que para los reportes no hace falta tener un método a menos que te DIERA TIEMPO y decidieras hacer que se remarcaran los lugares con errores    
    
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Caret;
import Backend.Objetos.Error;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class ManejadorInterfaz {   
    private ManejadorDeLinea manejadorDeLinea_JSON, manejadorDeLinea_reportes,
            manejadorLinea_Proyecto1, manejadorLinea_Proyecto2;    
    private JTextArea txtA_Proyecto1, txtA_Proyecto2, txtA_JSON, txtA_Reportes;
    private JTable log_JSON, log_Reportes;
    private JButton btn_analizarJSON, btn_analizarReportes, btn_visualizarHTML;    
    
    public void setComponents(JScrollPane scroll_proyecto1, JTextArea txtA_proyecto1,
            JScrollPane scroll_proyecto2, JTextArea txtA_proyecto2, JScrollPane scroll_txtA_JSON,
            JTextArea txtA_JSON, JScrollPane scroll_txtA_Reportes, JTextArea txtA_Reportes,
            JTable log_JSON, JTable log_Reportes, JButton btn_analizarJSON, 
            JButton btn_analizarReportes, JButton btn_visualizarHTML){//no lo hago en el cnstrct, porque al igual que en la appServidor, esta clase se ini en el cuerpo de la clase process, y para no tener que estar enviando parámetros que no tiene nada que ver con esa clase process, entonces mejor hago este set xD               
        this.txtA_JSON = txtA_JSON;
        this.txtA_Reportes = txtA_Reportes;
        this.txtA_Proyecto1 = txtA_proyecto1;
        this.txtA_Proyecto2 = txtA_proyecto2;
        
        this.manejadorDeLinea_JSON = new ManejadorDeLinea(this.txtA_JSON);
        this.manejadorDeLinea_reportes = new ManejadorDeLinea(this.txtA_Reportes);
        this.manejadorLinea_Proyecto1 = new ManejadorDeLinea(txtA_Proyecto1);
        this.manejadorLinea_Proyecto2 = new ManejadorDeLinea(txtA_Proyecto2);
        
        scroll_txtA_JSON.setRowHeaderView(this.manejadorDeLinea_JSON);        
        scroll_txtA_Reportes.setRowHeaderView(this.manejadorDeLinea_reportes);        
        scroll_proyecto1.setRowHeaderView(this.manejadorLinea_Proyecto1);        
        scroll_proyecto2.setRowHeaderView(this.manejadorLinea_Proyecto2);
        
        this.log_JSON = log_JSON;
        this.log_Reportes = log_Reportes;
        
        this.btn_analizarJSON = btn_analizarJSON;
        this.btn_analizarReportes = btn_analizarReportes;
        this.btn_visualizarHTML = btn_visualizarHTML;
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
    
    public void addArchivosALista(JList<String> lista, String[] archivosCargados, JTextArea areaTexto){                 
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        lista.setModel(modeloLista);
                
        for (String archivoCargado : archivosCargados) {
            modeloLista.addElement(archivoCargado);
        }      
        
        areaTexto.setText("");
    }      
      
    public void addResultados(String resultados, boolean esJSON){
        if(esJSON){
            this.txtA_JSON.setText(resultados);
            this.txtA_JSON.updateUI();
        }else{
            this.txtA_Reportes.setText(resultados);
            this.txtA_Reportes.updateUI();
        }
    }
    
    public void addUbicacion(JTextArea areaTexto, JLabel etiquetaPosicion){
        try {
           int posicionCareta = areaTexto.getCaretPosition();
           int linea= areaTexto.getLineOfOffset(posicionCareta);
           int columna = posicionCareta - areaTexto.getLineStartOffset(linea);       
            
           etiquetaPosicion.setText("línea: "+ (linea+1) +"    columna: "+ (columna+1)+" ");
        } catch(Exception ex) { }
    }
    
    public void buscarTexto(JTextArea areaTexto, String textoTotal, String textoABuscar,
            int posicionBusqueda, boolean isLeftToRight){//el texto se enviará sin espacios antes y después, para hacer una mejor búsqueda xD        
        
        int posicion = ((isLeftToRight)?textoTotal.indexOf(textoABuscar, posicionBusqueda):textoTotal.lastIndexOf(textoABuscar));
     
        areaTexto.setCaretPosition((posicion==-1)?0:posicion);
        areaTexto.moveCaretPosition(((posicion==-1)?0:(posicion + textoABuscar.length())));
    }
    
    public void buscarSiguiente(JTextArea areaTexto, String textoABuscar){
        Caret seleccion = areaTexto.getCaret();        
        
        if (seleccion.getMark() != seleccion.getDot()&& seleccion.getDot()< areaTexto.getText().length()){// si es igual, quiere decir que no hay más texto por medio del cual se pueda hacer la búsqueda...
            this.buscarTexto(areaTexto, areaTexto.getText(), textoABuscar, seleccion.getDot(), true);
        }        
    }
    
    public void buscarAnterior(JTextArea areaTexto, String textoABuscar){//Este texto es el original...
        Caret seleccion = areaTexto.getCaret();        
        
        if (seleccion.getMark() != seleccion.getDot() && seleccion.getMark() > 0){// si es igual, quiere decir que no hay más texto por medio del cual se pueda hacer la búsqueda...
            this.buscarTexto(areaTexto, areaTexto.getText().substring(0, seleccion.getMark()),
                    textoABuscar, 0, false);
        }        
    }
    
    public void manipularSeccionReportes(boolean habilitar){
        this.txtA_Reportes.setEditable(habilitar);
        this.btn_analizarReportes.setEnabled(habilitar);
        this.btn_visualizarHTML.setEnabled(habilitar);
    }//ahora solo debes ver 
    
    public void setErrores(ArrayList<Error> errores, boolean esJSON){
        DefaultTableModel modelo=(DefaultTableModel) ((esJSON)?log_JSON.getModel():log_Reportes.getModel());        
        
        for (int elementoActual = 0; elementoActual < errores.size(); elementoActual++) {
             modelo.addRow(new Object[]{elementoActual+1,                
                String.valueOf(errores.get(elementoActual).getLinea()),
                String.valueOf(errores.get(elementoActual).getColumna()),
                errores.get(elementoActual).getErrorType(),
                errores.get(elementoActual).getLexema(),
                errores.get(elementoActual).getDescripcion()});
        }
        
        //log.updateUI();//ya no resulta necesario, luego de haver hecho la actualización con la limpieza, es decir los datos se muestran correctamente... si en dado caso no se llegaran a mostrar los rep, entonces envuelve esto en un try-cathc por el null, que no entiendo la razón por la que surge, pero no creo que vaya a haber problema si al debuggear, te diste cta que la tabla ya mostraba los datos aunque no se había exe este llemado... quizá es porque no se puede llamar más de una vez si estos llamdos están seguidos...
    }
        
    public void resetLog(boolean esJSON){
        JTable tabla = ((esJSON)?log_JSON:log_Reportes);
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();        
        
        try {            
            while(tabla.getRowCount()>0){
                modelo.removeRow(0);                
            }
            
            tabla.updateUI();
        } catch (Exception e) {
            System.out.println("Error al limpiar el log" + e.getMessage());
        }        
    }      
    
    //ahora que lo pienso creo que para los reportes no hace falta tener un método a menos que te DIERA TIEMPO y decidieras hacer que se remarcaran los lugares con errores    
    
    public File openSaveChooser(){
        File carpeta;
        boolean continuar = true;
        
        while(continuar){
            JFileChooser saveChooser= new JFileChooser();
            saveChooser.setDialogTitle("Guardar Proyecto");             
            saveChooser.setDialogType(JFileChooser.SAVE_DIALOG);
            /*saveChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);*/ 
        
            if(saveChooser.showSaveDialog(saveChooser)==JFileChooser.APPROVE_OPTION){
                carpeta = saveChooser.getSelectedFile();
                
                if(!carpeta.isDirectory()){
                    return carpeta;
                    
                }else{
                   JOptionPane.showMessageDialog(null, "Debes especificar un nombre", "Error al guardar", JOptionPane.WARNING_MESSAGE);
                }
            }else{
                continuar = false;
            }    
        }
        
        return null;
    }    

    
    public File openReadChooser(String extension){
       JFileChooser fileChooser = new JFileChooser();        
        
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //se va a poner una carpeta por defecto
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Solo archivos ."+extension, new String[]{extension});//esto para evitar que pueda seleccionar cualquier otra extesión que no sea la requerida
        fileChooser.setFileFilter(filtro);        
        
        if(fileChooser.showOpenDialog(fileChooser)==(JFileChooser.APPROVE_OPTION)){
            return fileChooser.getSelectedFile();
        }        
        return null;
      }
    
    
}

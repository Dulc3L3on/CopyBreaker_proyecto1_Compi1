/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import java.util.ArrayList;
import javax.swing.JTable;
import Backend.Objetos.Error;
import javax.swing.JComboBox;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phily
 */
public class ManejadorInterfaz {
    private JTable log = null;
    private JComboBox<String> comboBox = null;
    
    public ManejadorInterfaz(){
        this.cambiarApariencia();
    }
    
    private void cambiarApariencia(){
        try{ 
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName()); //aunque si jalaras el GTK+ también estaría genial y no cambiaría con SO xD
        }catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ){ 
           System.err.println("Imposible cargar interfaz, se usará la interfaz por defecto de Java.\nError: "+e);
        }         
    }

    public void setComponentes(JTable log, JComboBox<String> comboBox){
        this.log = log;
        this.comboBox = comboBox;
    }
    
    public void setErrores(ArrayList<Error> errores){
        DefaultTableModel modelo=(DefaultTableModel) log.getModel();        
        
        for (int elementoActual = 0; elementoActual < errores.size(); elementoActual++) {
             modelo.addRow(new Object[]{elementoActual+1, 
                errores.get(elementoActual).getClase(),
                String.valueOf(errores.get(elementoActual).getLinea()),
                String.valueOf(errores.get(elementoActual).getColumna()),
                errores.get(elementoActual).getErrorType(),
                errores.get(elementoActual).getLexema(),
                errores.get(elementoActual).getDescripcion()});
        }
        
        //log.updateUI();//ya no resulta necesario, luego de haver hecho la actualización con la limpieza, es decir los datos se muestran correctamente... si en dado caso no se llegaran a mostrar los rep, entonces envuelve esto en un try-cathc por el null, que no entiendo la razón por la que surge, pero no creo que vaya a haber problema si al debuggear, te diste cta que la tabla ya mostraba los datos aunque no se había exe este llemado... quizá es porque no se puede llamar más de una vez si estos llamdos están seguidos...
    }
    
    public void resetLog(){
        this.eliminarTodosLosRegistros(((DefaultTableModel)log.getModel()), log);
        this.comboBox.setSelectedIndex(0);//puesto que el proyecto 1 es siempre el valor por defecto...
    }
    
    private void eliminarTodosLosRegistros(DefaultTableModel modelo, JTable tabla){//dejo así los parámetros para ser más general
        try {            
            while(tabla.getRowCount()>0){
                modelo.removeRow(0);                
            }
            
            tabla.updateUI();
        } catch (Exception e) {
            System.out.println("Error al limpiar la tabla" + e.getMessage());
        }        
    }      
    
    public void habilitarComboBox(){
        if(!this.comboBox.isEnabled()){
            this.comboBox.setEnabled(true);
        }
    }
    
    public JTable getTable(){//solo para responder mi duda xD xD
        return this.log;
    }
    
}

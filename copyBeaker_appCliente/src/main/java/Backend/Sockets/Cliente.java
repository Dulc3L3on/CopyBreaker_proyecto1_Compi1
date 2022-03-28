/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Sockets;

import Backend.Objetos.RESULT;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class Cliente {
    private final String HOST = "127.0.0.1";
    private final int PUERTO = 77777;
    private Socket cliente;
    private DataOutputStream dataOutputStream;
    private ObjectOutputStream objectOutputStream;    
    private ObjectInputStream objectInputStream;
    private RESULT result;
    
    public Cliente(){
        try {
            this.cliente = new Socket(HOST, PUERTO);//especifica el camino por el que debe enviar la info            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar iniciar el cliente",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void sendData(String mensaje){
        try {
            dataOutputStream = new DataOutputStream(cliente.getOutputStream());//obtiene el camino            
            dataOutputStream.writeUTF(mensaje);
         
            this.dataOutputStream.close();        
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Imposible enviar los datos\nal flujo de salida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void sendDataObject(ArrayList<ArrayList<File>> archivos){
        try {        
            objectOutputStream = new ObjectOutputStream(cliente.getOutputStream());//obtiene el camino
            objectOutputStream.writeObject(archivos);
            
            this.objectOutputStream.close();        
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Imposible enviar los objetos\nal flujo de salida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }        
    
    public RESULT getDataObject(){//puesto que además del texto, recibiría
        try{                        
            objectInputStream = new ObjectInputStream(cliente.getInputStream());//abre un flujo para que la entrada pueda transitar por ahí
            result = (RESULT) objectInputStream.readObject();//recibe el flujo de datos de entrada
            
            objectInputStream.close();            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("error del servidor: "+e.getMessage());
            JOptionPane.showMessageDialog(null,"Algo salió mal al recibir"
                    + "\nla información del cliente", 
                    "Error de comuniacion", JOptionPane.ERROR_MESSAGE);
        }
        
        System.out.println("result recibido?: "+(result!=null));
        return this.result;//nunca será null, a menos que haya un error en el envío, porque este método solo se exe cuando todo está bien...
    }

    /**
     * Se invocará cuando el programa se cierre...
     */
    public void finalizeClient(){
        try {
            this.cliente.close();
        } catch (IOException ex) {
            System.out.println("Error al intentar cerrar el cliente: "+ex.getMessage());
        }
    }
    
}

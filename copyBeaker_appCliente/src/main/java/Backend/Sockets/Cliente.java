/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class Cliente {
    private final String HOST = "127.0.0.1";
    private final int PUERTO = 7777;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private ObjectOutputStream objectOutputStream;        
    private DataInputStream dataInputStream;
    private String entrada;
    
    public Cliente(){}
    
    public void sendData(String mensaje){
        try {
            this.socket = new Socket(HOST, PUERTO);//especifica el camino por el que debe enviar la info            
            dataOutputStream = new DataOutputStream(socket.getOutputStream());//obtiene el camino            
            dataOutputStream.writeUTF(mensaje);
         
            //this.dataOutputStream.close();
        } catch (IOException ex) {
            System.out.println("Cliente: error al enviar plain text");
            JOptionPane.showMessageDialog(null, "Imposible enviar los datos\nal flujo de salida", "Error [client]", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void sendDataObject(ArrayList<ArrayList<File>> archivos){
        try {        
            this.socket = new Socket(HOST, PUERTO);//especifica el camino por el que debe enviar la info            
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());//obtiene el camino
            objectOutputStream.writeObject(archivos);
            
            //this.objectOutputStream.close();        
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Imposible enviar los objetos\nal flujo de salida", "Error [client]", JOptionPane.ERROR_MESSAGE);
            System.out.println("Cliente: error al enviar Objetos "+ex.getMessage());
        }
    }        
    
    
    //los getter por la naturaleza de los clientes, deberían ser exe justo después de los send, para no tener problemas con que el socket ya halla sido cerrado...
    public String getData(){
        entrada = null;
        
        try {            
            dataInputStream = new DataInputStream(socket.getInputStream());//abre un flujo para que la entrada pueda transitar por ahí
            entrada = dataInputStream.readUTF();//recibe el flujo de datos de entrada            
            
            //dataInputStream.close();            
        } catch (IOException ex) {
            System.out.println("Cliente: error al obtener los datos: "+ex.getMessage());
            JOptionPane.showMessageDialog(null,"Algo salió mal al intentar la"
                    + "\ncomunicación por sockets", 
                    "Error de comuniacion [client]", JOptionPane.ERROR_MESSAGE);
        }        
        
        System.out.println("Texto recibido: "+entrada);
        return entrada;
    }
    
    private void closeStreams(){        
        try {
           if(this.objectOutputStream != null){
               this.objectOutputStream.close();
                
           }
           if(this.dataInputStream != null){
               this.dataInputStream.close();
            }
        } catch (IOException ex) {
            System.out.println("[Cliente] Error al cerrar los Streams");
        }
    }
    
    
    /**
     * Se invocará cuando el programa se cierre...
     */
    public void finalizeClient(){
        try {
            closeStreams();
             if(this.socket != null){
                if(!this.socket.isClosed()){//Por si acaso, con tal de no provocar errores xD xD
                    this.socket.close();
                }
             }    
        } catch (IOException ex) {
            System.out.println("Error al intentar cerrar el cliente: "+ex.getMessage());
        }
    }
    //practicamente debo copiar lo del servidor aquí para hacer lo de la recepción, tener dos clientes, puesto que uno va a recibir y otro enviar
    //y creo que no cierra la aplicación porque usas un timer y no un Thread o el que se implementa... aunque debería funcionar, puesto que funcionó en lo de EDD, más o menos lo hice así, pero eso sí, no tenía el while...
}

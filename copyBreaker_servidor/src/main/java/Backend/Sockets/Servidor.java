/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Sockets;

import Backend.Objetos.Resultado.RESULT;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class Servidor {
    private final int PUERTO = 77777;
    private ServerSocket servidor;
    private Socket cliente;    
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ObjectOutputStream objectOutputStream;
    private String entrada;
    private ObjectInputStream objectInputStream;
    private ArrayList<ArrayList<File>> archivosEntrada;
        
    public Servidor(){
        try {
            System.out.println("Iniciando servidor");
            this.servidor = new ServerSocket(PUERTO);//1. se dispone a escuchar  
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar iniciar el servidor",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }    
    
    public String getData(){
        entrada = null;
        
        try {            
            cliente = servidor.accept();//2. Acepta todas las peticiones que le lleguen del exterior
            
            dataInputStream = new DataInputStream(cliente.getInputStream());//abre un flujo para que la entrada pueda transitar por ahí
            entrada = dataInputStream.readUTF();//recibe el flujo de datos de entrada
            
            dataInputStream.close();            
        } catch (IOException ex) {
            System.out.println("error del servidor: "+ex.getMessage());
            JOptionPane.showMessageDialog(null,"Algo salió mal al intentar la"
                    + "\ncomunicación por sockets", 
                    "Error de comuniacion", JOptionPane.ERROR_MESSAGE);
        }        
        
        System.out.println("Texto recibido: "+entrada);
        return entrada;
    }
    
    public ArrayList<ArrayList<File>> getDataObject(){
        this.archivosEntrada = new ArrayList<>();        
        
        try{            
            cliente = servidor.accept();//2. Acepta todas las peticiones que le lleguen del exterior
            objectInputStream = new ObjectInputStream(cliente.getInputStream());//abre un flujo para que la entrada pueda transitar por ahí
            archivosEntrada = (ArrayList<ArrayList<File>>) objectInputStream.readObject();//recibe el flujo de datos de entrada
            
            objectInputStream.close();            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("error del servidor: "+e.getMessage());
            JOptionPane.showMessageDialog(null,"Algo salió mal al recibir"
                    + "\nla información del cliente", 
                    "Error de comuniacion", JOptionPane.ERROR_MESSAGE);
        }
        
        System.out.println("Archivos recibidos?: "+(!archivosEntrada.isEmpty()));
        return this.archivosEntrada;
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
    
    public void sendDataObject(RESULT result){
        try {        
            objectOutputStream = new ObjectOutputStream(cliente.getOutputStream());//obtiene el camino
            objectOutputStream.writeObject(result);
            
            this.objectOutputStream.close();                 
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Imposible enviar los objetos\nal flujo de salida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void closeClient(){//se cerrará luego de enviar la respuesta al cliente, puesto que con eso se finaliza el ciclo...    
        try {        
            if(!this.cliente.isClosed()){//Por si acaso, con tal de no provocar errores xD xD
                this.cliente.close();
            }            
        } catch (IOException ex) {
            System.out.println("[Server] Error al cerrar el cliente");
        }
    }
    
    /**
     * Se invoacrá cuando el programa se cierre
     */
    public void finalizeServer(){
        try {
            this.servidor.close();            
        } catch (IOException ex) {
            System.out.println("[Server] Error al cerrar el servidor: "+ex.getMessage());
        }
    }
    
}

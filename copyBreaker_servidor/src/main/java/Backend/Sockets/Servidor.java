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
    private final int PUERTO = 7777;
    private ServerSocket servidor;
    private Socket socket;    
    private DataInputStream dataInputStream;
    private ObjectInputStream objectInputStream;
    private String entrada;
    private ArrayList<ArrayList<File>> archivosEntrada;
    private DataOutputStream dataOutputStream;
    private ObjectOutputStream objectOutputStream;    
        
    public Servidor(){
        try {
            System.out.println("Iniciando servidor");
            this.servidor = new ServerSocket(PUERTO);//1. se dispone a escuchar  
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar iniciar el servidor",
                    "Error [server]", JOptionPane.ERROR_MESSAGE);
        }
    }    
    
    public String getData(){
        entrada = null;
        
        try {            
            socket = servidor.accept();//2. Acepta todas las peticiones que le lleguen del exterior
            
            dataInputStream = new DataInputStream(socket.getInputStream());//abre un flujo para que la entrada pueda transitar por ahí
            entrada = dataInputStream.readUTF();//recibe el flujo de datos de entrada
            
            //dataInputStream.close();            
        } catch (IOException ex) {
            System.out.println("error del servidor: "+ex.getMessage());
            JOptionPane.showMessageDialog(null,"Algo salió mal al intentar la"
                    + "\ncomunicación por sockets", 
                    "Error de comuniacion [server]", JOptionPane.ERROR_MESSAGE);
        }        
        
        System.out.println("Texto recibido: "+entrada);
        return entrada;
    }
    
    public ArrayList<ArrayList<File>> getDataObject(){
        this.archivosEntrada = new ArrayList<>();        
        
        try{            
            socket = servidor.accept();//2. Acepta todas las peticiones que le lleguen del exterior
            objectInputStream = new ObjectInputStream(socket.getInputStream());//abre un flujo para que la entrada pueda transitar por ahí
            archivosEntrada = (ArrayList<ArrayList<File>>) objectInputStream.readObject();//recibe el flujo de datos de entrada
            
            //objectInputStream.close();            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("error del servidor: "+e.getMessage());
            JOptionPane.showMessageDialog(null,"Algo salió mal al recibir"
                    + "\nla información del cliente", 
                    "Error de comuniacion [server]", JOptionPane.ERROR_MESSAGE);
        }
        
        System.out.println("Archivos recibidos?: "+(!archivosEntrada.isEmpty()));
        return this.archivosEntrada;
    }
    
    //estos send, siempre deberían exe después de un get, puesto que si no se hace así, no es posible asegurar que el cliente haya sido aceptado...
     public void sendData(String mensaje){
        try {            
            dataOutputStream = new DataOutputStream(socket.getOutputStream());//obtiene el camino            
            dataOutputStream.writeUTF(mensaje);
         
            //this.dataOutputStream.close();            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Imposible enviar los datos\nal flujo de salida", "Error [server]", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void sendDataObject(RESULT result){
        try {        
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());//obtiene el camino
            objectOutputStream.writeObject(result);
            
            this.objectOutputStream.close();                 
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Imposible enviar los objetos\nal flujo de salida", "Error [server]", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void closeClient(){//se cerrará luego de enviar la respuesta al cliente, puesto que con eso se finaliza el ciclo...    
        try {
            closeStreams();//inovoco hasta esta parte al cierre de los streams, debido a que si los cerraba antes de terminar los procesos con el cliente, esto me dada un error de comunicación, quí en el servidor a pesar de hacer lo mismo no me daba eso, pero por si acaso xD
            if(this.socket != null){
                if(!this.socket.isClosed()){//Por si acaso, con tal de no provocar errores xD xD
                    this.socket.close();
                }            
            }         
        } catch (IOException ex) {
            System.out.println("[Server] Error al cerrar el cliente");
        }
    }
    
    private void closeStreams(){        
        try {
           if(this.objectInputStream != null){
               this.objectInputStream.close();
                
           }
           if(this.dataOutputStream != null){
               this.dataOutputStream.close();
            }
        } catch (IOException ex) {
            System.out.println("[Cliente] Error al cerrar los Streams");
        }
    }
    
    /**
     * Se invoacrá cuando el programa se cierre
     */
    public void finalizeServer(){
        try {
            this.servidor.close();     
            closeClient();
            
        } catch (IOException ex) {
            System.out.println("[Server] Error al cerrar el servidor: "+ex.getMessage());
        }
    }
    
}

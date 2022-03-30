/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Sockets;

import Backend.Manejadores.ManejadorAnalisis;
import Backend.Objetos.Resultado.RESULT;

/**
 *
 * @author phily
 */
public class ServerProcess extends Thread{
    private final Servidor servidor;
    private final ManejadorAnalisis manejadorAnalisis;    
    private boolean enEspera = true;
    
    public ServerProcess(){        
        this.servidor = new Servidor();
        this.manejadorAnalisis = new ManejadorAnalisis();//no habrán incongruencias por existencias de datos anteiores, puesto que el método de analizar proyectos, se encarga de limpiar todos los listados cada vez que inicia su proceso...        
    }
    
    @Override
    public void run(){
        this.startServer();
    }    
    
    private void startServer(){
        while(enEspera){            
            System.out.println("---------Servidor a la espera----------");        
        
            System.out.println("Analisis en espera");        
            this.manejadorAnalisis.analizarProyectos(this.servidor.getDataObject());
            System.out.println("Analisis finalizado");                
                                
            this.servidor.sendData(this.manejadorAnalisis.getJSON());
            System.out.println("sended RESPONSE: "+ this.manejadorAnalisis.getJSON());            
            
            this.servidor.closeClient();//para que no se repita la llamada xD
            System.out.println("---------Response completado-----------");
        }        
    }
    
    private void dejarDeEsperar(){
        this.enEspera = false;
    }
    
    public void stopServerProcess(){//Se invocará al cerra el programa       
        dejarDeEsperar();
        this.servidor.finalizeServer();                
    }
    
    public ManejadorAnalisis getManejadorAnalisis(){
        return this.manejadorAnalisis;
    }
    
}

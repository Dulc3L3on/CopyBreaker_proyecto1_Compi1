/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Sockets;

import Backend.Manejadores.ManejadorAnalisis;
import Backend.Objetos.Resultado.RESULT;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author phily
 */
public class ServerProcess {
    private final Servidor servidor;
    private final ManejadorAnalisis manejadorAnalisis;
    private final Timer timer;
    
    public ServerProcess(){        
        this.servidor = new Servidor();
        this.manejadorAnalisis = new ManejadorAnalisis();//no habrán incongruencias por existencias de datos anteiores, puesto que el método de analizar proyectos, se encarga de limpiar todos los listados cada vez que inicia su proceso...
        
        timer = new Timer(0, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();                
            }
        });
        
        timer.start();
    }
    
    private void startServer(){
        while(this.timer.isRunning()){
            System.out.println("Servidor a la espera");        
        
            System.out.println("Analisis en espera");        
            this.manejadorAnalisis.analizarProyectos(this.servidor.getDataObject());
            System.out.println("Analisis finalizado");                
        
            RESULT result =  this.manejadorAnalisis.getRESULT();
            if(result != null){
                this.servidor.sendDataObject(result);
                System.out.println("Sin erores -> respuesta enviada");
            }
        
            this.servidor.closeClient();//para que no se repita la llamada xD
            System.out.println("Servidor a completado solicitud");
        }        
    }
    
    public void stopServerProcess(){//Se invocará al cerra el programa
        this.servidor.finalizeServer();
        this.timer.stop();
    }
    
    public ManejadorAnalisis getManejadorAnalisis(){
        return this.manejadorAnalisis;
    }
    
}

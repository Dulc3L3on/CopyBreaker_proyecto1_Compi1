/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Sockets;

import Backend.Manejadores.ManejadorInterfaz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author phily
 */
public class ClientProcess {
    private final Cliente cliente;
    private final ManejadorInterfaz manejadorInterfaz;
    private final Timer timer;
    
    public ClientProcess(ManejadorInterfaz manejadorInterfaz){        
        this.cliente = new Cliente();
        this.manejadorInterfaz = manejadorInterfaz;
        timer = new Timer(0, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                startClient();                
            }
        });
        
        timer.start();
    }
    
    private void startClient(){
        while(this.timer.isRunning()){
            System.out.println("Cliente a la espera");        
        
            System.out.println("Posteo resultados a la espera");        
            this.manejadorInterfaz.addResultados_JSON(this.cliente.getDataObject().getJSON());
            System.out.println("Posteo finalizado");                        
            
            System.out.println("Servidor a completado solicitud");
        }        
    }
    
    public void stopServerProcess(){//Se invocará al cerra el programa
        this.cliente.finalizeClient();
        this.timer.stop();
    }
    
    public ManejadorInterfaz getManejadorInterfaz(){
        return this.manejadorInterfaz;
    }
    
    public Cliente getCliente(){
        return this.cliente;
    }
}

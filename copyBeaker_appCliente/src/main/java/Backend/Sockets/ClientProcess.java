/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Sockets;

import Backend.Manejadores.ManejadorInterfaz;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author phily
 */
public class ClientProcess {
    private Cliente cliente;
    private final ManejadorInterfaz manejadorInterfaz;    
    
    public ClientProcess(ManejadorInterfaz manejadorInterfaz){                
        this.manejadorInterfaz = manejadorInterfaz;        
    }
    
    public void request(ArrayList<ArrayList<File>> archivos){
        System.out.println("-------Request iniciado---------");        
        this.cliente = new Cliente();
        System.out.println("Request enviado");        
        this.cliente.sendDataObject(archivos);
        System.out.println("En espera de resultados");        
        //en realidad aquí debería invocarse al manejador de resultados, puesto que hay que hacer varias cosas, todo esto si lo recibido no es null...
        this.manejadorInterfaz.addResultados_JSON(this.cliente.getData());
        System.out.println("Resultados recibidos");                        
        
        this.cliente.finalizeClient();        
        System.out.println("-------Request finalizado-------");
    }    
    
    public ManejadorInterfaz getManejadorInterfaz(){
        return this.manejadorInterfaz;
    }    
}

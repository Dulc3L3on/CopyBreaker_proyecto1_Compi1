/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Sockets;

import Backend.Manejadores.ManejadorAnalisis;
import Backend.Manejadores.ManejadorInterfaz;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author phily
 */
public class ClientProcess {
    private Cliente cliente;
    private final ManejadorAnalisis manejadorAnalisis;
    private final ManejadorInterfaz manejadorInterfaz;    
    
    public ClientProcess(ManejadorInterfaz manejadorInterfaz, ManejadorAnalisis manejadorAnalisis){                
        this.manejadorInterfaz = manejadorInterfaz;   
        this.manejadorAnalisis = manejadorAnalisis;
    }
    
    public void request(ArrayList<ArrayList<File>> archivos){
        System.out.println("-------Request iniciado---------");        
        this.cliente = new Cliente();
        System.out.println("Request enviado");        
        this.cliente.sendDataObject(archivos);
        System.out.println("En espera de resultados");        
        //en realidad aquí debería invocarse al manejador de resultados, puesto que hay que hacer varias cosas, todo esto si lo recibido no es null...
        String JSON = this.cliente.getData();
        System.out.println("Resultados recibidos");                        
        this.manejadorAnalisis.procesarJSONRecibido(JSON);//puede que lo guarde o no, sin importar eso no hay problema con generar el JSON...                
        System.out.println("Resultados procesados y desplegados");        
        
        this.cliente.finalizeClient();        
        System.out.println("-------Request finalizado-------");
    }    
    
    public ManejadorInterfaz getManejadorInterfaz(){
        return this.manejadorInterfaz;
    }    
}

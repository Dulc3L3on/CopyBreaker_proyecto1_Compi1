/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Analizadores.JSON_Parser;
import Backend.Analizadores.Lexer_JSON;
import Backend.Objetos.Resultados.RESULT;
import java.io.StringReader;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class ManejadorAnalisis {
    private final ManejadorInterfaz manejadorInterfaz;
    private Lexer_JSON lexer_JSON;
    private JSON_Parser parser_JSON;
    private RESULT result;
    private ManejadorErrores manejadorErrores;
    private final ManejadorProyectos manejadorProyectos;
    
    public ManejadorAnalisis(ManejadorInterfaz manejadorInterfaz){
        this.manejadorInterfaz = manejadorInterfaz;
        this.manejadorProyectos = new ManejadorProyectos(manejadorInterfaz);
    }
    
    public void procesarJSONRecibido(String JSON){
        if(this.verificarEstructuraJSON(JSON)){
            this.manejadorProyectos.crearProyectoCopy(JSON);//si lo cierra, entonces no se crearán los archivos, simplemente se mostrará el JSON, con lo de los reportes no hay problema porque no hay algo que mostrar...            
        }else{
            System.out.println("JSON recibido con errores");
        }//se supone que no debería entrar aquí, es más ni siquiera debería tener el if...
    }
 
    public boolean verificarEstructuraJSON(String JSON){
        this.manejadorErrores = new ManejadorErrores();    
        
        this.result = this.analizarJSON(JSON);
        
        this.manejadorInterfaz.resetLog(true);
        if(this.manejadorErrores.getListaErrores().isEmpty()){
            JOptionPane.showMessageDialog(null, "La variable RESULT ha sido generada\n"
            + " con éxito :).\n\nYa puedes utilizarla para crear\n tus reportes", 
              "Analisis exitoso", JOptionPane.INFORMATION_MESSAGE);
            
            this.manejadorInterfaz.manipularSeccionReportes(true);            
            return true;
        }else{
            this.manejadorInterfaz.manipularSeccionReportes(false);
            this.manejadorInterfaz.setErrores(this.manejadorErrores.getListaErrores(), true);//se setean los errores en la consola
        }
        return false;
    }//mayormente utilizado para la edución...
    
    private RESULT analizarJSON(String JSON){
        try {
            lexer_JSON = new Lexer_JSON(new StringReader(JSON));
            lexer_JSON.setInfoNecesaria(manejadorErrores);
            
            parser_JSON = new JSON_Parser(lexer_JSON, manejadorErrores);
            parser_JSON.debug_parse();
        } catch (Exception ex) {
            System.out.println("error en el análisis del JSON: "+ ex.getMessage());
            ex.printStackTrace();
        }              
        
        return this.parser_JSON.getRESULT();
    }
    
    public ManejadorProyectos getManejadorProyectos(){
        return this.manejadorProyectos;
    }
    
    //será útil en el manejador que se encarga de poner en marcha el HTML    
    public RESULT getRESULT(){
        return this.result;
    }
    
    //para el HTML se tendrían los mismos métodos, solo que se add la var para instanciar
    //el analizador semántico y el objeto que se encarga de poner en marcha todo
    //lo del HTML, si es que no hubieron errores, y así mostrar en la pestaña o addla 
    //dinámicamente, o mejor habilitar un btn para mostrar un JFrame que solo contenga 
    //el reporte creado, será de las mismas dimensiones que el home, para que así se 
    //puedan visualizar mejor las cosas xD
    
    
}

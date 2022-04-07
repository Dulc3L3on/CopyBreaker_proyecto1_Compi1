/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Analizadores.DEF_Lexer;
import Backend.Analizadores.DEF_Parser;
import Backend.Analizadores.HTML_Lexer;
import Backend.Analizadores.HTML_Parser;
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
    private DEF_Lexer lexer_DEF;
    private DEF_Parser parser_DEF;
    private HTML_Lexer lexer_HTML;
    private HTML_Parser parser_HTML;
    private String HTML_normalizado;
    private ManejadorErrores manejadorErrores;
    private final ManejadorProyectos manejadorProyectos;
    
    public ManejadorAnalisis(ManejadorInterfaz manejadorInterfaz){
        this.manejadorInterfaz = manejadorInterfaz;
        this.manejadorProyectos = new ManejadorProyectos(manejadorInterfaz);
    }
    
    public void procesarJSONRecibido(String JSON, boolean esApertura){        
        if(this.verificarEstructuraJSON(JSON)){
            if(!esApertura){
                this.manejadorProyectos.crearProyectoCopy(JSON);//si lo cierra, entonces no se crearán los archivos, simplemente se mostrará el JSON, con lo de los reportes no hay problema porque no hay algo que mostrar...                            
            }            
        }else{
            System.out.println("JSON recibido con errores");
        }//se supone que no debería entrar aquí, es más ni siquiera debería tener el if...
    }
 
    /**
     * Este es utilizado para cuando hagan edición
     * @param JSON
     * @return
     */
    public boolean verificarEstructuraJSON(String JSON){
        this.manejadorErrores = new ManejadorErrores();    
        
        this.result = this.analizarJSON(JSON);
        
        this.manejadorInterfaz.resetLog(true);
        if(this.manejadorErrores.getListaErrores().isEmpty()){
            JOptionPane.showMessageDialog(null, "La variable RESULT ha sido generada\n"
            + " con éxito :).\n\nYa puedes utilizarla para crear\n tus reportes", 
              "Analisis exitoso", JOptionPane.INFORMATION_MESSAGE);
            
            this.manejadorInterfaz.addResultados(JSON, true);
            this.manejadorInterfaz.addResultados(this.manejadorProyectos.inicializarDEF(), false);
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
    
    public void procesarCOPYRecibido(String COPY){
        int inicioHTML = COPY.toLowerCase().indexOf("<html>");//solo que habría problemitas si esto estuviera contenido en un comentario...
                
        String parteDEF = ((inicioHTML != 0)?((inicioHTML != -1)?COPY.substring(0,inicioHTML):COPY):"");//no habrá problemas puesto que llegará hasta el valor de iniHTML -1, que pasa cuando el valor de inicio y fin son iguales? creo que daría error en caso que el final fuese 0...
        String parteHTML = ((inicioHTML < COPY.length())?((inicioHTML != -1)?COPY.substring(inicioHTML):""):"");
        
        
        if(this.verificarEstructuraCOPY(parteDEF, parteHTML)){
            this.manejadorProyectos.guardarCambios(COPY, false);//si el usuario quiere, puede cambiar los cambios, pero así por defecto no xD
        }else{
            System.out.println("COPY generado por usuario con errores");
        }//se supone que no debería entrar aquí, es más ni siquiera debería tener el if...
    }
 
    private boolean verificarEstructuraCOPY(String DEF, String HTML){
        this.manejadorErrores = new ManejadorErrores();      
        this.HTML_normalizado = null;
        
        this.analizarDEF(DEF);
        this.analizarHTML(HTML);
        
        this.manejadorInterfaz.resetLog(false);
        if(this.manejadorErrores.getListaErrores().isEmpty()){
            JOptionPane.showMessageDialog(null, "El reporte ha sido generado con éxito. :)\n"
            + "\n\nYa puedes acceder a él presionando el\n"
            + "botón <VER REPORTE>", 
              "Analisis exitoso", JOptionPane.INFORMATION_MESSAGE);            
        
            //this.HTML_normalizado = generadorHTML();//tendría que recibir el HTML, que se sustrajo y manipularlo por medio de la TS...
            return true;
        }else{            
            this.manejadorInterfaz.setErrores(this.manejadorErrores.getListaErrores(), false);//se setean los errores en la consola
        }
        return false;
    }
    
    private void analizarDEF(String DEF){        
        if(!DEF.isBlank()){
            System.out.println("\n-------Análsis DEF-------");
            System.out.println("\n"+DEF);
            
            try {            
                lexer_DEF = new DEF_Lexer(new StringReader(DEF));
                lexer_DEF.setInfoNecesaria(manejadorErrores);
            
                parser_DEF = new DEF_Parser(lexer_DEF, manejadorErrores);
                parser_DEF.debug_parse();
            } catch (Exception ex) {
                System.out.println("error en el análisis del JSON: "+ ex.getMessage());
                ex.printStackTrace();
            }             
        }        
    }
    
    private void analizarHTML(String HTML){//me imagino que irá a devolver el HTML normalizado xD
        if(!HTML.isBlank()){
            System.out.println("\n-------Análisis HTML-------");
            System.out.println("\n"+HTML);
            
            try {
                lexer_HTML = new HTML_Lexer(new StringReader(HTML));            
            
                parser_HTML = new HTML_Parser(lexer_HTML, manejadorErrores);
                parser_HTML.debug_parse();
            } catch (Exception ex) {
                System.out.println("error en el análisis del JSON: "+ ex.getMessage());
                ex.printStackTrace();
            }
        }        
    }
    
    public String getHTML(){
        return this.HTML_normalizado;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Analizadores.Lexer;
import Backend.Analizadores.Parser;
import Backend.Herramientas.Herramienta;
import Backend.Objetos.Resultado.Clase;
import Backend.Objetos.Resultado.RESULT;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 *
 * @author phily
 */
public class ManejadorAnalisis {//para mientras que estos métodos son del servidor...
    private Herramienta herramienta;
    private ComplexComparator complexComparator;
    private final int NUMERO_PROYECTOS_ANALIZAR = 2;
    
    public ManejadorAnalisis(){
        this.herramienta = new Herramienta();        
    }
 
    public void analizarProyectos(ArrayList<ArrayList<File>> archivosProyectos){       
        ManejadorErrores[] manejadoresError = new ManejadorErrores[]{new ManejadorErrores(), new ManejadorErrores()};
        ArrayList<ArrayList<RESULT>> listadoRESULTS = new ArrayList<>();
        
        for (int actual = 0; actual < NUMERO_PROYECTOS_ANALIZAR; actual++) {
            listadoRESULTS.add(analizarClases(archivosProyectos.get(actual), 
                this.herramienta.getNombreArchivos(archivosProyectos.get(actual)),
                manejadoresError[actual], (actual+1)));            
        }
        
        if(manejadoresError[0].getListaErrores().isEmpty() && manejadoresError[0].getListaErrores().isEmpty()){
            complexComparator = new ComplexComparator(listadoRESULTS.get(0), listadoRESULTS.get(1));
            
            //ya tendríamos que tener la lógica para escribir el JSON o para formarlo después de tener el RESULT final
           //mira que conviene ojo mira que conviene
        }else{
            for (int actual = 0; actual < NUMERO_PROYECTOS_ANALIZAR; actual++) {
                //se llama al manejador de la tabla/interfaz para que add los errores a la consola, pensaba usar una tabla para más orden, pero por el scroll, quizá me quede con el JTxtArea
            }
        }        
        
        /*
          this.analizarClase(clases.get(claseActual).getAbsoluteFile());
        }*/
        
        
    }//se invocará una vez por cada proyecto, para que así recorra la lista de files de cada carpeta y cree una lista por separado
    
    public ArrayList<RESULT> analizarClases(ArrayList<File> archivosProyecto, 
            String[] nombreClases, ManejadorErrores manejadorErrores, int numeroProyecto){
        
        ArrayList<RESULT> resultados = new ArrayList<>();
                
        for (int actual = 0; actual < archivosProyecto.size(); actual++) {
            resultados.add(this.analizarClase(archivosProyecto.get(actual),
                    nombreClases, nombreClases[actual], manejadorErrores, numeroProyecto));            
        }
        
        return resultados;//si la clase no tiene contenido, entonces solo ese listado tendrá un ele, los demás estarán vacíos...
    }
    
    //yo imagino que esto se envlverá en un for para crear la TS de cada clase y así addla a la lista de TS...    
    public RESULT analizarClase(File clase, String[] nombreClases, String nombreClase, 
            ManejadorErrores manejadorErrores, int numeroProyecto){
        
        RESULT result = new RESULT();
        result.addClase(new Clase(nombreClase));//puesto que se req esta info desde el ini, para los errores en el caso del lexer, y para el seteo de la info de las var además de los errores en el caso del parser
        
        try {            
            String string = Files.readString(clase.toPath());
            System.out.println(string);            
            
            Lexer lexer = new Lexer(new StringReader(string));
            lexer.setInfoNecesaria(result, "Proyecto "+numeroProyecto, 
               nombreClases, manejadorErrores);
            
            Parser parser = new Parser(lexer,result, manejadorErrores);        
            parser.debug_parse();                        
        } catch (Exception ex) {
            System.out.println("excepción [ex]: "+ ex.getMessage());
            ex.printStackTrace();            
        }
        
        return result;//puesto que al ser por referencia, las modificaciones hechas por el lexer y/o el parser se encuentran aquí...
    }
    
}




//lo primero que hice para leer el archivo fue usar un new FileReader(clase)
//luego envolver eso en un BufferedReader... new BufferefReader(new FileReader(clase))
//ahora voy a probar crear un path y de el con Files obtener el string y volver eso a un StringReader
    //u obtener el path del archivo de forma directa...
    //String string = Files.readString(clase.toPath()), en Lexer -> new StringReader(string)
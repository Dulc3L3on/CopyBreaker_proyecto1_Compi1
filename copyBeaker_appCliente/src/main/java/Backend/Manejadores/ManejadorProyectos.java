/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.ProyectoCopy.Archivo;
import Backend.Objetos.ProyectoCopy.ProyectoCopy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class ManejadorProyectos {
    private String pathProyecto = null;//si es null quiere decir que no hay archivo guradado o abierto!
    private String nombreProyecto = null;
    private final ManejadorInterfaz manejadorInterfaz;
    private ProyectoCopy proyectoCopy = null;
    
    public ManejadorProyectos(ManejadorInterfaz manejadorInterfaz){
        this.manejadorInterfaz = manejadorInterfaz;
    }
    
    public void crearProyectoCopy(String contenidoJSON){
        this.guardarComo(contenidoJSON, this.inicializarDEF());
    }
    
    public void guardarComo(String contenidoJSON, String contenidoDEF){
        File directorio = this.manejadorInterfaz.openSaveChooser();        
        
        if(directorio != null){            
            this.pathProyecto = directorio.getAbsolutePath();//establezco hasta aquí el path puesto que si fuera null y la razón por la que se seleccionó guardar como fuera para guardar de nuevo un mismo proyecto con el que se estaba trabajando y ya se tenía guardado en otra parte, puesto que no se va a guardar por haber cancelado el proceso, entonces no tendría porque cambiarse el path...
            this.pathProyecto+= (!this.pathProyecto.endsWith(".copy"))?".copy":"";
            
            this.nombreProyecto = directorio.getName();
            
            this.proyectoCopy = new ProyectoCopy(pathProyecto, 
                new Archivo(pathProyecto+nombreProyecto+"JSON"+ ".json", 
                        nombreProyecto+"JSON", contenidoJSON),
                new Archivo(pathProyecto+nombreProyecto+"DEF"+ ".def",
                        nombreProyecto+"DEF", contenidoDEF));
            
            this.guardar();                        
        }
    }
    
    public String inicializarDEF(){
        return "</Seccion de definición de variables/>\n\n\n</Sección del HTML/>\n"
                + "<html>\n\t</aquí puedes darle forma a tu reporte :3 />\n</html>";
    }
    
    /**
     * Este es el método que deberás utilizar para
     * actualizar el objeto del proyectoCOpy actual
     * ya se encarga de hacerlo siempre y cuando no
     * sea null
     * @param cambios
     * @param enJSON
     */
    public void guardarCambios(String cambios, boolean enJSON){//en este método se actualiza el contenido del objeto        
        if(proyectoCopy != null){            
            this.proyectoCopy.resetContent(cambios, enJSON);
            this.guardar();
        }
    }
    
    private void guardar(){//solo guarda, no toma en cuenta si ya se actualizó el obj Proyecto o no...
        if(pathProyecto != null){//debe revisar este path, porque se supone que ya se h            
           try(ObjectOutputStream objectOutpusStream = new ObjectOutputStream(new FileOutputStream(pathProyecto))){
                objectOutpusStream.writeObject(this.proyectoCopy);                
            }catch(IOException error){
                JOptionPane.showMessageDialog(null, 
                    "Surgió algo que impidió guardar el archivo\ninténtalo de nuevo",
                    "Error de guardado",
                    JOptionPane.ERROR_MESSAGE);
                error.printStackTrace();                
            }            
        }
    }
    
    public void openProyect(){
        File archivo = this.manejadorInterfaz.openReadChooser(".copy");//se llama al menajdor interfaz para msotrar el jfilechooser de apertura
        
        if(archivo != null){//se lee el proyecto si el path != null/true            
            pathProyecto = archivo.getAbsolutePath();//para así actualizarlo...
            this.nombreProyecto = archivo.getName();
            
            try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(pathProyecto))) {
                this.proyectoCopy = (ProyectoCopy) objectInputStream.readObject();
                
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Surgió un error al intentar abrir\nel proyecto, intenta de nuevo ;)", "Error de apertura", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error al abrir el proyecto");
            }
        }        
    }///también se debe llamar al analizador, para que pueda procese los dos archivos, y así pueda genera los resultados dep del contenido guardado
    //y con ello almacenar en su variable de HTML_normalizado y de RESULT, lo corresp.. esto con tal de no tener que estar guardando más cosas xD
    //Así solo llamo los métodos juntos en el JFrame y ya xD
    
    public ProyectoCopy getProyectoAbierto(){
        return this.proyectoCopy;
    }
    
    public boolean actualProyectIsSaved(){
        return (this.proyectoCopy != null);
    }
    
    //el copy guardará los objetos del JSON y del .def por si acaso los eliminan entonces los volvería a crear
    //con ese método de file, que si no existe crea sino reemplaza, pero no me acuerdo cómo se llama :c :v
}

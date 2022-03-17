/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Analizadores.Lexer;
import Backend.Analizadores.Parser;
import Backend.Objetos.TablaSimbolos;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author phily
 */
public class ManejadorAnalisis {//para mientras que estos métodos son del servidor...
 
    public ArrayList<TablaSimbolos> analizarProyectos(ArrayList<File> clases){
        ArrayList<TablaSimbolos> listaTablaSimbolos = new ArrayList<>();
        
        for (int claseActual = 0; claseActual < clases.size(); claseActual++) {
                this.analizarClase(clases.get(claseActual).getAbsoluteFile());
        }
        
        return listaTablaSimbolos;
    }//se invocará una vez por cada proyecto, para que así recorra la lista de files de cada carpeta y cree una lista por separado
    
    //yo imagino que esto se envlverá en un for para crear la TS de cada clase y así addla a la lista de TS...    
    public void analizarClase(File clase){
        /*TablaSimbolos tablaSimbolos = new TablaSimbolos();*/
        try {            
             Lexer  lexer = new Lexer(new FileReader(clase));
             Parser parser = new Parser(lexer);
        
            parser.debug_parse();
        } catch (Exception ex) {
            System.out.println("excepción [ex]: "+ ex.getMessage());
            ex.printStackTrace();            
        }
        
        /*return tablaSimbolos;*/
    }
    
}

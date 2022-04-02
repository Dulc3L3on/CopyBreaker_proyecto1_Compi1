/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.Resultado.RESULT;

/**
 *
 * @author phily
 */
public class EscritorJSON {//Esta clase no será útil en el cliente, puesto que se va a escribir a partir de la gramática
    private String JSON = "";//Aquí la estructura que engloba todo [bueno al menos es inicio xD... así como hicsite en la práctica de EDD del año pasado...    
        
    public void escribirJSON(RESULT result){
        
        JSON = "{\n";
        
        this.addScore(result.getScore());
        this.addClases(result.getClassObjects());        
        this.addVariables(result.getVariablesObjects());
        this.addMetodos(result.getMethodObjects());
        this.addComentarios(result.getCommentsObjects());
        
        JSON += "}";
    }
    
    private void addScore(double score){
        JSON += "   Score: \""+ score + "\",\n";//no es neceario usar string.format para establecer el #decimales a mostrar puesto que el redondeo, se encargará de ello xD
    }
    
    private void addClases(String clases){
        JSON += "   Clases: [";                
        JSON += ((!clases.isBlank())?("\n"+clases+"\n     ]"):" ]");
        JSON += ",\n";
    }
    
    private void addVariables(String variables){
        JSON += "   Variables: [";        
        JSON += ((!variables.isBlank()?("\n"+variables+"\n      ]"):(" ]")));
        JSON += ",\n";
    }
    
    private void addMetodos(String metodos){
        JSON += "   Metodos: [";
        JSON += ((!metodos.isBlank())?("\n"+metodos+"\n      ]"):" ]");
        JSON += ",\n";
    }
    
    private void addComentarios(String comentarios){
        JSON += "   Comentarios: [";
        JSON += ((!comentarios.isBlank())?("\n"+comentarios+"\n      ]"):" ]");
        JSON += "\n";
    }
    
    public String getJSON(){
        return this.JSON;//puesto que no se puede enviar algo nulo por medio de los streams obtenidos en este caso por los sockets... entonces al condición es que se exe los proc del cliente si !JSON.isEmpty()
    }
    
}

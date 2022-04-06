/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.Resultados;

import java.io.Serializable;

/**
 *
 * @author phily
 */
public class Comentario implements Serializable{
    private String texto;
    
    public Comentario(String texto){
        this.texto = texto;
    }
    
    public String getTexto(){
        return this.texto;
    }
    
    
    //Estos métodos son para formar el reporte
    public String asString(){        
        //puesto que se add la concat de string al haber saltos de línea en el
        //contenido de los comentarios, entonces en el lexer del JSON se debe
        //hacer que solo concatene el contenido no a
        //esos-> \" {whiteSpace}* + {whiteSpace}* \" esto lo digo, porque de no 
        //hacerlo, en el reporte se mostrará esas concatenaciones...
        return "{ Texto: \""+ this.texto + "\" }";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.Auxiliares.Match;
import Backend.Objetos.Resultado.Comentario;
import Backend.Objetos.Resultado.Metodo;
import Backend.Objetos.Resultado.RESULT;
import Backend.Objetos.Resultado.Variable;
import java.util.ArrayList;

/**
 *
 * @author phily
 */
public class ComplexComparator {
    private final SingleComparator singleComparator;
    private RESULT result;
    
    public ComplexComparator(ArrayList<RESULT> resultadosProyecto1, ArrayList<RESULT> resultadosProyecto2){
        this.singleComparator = new SingleComparator();
        this.result = new RESULT();
        
        this.hallarRepitencias(resultadosProyecto1, resultadosProyecto2);
        System.out.println("Task of ComplexComaparator finalized");
    }
    
    private void hallarRepitencias(ArrayList<RESULT> resultadosProyecto1, ArrayList<RESULT> resultadosProyecto2){
        this.hallarClasesRepetidas(resultadosProyecto1, resultadosProyecto2);
        this.hallarComentariosRepetidos(resultadosProyecto1, resultadosProyecto2);
        this.hallarVariablesRepetidas(resultadosProyecto1, resultadosProyecto2);
        this.hallarMetodosRepetidos(resultadosProyecto1, resultadosProyecto2);
    }
        
    private void hallarClasesRepetidas(ArrayList<RESULT> resultados1, ArrayList<RESULT> resultados2){
        ArrayList<RESULT> auxiliarRESULT2 = (ArrayList<RESULT>) resultados2.clone();//para que así no se modifique el arr original
        
        for(RESULT result1 : resultados1) {                       
            RESULT result2 = this.singleComparator.fueCopiada(result1, auxiliarRESULT2);            
                
            if(result2 != null){
                this.result.addClase(result2.getClase(0));//bien hubiera podido crear una clase a partir de la info que brinda este objeto, pero ya que está formado... a menos que de problemas de ref, entonces si crearé otro obj...
                auxiliarRESULT2.remove(result2);//puesto que Java impide que los nombres de las clases de un mismo package se repitan, entonces este result ya no debe considerarse en otra comparación, pero si el proyecto es funcional, entonces no tendría por qué existir otra clase llamada igual... por lo tanto no tendría que removerse, aunque creo que lo haces para disminuir las revisiones xD                
            }            
        }    
        
        this.result.addSubScore((result.getClases().size()*2), (resultados1.size()+resultados2.size()));
    }//va a parar cuando encuentre la clase igual, se va a mantener fijo el RESULT de la clase del proy1, hasta que se acaben las del proy2, o se encuentre que es igual con otra
    
    private void hallarComentariosRepetidos(ArrayList<RESULT> resultados1, ArrayList<RESULT> resultados2){
        ArrayList<ArrayList<Match>> comentariosMatch = new ArrayList<>();
        int comentariosTotales = 0, aparicionesRepetidas = 0, aparicionesRepetidasParciales = 0;
        boolean seRepitio = false;
        
        for (RESULT result2 : resultados2) {
            comentariosTotales += result2.getComentarios().size();//pongo arriba este conteo, puesto que como se hace un remove en el singleComparator, y la lista que se envía no es un clone, ni se clona allá en el método, entonces esa eli, afecta a la lista que está aquí, por el hecho de ser un obj y de haber pasado el valor por ref, por lo cual o hacías o enviabas un clone para que el método del singleComparator empleara eso, o ponías antes el autoincre, puesto que esa lista, no te va a ser útil después, entonces se eligió el movimiento xD
            comentariosMatch.add(this.singleComparator.getCommentsMatch(result2.getComentarios()));             
        }//se recoge la lista que contiene los comentarios sin repeticiones y el #apariciones de cada comentario involucrado en un ele de la lista...
        
        for (RESULT result1 : resultados1) {
            ArrayList<Comentario> listaComentarios1 = result1.getComentarios();
            comentariosTotales += listaComentarios1.size();
            
            for (Comentario comentario1 : listaComentarios1) {
                for (ArrayList<Match> comentariosMatch2 : comentariosMatch) {//si no hay ningún comentario en el proy2, no habría problema
                    Comentario comentario = this.singleComparator.fueCopiado(comentario1, comentariosMatch2);
                    
                    if(comentario != null){
                        this.result.addComentario(comentario);   
                        aparicionesRepetidasParciales += this.singleComparator.getComentariosInvolucrados();
                        seRepitio = true;
                    }
                }
                aparicionesRepetidas += ((aparicionesRepetidasParciales == 0 && !seRepitio)?0:(aparicionesRepetidasParciales+1));//el +1 por el comentario previamente revisado de la clase1, debido al hecho que sus apariciones repetidas, solo deben contarse 1 vez...
                aparicionesRepetidasParciales = 0;//para limpiar el conteo para el otro comment de la lista de la clase del proy1...
                seRepitio = false;//para así limpiar todo para el siguiente comentario
            }
        }   
        
        this.result.addSubScore(aparicionesRepetidas, comentariosTotales);
    }//se va a manener fijo el comentario de la clase actual del proy1, hasta acabar los result de las clases del proy2
    
    private void hallarVariablesRepetidas(ArrayList<RESULT> resultados1, ArrayList<RESULT> resultados2){
        ArrayList<ArrayList<Match>> variablesMatch = new ArrayList<>();
        int variablesTotales = 0, aparicionesRepetidas = 0, aparicionesRepetidasParciales = 0;
        boolean seRepitio = false;
        
        for (RESULT result2 : resultados2) {
            variablesTotales += result2.getVariables().size();//pongo arriba este conteo, puesto que como se hace un remove en el singleComparator, y la lista que se envía no es un clone, ni se clona allá en el método, entonces esa eli, afecta a la lista que está aquí, por el hecho de ser un obj y de haber pasado el valor por ref, por lo cual o hacías o enviabas un clone para que el método del singleComparator empleara eso, o ponías antes el autoincre, puesto que esa lista, no te va a ser útil después, entonces se eligió el movimiento xD
            variablesMatch.add(this.singleComparator.getVariablesMatch(result2.getVariables()));
             
        }
        
        for (RESULT result1 : resultados1) {
            ArrayList<Variable> listaVariables1 = result1.getVariables();
            variablesTotales += listaVariables1.size();
            
            for (Variable variable1 : listaVariables1) {
                for (ArrayList<Match> variablesMatch2 : variablesMatch) {//si no hay ningún comentario en el proy2, no habría problema
                    Variable variable = this.singleComparator.fueCopiada(variable1, variablesMatch2);
                    
                    if(variable != null){
                        this.result.addVariable(variable);
                        aparicionesRepetidasParciales += this.singleComparator.getVariablesInvolucradas();                       
                        seRepitio = true;
                    }
                }
                aparicionesRepetidas += ((aparicionesRepetidasParciales == 0 && !seRepitio)?0:(aparicionesRepetidasParciales+1));//el +1 por la variable previamente revisada de la clase1
                aparicionesRepetidasParciales = 0;//para limpiar el conteo para la otra var de la clase del proy 1...
                seRepitio = false;
            }
        }    
        
        this.result.addSubScore(aparicionesRepetidas, variablesTotales);
    }//se va a mantener fijo el RESULT de la clase del proy1, hasta que las clases del proy2 se acaben de revisar
    
    private void hallarMetodosRepetidos(ArrayList<RESULT> resultados1, ArrayList<RESULT> resultados2){   
        ArrayList<ArrayList<Match>> metodosMatch = new ArrayList<>();
        int metodosTotales = 0, aparicionesRepetidasParciales = 0, aparicionesRepetidas = 0;
        boolean seRepitio = false;
        
        for (RESULT result2 : resultados2) {
            metodosTotales += result2.getMetodos().size();//pongo arriba este conteo, puesto que como se hace un remove en el singleComparator, y la lista que se envía no es un clone, ni se clona allá en el método, entonces esa eli, afecta a la lista que está aquí, por el hecho de ser un obj y de haber pasado el valor por ref, por lo cual o hacías o enviabas un clone para que el método del singleComparator empleara eso, o ponías antes el autoincre, puesto que esa lista, no te va a ser útil después, entonces se eligió el movimiento xD
            metodosMatch.add(this.singleComparator.getMetodossMatch(result2.getMetodos()));             
        }
        
        for (RESULT result1 : resultados1) {
            ArrayList<Metodo> listaMetodos1 = result1.getMetodos();
            metodosTotales += listaMetodos1.size();
            
            for (Metodo metodo1 : listaMetodos1) {
                for (ArrayList<Match> metodosMatch2 : metodosMatch) {//si no hay ningún comentario en el proy2, no habría problema
                    Metodo metodo = this.singleComparator.fueCopiado(metodo1, metodosMatch2);                    
                    
                    if(metodo != null){
                        this.result.addMetodo(metodo);
                        aparicionesRepetidasParciales += this.singleComparator.getMetodosInvolucrados();
                        seRepitio = true;
                    }
                }               
                aparicionesRepetidas += ((aparicionesRepetidasParciales == 0 && !seRepitio)?0:(aparicionesRepetidasParciales+1));//el +1, por el método revisado previamente de la clase1
                aparicionesRepetidasParciales = 0;//para limpiar el conteo para el otro método de la clase, del proy1...
                seRepitio = false;
            }
        }    
        
        this.result.addSubScore(aparicionesRepetidas, metodosTotales);
    }//se va a mantener fijo el RESULT de la clase actual del proy1, hasta que se acaben las clases del proy2    
    
    public RESULT getRESULT(){//nunca devolverá null, puesto que esta clase solo será instanciada si no hubieron errores, lo que si puede llegar a pasar es que devuelva un result vacío
        return this.result;
    }    
}

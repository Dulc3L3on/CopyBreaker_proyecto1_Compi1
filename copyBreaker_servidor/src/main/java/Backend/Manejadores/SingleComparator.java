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
public class SingleComparator {
    private int comentariosInvolucrados;
    private int variablesInvoucradas;
    
    //si alguno de los criterios son TRUE, se devolverá un nuevo objeto Clase
    //con los datos corresp y las fusiones nec, para ser add al obj RESULT_Repintencia
    //Creado en el analizadorRepitencia [de forma eqq con las demás estructuras
    
    public RESULT fueCopiada(RESULT resultClase1, ArrayList<RESULT> resultadosClase2){
        for (RESULT resultClase2 : resultadosClase2) {
            if(resultClase1.getClase(0).getNombre().equals(resultClase2.getClase(0).getNombre())///pongo 0, porque como cada RESULT parcial se creará por clase, entonces no hay más de una en sus listados... cuando en el archivo no haya nada [ni siquiera el esqueleto de una clase, el parser informará de un error, porque al menos debería tener eso [yo pienso que está bien que lo reporte como tal] y por ese motivo no se exe esto y por ello NUNCA habrá problema de un NullPosnter con respecto a eso xD
                && compararMetodosClase(resultClase1.getMetodos(),resultClase2.getMetodos())){
                
                return resultClase2;//para que así se pueda hacer la eli deseada en el método que invoca a este, para reducir las búsquedas... xD
            }             
        }        
        
        return null;
    }//nice
    
    private boolean compararMetodosClase(ArrayList<Metodo> listaMetodos1, ArrayList<Metodo> listaMetodos2){//no importa cual se envíe a cual, puesto que no se sobreapasará de sus índices...              
        if(listaMetodos1.size() != listaMetodos1.size()){
            return false;
        }        
        
        for (int actualRevisada = 0; actualRevisada < listaMetodos1.size(); actualRevisada++) {
            if(!existeMetodoIgual(listaMetodos1.get(actualRevisada).getNombre(), listaMetodos2)){
                return false;
            }
        }       
        return true;//tb retornará true cuando no tengan métodos ambas, lo cual es correcto xD
    }
    
    private boolean existeMetodoIgual(String metodo1, ArrayList<Metodo> listaMetodos2){
        for (int actual = 0; actual < listaMetodos2.size(); actual++) {
            if(metodo1.equals(listaMetodos2.get(actual).getNombre())){        
                return true;
            }
        }
        return false;
    }
    
    public Comentario fueCopiado(Comentario comentario1, ArrayList<Match> agrupacionComentarios2){//este comentario será de la lista Match<Comentario>, también podría hacerse esto con el método de comp de vars, o hacer lo de vars  [enviar el obj de revisión actual y la lista de la clase del proyecto2 corresp] en los demás métodos...                        
        comentariosInvolucrados = 0;
        
        for (Match agrupacion : agrupacionComentarios2) {
            if(comentario1.getTexto().equals(agrupacion.getComentario().getTexto())){                   
                this.comentariosInvolucrados = ((agrupacion.fueUtilizado())?0:agrupacion.getInvolucrados());
                return new Comentario(comentario1.getTexto());
            }
       }
       return null;
    }//nice
    
     public ArrayList<Match> getCommentsMatch(ArrayList<Comentario> listaComentarios2){
        ArrayList<Comentario> listaAuxiliar = listaComentarios2;
        ArrayList<Match> agrupaciones = new ArrayList<>();
        
        for (int anterior = 0; anterior < listaAuxiliar.size(); anterior++) {//debe llegar hasta la última posición, puesto que el listado de agrupaciones tiene un nodo por cada var != a cualquiera de los nodos que ya se encuentren en la lista xD
            agrupaciones.add(new Match(new Comentario(listaAuxiliar.get(anterior).getTexto())));
            
            for (int actual = (anterior+1); actual < listaAuxiliar.size(); actual++) {
                if(listaAuxiliar.get(anterior).getTexto().equals(listaAuxiliar.get(actual).getTexto())){
                    
                    agrupaciones.get(agrupaciones.size()-1).addInvolucrado();//y así tener el #vars iguales[tipo y nombre] del proy2                    
                    listaAuxiliar.remove(actual);
                    actual--;//para que así pueda estudiar el que ahora ocupa el lugar
                }
            }
        }        
        return agrupaciones;//devolverá una lista vacía, si no hay una lista de comentarios en el result...
    }    
    
    public Variable fueCopiada(Variable variableClase1, ArrayList<Match> agrupacionVariables2){     
        this.variablesInvoucradas = 0;
        
        for (Match agrupacion : agrupacionVariables2) {
            if(variableClase1.getTipo().equals(agrupacion.getVariable().getTipo()) && 
                variableClase1.getNombre().equals(agrupacion.getVariable().getNombre())){
                
                this.variablesInvoucradas = ((agrupacion.fueUtilizado())?0:agrupacion.getInvolucrados());
                return new Variable(variableClase1.getTipo(), variableClase1.getNombre(), 
                        (variableClase1.getFuncion()+" "+agrupacion.getVariable().getFuncion()));
            }
       }
       return null;
    }//nice
    
    
    //creado para no tener que revisar cada variable debido a la nec de formar el 
    //string de "función"
    public ArrayList<Match> getVariablesMatch(ArrayList<Variable> listaVariables2){
        ArrayList<Variable> listaAuxiliar = listaVariables2;
        ArrayList<Match> agrupaciones = new ArrayList<>();
        
        for (int anterior = 0; anterior < listaAuxiliar.size(); anterior++) {//debe llegar hasta la última posición, puesto que el listado de agrupaciones tiene un nodo por cada var != a cualquiera de los nodos que ya se encuentren en la lista xD
            agrupaciones.add(new Match(new Variable(listaAuxiliar.get(anterior).getTipo(),
            listaAuxiliar.get(anterior).getNombre(), listaAuxiliar.get(anterior).getFuncion())));
            
            for (int actual = (anterior+1); actual < listaAuxiliar.size(); actual++) {
                if(listaAuxiliar.get(anterior).getTipo().equals(listaAuxiliar.get(actual).getTipo()) &&
                   listaAuxiliar.get(anterior).getNombre().equals(listaAuxiliar.get(actual).getNombre())){
                    
                    agrupaciones.get(agrupaciones.size()-1).addInvolucrado();//y así tener el #vars iguales[tipo y nombre] del proy2
                    agrupaciones.get(agrupaciones.size()-1).getVariable().setFuncion(listaAuxiliar.get(actual).getFuncion());
                    listaAuxiliar.remove(actual);
                    actual--;//para que así pueda estudiar el que ahora ocupa el lugar
                }
            }
        }        
        return agrupaciones;
    }
    
    public Metodo fueCopiado(Metodo metodo1, ArrayList<Metodo> metodosClase2){
        for (Metodo metodo2 : metodosClase2) {
            if(metodo1.getTipo().equals(metodo2.getTipo()) && metodo1.getNombre().equals(metodo2.getNombre())
               && this.compararParametros(metodo1.getParametros(), metodo2.getParametros())){
                
                return new Metodo(metodo1.getTipo(), metodo1.getNombre(), metodo1.getParametros().size());//puesto que por la lógica de java, cada método en cada clase es único
            }        
        }
        
        return null;
    }
    
    private boolean compararParametros(ArrayList<Variable> parametros1, ArrayList<Variable> parametros2){
        if(parametros1.size() != parametros2.size()){
            return false;
        }
        
        for (Variable parametro1 : parametros1) {
            if(!this.existeParametroIgual(parametro1, parametros2)){
                return false;
            }
        }
        
        return true;//igual que con la clase, dará true cuando no tengan paráms y eso debe ser así xD
    }//no dijo que debían estar en orden, solo que fueran los mismos...
    
    private boolean existeParametroIgual(Variable parametro1, ArrayList<Variable> parametros2){
        for (Variable parametro2 : parametros2) {
            if(parametro1.getTipo().equals(parametro2.getTipo()) &&
                parametro1.getNombre().equals(parametro2.getNombre())){
                 return true;//puesto que con 1 basta
            }
       }
       return false;
    }  
    
    /**
     * El dato de esta variable existe entre el espacio
     * después de haber empleado fueReptedio [comment]
     * y la inocación de este mismo método [si es que 
     * se realiza a la misma instancia]
     * @return
     */
    public int getComentariosInvolucrados(){
        return this.comentariosInvolucrados;
    }
    
    
    /**
     * El dato de esta variable existe entre el espacio
     * después de haber empleado fueReptedio [variable]
     * y la inocación de este mismo método [si es que 
     * se realiza a la misma instancia]
     * @return
     */
    public int getVariablesInvolucradas(){
        return this.variablesInvoucradas;
    }
}

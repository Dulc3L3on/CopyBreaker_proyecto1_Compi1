/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servidor_copybreaker;

import jdk.nashorn.api.tree.BreakTree;

/**
 *
 * @author phily
 */
public class PruebasSintaxis {
    int varGlobal =5;
    
    PruebasSintaxis (){
        
    }
    
    /*
    
    int int4;
    //int4 = 9;

    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        
        String $;
        
        int int1 =-1, int2 = -1, int3 = -1;
        
        System.out.println("int1" + int1 + "int2"+ int2+ "int3"+ int3);
    }
    
    public void otroMetodo(){//esto me hace dar cuenta que, int, double, float y char están en un mismo grupo, es decir en las producciones de un mismo NT...
        //imagino que es porque con cada uno de ellos se puede hacer operaciones aritméticas sin problema [en el char sucede eso, puesto que se puede trabajar con la numeración ASCII o el caracter en cuestión...
        int var1 = 0;
        Object objeto1;
        String string= "";
        
        String var2 = "" + false + true + 1 + 'c' + 1.1+55+15*10 +'c';
        var2 =  1+55+15*10 +"";
        var2 =  'a' + 1.1 +"";
        var2 =  2.2 + 1.1 +"";
        var2 =  'b' + 'a' +"";
        var2 =  2.2 + 1.1 +"";
        var2 +=  2.2 + 1.1 +"";
        //var2 -=  2.2 + 1.1 +"";//solo permite autoincremento con +
        var2 =  false + 'a' +"";
        var2 =  false + true +"";
        var2 =  false + true +string;
        var2 =  false + "" +"";
        var2 = (false + "")+ "";
        var2 = objeto1 + "";
        var2 =  false +string;
        
        var1 +=2;
        var2 += "";        
        var1 -= 3;
        //var2 -= "";
        var1 *= 3;
        //var2 *= "";
        var1 /= 3;
        //var2 /= "";
        var1 %= 3;
        
        var1 += var1;
        
        switch(var2){
            case 15+""+1:
                this.retorno(var1);
                for (int indice = 0; indice <10; /*(indice ++)*///indice++) {//tampoco permite encerrar el incremento aquí dentro del for...
  /*                  System.out.println("si se puede meter un for...");
                }
                int var3;
                var3 = 4;
                --var3;
                if(true){
                
                }
                //return "";//no permite hacer esto puesto que el método es de tipo void...
                //return;//si permite hacer esto obviamente, pero al igual que en el caso en el que a la par de él si hay algo, no debe haber nada más abajo...
            break;
        }
        
        for (int i = 0; (i<10 && i >5); i++) {
            
        }
        
        var1++;
        //(var1++;)//no lo permite
        //(var1++);//no lo permite
        (var1)++;
        
        
        var1+=1;
        //(var1+=1;)//no lo permite
        //(var1+=1);//no lo permite
        (var1)+=1;
        
        for (double i = 0; (i<10 && i >5); i++) {
            
        }
        
        for (char i = 0; (i<10 && i >5); i++) {
            
        }
        
        /*for (String i = 0; (i<10 && i >5); i++) {
            
        }*/
        
   /*     if(!'c' != !'d'){
        
        }
          if(!'c' != 'd'){
        
        }
         
        if(!false != !true){
            String string = ("")+("");
        }
        if(!false != true){
            String string = ("")+("");
        }
    }
    
    public int metodoSwitch_int(int var){
        switch(var){
            case 1:
                //return;//no lo permite puesto que el método tiene tipo != void...
                return 1;
        }
        
        //break;
        return 0;
    }
    
    public void metodoSwitch_void(int var){
        switch(var){
            case 1:
                return;
                
            case 2:
                break;
                //return 1;//no lo permite puesto que el método tiene tipo == void...
        }
        
        //break;
     
    }
        
    public void metodoSwitch_onlyDefault(int var){
        switch(var){
            default:
                System.out.println("");
        }
        
        if(""+2+"" == ""){
        
        }
        if(2+"" == ""){
        
        }
        
        if(""+2+false+"" == ""){
        
        }
        
        if(2+5+"" == false){
        
        }
        
        //break;
    }
    
    public void conRetornoASecas(){
        boolean boleana = (1==1);
        this.pruebaSwitch_Condiciones((1==1));//si permiten expresiones, puesto que esto devuelve el valor de la expresión evaluada...
        
        return;
    }
    
    public void conBreak(){
        break;
    }
    
    public void pruebaSwitch_Condiciones(boolean var){
        switch(var){
            case true:
                break;
                
        }
        int indice =0;
        
        for (indice; indice < 10; indice++) {
            return;
        }
        
        for (indice = 0; indice < 10; indice = indice -5) {
            
        }
        
        for (indice = 0; indice < 10; indice -5) {
            
        }
        
        for (indice = 0; indice < 10; indice =- 5) {//Esto lo toma como una asignación -5 o hace indice = indice - 5???
            
        }
        
        //yo diría que si el IDE No advierte de un error, entonces java no toma esto como error, o habrán pasado por alto esto en sus gramáticas??? xD
        for (; indice < 10; indice =- 5) {//permite no tener una asignacion
            
        }
        for (indice = 0;; indice =- 5) {//permite no tener una condicion
            
        }
        
      
        
        for (indice = 0; indice < 10;) {
            
        }
      
        for (;; indice =- 5) {//
            
        }
        for (; indice < 10; ) {
            
        }
        for (indice = 0; ; ) {//Esto lo toma como una asignación -5 o hace indice = indice - 5???
            
        }
        
        
        
        
    }
    
    public void soloParaElFor(){
      boolean booleano = false;
      
      int entero = [5];
      int String = [""];
      boolean bol = [true];
      char caracter = ['c'];
        for (booleano = true; booleano;) {
            
        }
        
        for (; booleano;) {
            
        }
        
        for (; booleano == true;) {
            
        }
        for (boolean bool = (booleano ==true); bool== true;) {
            
        }
    }
    
    / **/
    /* * /dfgdf
    / * * /
    sadas
    /* * / * ******* / */
    /****/
    /*/*/
    /*/**/
    /* /* */
    /* / * */
   /********///**/
    
  /* public int retorno(int etero){
        //int numero += 5;//no permite un autoincremento y mucho menos un incremento en la línea de creación, puesto que aún no ha sido inicializada...
        int numero;
        numero+=2;
   //     {return 3;
   //}
    if(etero == 3){
     etero = etero++;
        System.out.println(etero);
    }
    return 4;        
        
    }*/
    
    
    

public void pruebaSwitch(String string){
    String concat = "";
    concat = (1==3) +  "";
    concat = 1-15*12 + "" + "c"+ 'c';
    char caracter = 'c';
    //char caracter2 = "b";
    //char caracter3 = c;
    Object objeto = new PruebasSintaxis();
    //int numero = (5), (numero2= 3);//parentesis al contenido si lo permite, a toda la creación, no xD wuju xD
    
    switch(string){
        case "cassa":
            break;
    }
    
    switch(string){
        case "cassa":
     
    }
    
    switch(string){
     
    }
    
    
    switch(string){
        default: System.out.println("hola xD");
    }
    
    switch(string){
        default: 
    }
}//solo admite int, double, string y char

public int pruebaContenidoCiclos(){
    for (int i = 0; i < 10; i++) {
        return 1;
    }
    
    while(true){
        return 2;
    }
    
    
}

public int pruebaContenidoDoWhile(){
    do{
        return 3;
    }while(false);
    
    //return 0;//puesto que de alguna manera sabe que si no exe ninguna sola vez el ciclo, el return del propio método, no tiene sentido...
}
    
public void pruebaAsignacionFor(){
    int valor = 3;
    
    for (int indice = valor; indice < 10; indice++) {
         
    }
    
    for (int indice = 0; indice < 10; ) {
         
    }
    
    /*for (int indice = 0; ; ) {
         
    }*///si lo permite pero da error semántico para lo que esté abajo porque sabe que sería infi xD
    
    for (double indice = 0.5; indice < 15.3; indice+=0.5) {
         
    }
    
    for (double indice = 0.5; indice < 15.3; ) {
         
    }
    
    /*for (double indice = 0.5;; ) {
         
    }*///si lo permite pero da error semántico para lo que esté abajo porque sabe que sería infi xD
    
    for (char indice = 0; indice < 10; indice++) {
         
    }
    
    
    
    for (String indice = ""; valor < 10; ) {
        
    }
    
    /*for (String indice = ""; ; ) {
        
    }*///si lo permite pero da error semántico para lo que esté abajo porque sabe que sería infi xD
    for (;valor < 10; valor++) {
        
    }
    String string = "";
    for (;valor < 10; string+="") {
        
    }
    char caracter = 'v';//no permite que haya algo vacío entre '', lo bueno que eso lo impide el lexer xD
    for (;valor < 10; caracter+='a') {//no da error ese más igual, puesto que solo debería almacenar caracteres????
        
    }
    
    
    /*for (;; caracter+='a') {//no da error ese más igual, puesto que solo debería almacenar caracteres????
        
    }*///si lo permite pero sabe qu elo de abajo es inalcanzable [semánticamente xD]
    
    /*for (;; caracter+='a') {//no da error ese más igual, puesto que solo debería almacenar caracteres????
        
    }*/
    
    
    for (boolean indice =true; indice; valor++) {
         
    }
    for (boolean indice =true; indice; ) {
         
    }
    
    
    /*for (boolean indice =true;; ) {
         
    }*///si lo permite pero da error semántico para lo que esté abajo porque sabe que sería infi xD
    for (;; ) {
         
    }//si lo permite pero da error semántico para lo que esté abajo porque sabe que sería infi xD, esto es válido para todos, o sea obvi xD
    
}

public void masPruebasFor(int valor){
    for (Object objeto = new Object(); valor < 10; valor ++) {
        
    }
    
    for (PruebasSintaxis objeto = new PruebasSintaxis(); valor < 10; valor ++) {
        
    }
    
    if(!true == !true  && !false){
    
    }
    
    if(!true != !true  && !false){
    
    }//booleans solo admiten == y !=
    
    if(5 >= 3  && !false){
    
    }
    Object object1 = null;
    Object object2 = null;
    PruebasSintaxis objeto = null;
    boolean variable= true;
    //acaso =! es como un tipo de autoasignación para booleans??? pero eso solo cb su estado, sería como bool = !bool, pero eso no reduce nada... ese si lo voy a ignorar, porque no sé algo específico... :v
    
    if(variable =! variable){
    
    }
    
    if(objeto == object1  && !false){
    
    }//objects solo admiten == y !=, no solo pueden compararse con objcts sino tb con objetos, aunque creo que esa siempre daría false xD, lo mismo aplica para Objeto   
     
    if(object1 == ""  && !false){
    
    }//object puede compararse tb con strings... puesto que esos son una clase...
    
    if(true && !false){
    
    }
    
    if(5.5 > 'c'  && !false){
    
    }//los numeros[int y double] pueden compararse con char y vice, con ambos se puede usar todos los operadorsRelacionales
    //char y string no se pueden comparar
    
    if('c'<='b'){
    
    }
    
    if("" != ""  && !false){
    
    }//string solo admite == y !=
}//:v permite que se asigne todos los tipos [al menos de la práctica xD]

public Object pruebaRetornoPermititdo_Object(){
    PruebasSintaxis prueba = new PruebasSintaxis();
    String string = "";
    
    //return prueba;//claro que si funcioan
    return string;//por supuesto que tb xD
}

public int pruebaRetornoPermitido_int(){
    char carcater = 'c';
    double doble = 0.1;
    
    //return doble;//no lo permite sin casteo
    return 'c';//lo sabía xD
}//int, char

public double pruebaRetornoPermitido_double(){
    char caracter = 'c';
    int entero = 5;
    
    return caracter;//tb lo sabía xD
    //return entero;//claro que si xD
}//char, entero, double

public char pruebaRetornoPermitido_char(){
    int entero = 5;
    double doble = 5.5;
    
    
   return 5;//no permite int[si es una var] ni double [en valor y var], pero en las condiciones si permitie compararse entre ellos... :0
   // return 'd';
}

public void pruebaAsignacionesPermitidas(){
    int entero = 'c';
    //int entero2 = 5.5;
    
    double doble = 5;
    doble = 'd';
    
    char caracter = 5;
    //caracter = entero;
    //caracter = 73.5;
    
}//sé que permite otros,pero creo que ignoaré esto porque implica más trabajo y no creo que evalúen hasta ese punto 

public int getNumero(int numero){
    return numero;
}


public void pruebaOpcionesSwitch(){
    int entero = 5;
    double doble = 7.7;    
    String string = "";
    char caracter = 'c';
    boolean bool = entero==5+3;
    PruebasSintaxis pruebas = new PruebasSintaxis();
    Object objeto = new Object();
    
    this.varGlobal++;
    
    switch(this.getNumero(5)){
        //case 5+5*entero:
         case 5+5*3:
    }
    /*switch(doble){
    
    }*/
    
    switch('c'){
        case 'd':
        //case caracter:
    }
    
    switch("c"){
        case "asd"+3+true:
    }        
    //es un hecho, solo admite ints, char y string
    
    /*switch(pruebas){
    
    }*/
    
    switch("c"){
        default :
    }
}

public void prubeasConcat(){
     String concat = (3>2) +"";//solo permite condiciones [según el nombre que psusiste en tu gramática] si están encerradas enter ()
     
     char caracter = ' ';
}

public void pruebasIf(){
    int numero = 5;
    double doble = 3;
    
    if(!!!(5>4)){
    
    }    
    if(!(5<4)){
    
    }    
    if(!(5>=4)){
    
    }    
    if(!(5<=4)){
    
    }    
    if(!(5==4)){
    
    }    
    if(!(numero>=4)){
    
    }    
    if(!(5<=numero)){
    
    }    
    if(!(numero==4)){
    
    }    
    
    if(!(5<=numero)){
    
    }    
    if(!(numero==doble)){
    
    }//si deja comparar entre int y doubles    
    if(!(5<=numero) && !(numero==doble)){
    
    }    
    if(!(numero==4) || (numero==doble)){
    
    }    
    if(!!(!(numero==4) || (numero==doble))){
    
    }    
    
    if(!(""=="")){
    
    }
    if(!(""!="")){
    
    }
    
    if(!('c'=='d')){
    
    }
    if(!('c'!='d')){
    
    }
    if(!('c'>'d')){
    
    }
    //OK, si se puede negar una condición con OR :0 -_- JAJAJA, que bien xD    
}

public void probarConcatenacion(int numero){
    String concat = ((1>3))+""+2;
    concat = 2.2+3.3 +"";
    concat = 2+5+7+"";
    concat = 'c'+'f'+"";
    concat = 2 +'c'+5.3 + "";
    concat = ""+(1-5);
    concat = ""+(numero--)+(numero+=1);
    concat = true +"";
}//RESUMEN
//todas las operaciones: aritmeticas, comparacion, la incre, la autoincre
//doubles, int, char y strings [dobi xD], pueden aparecer +1 vez antes de un string
    //ya sea puros o una combinación de cualquiera de estos
//boolean puede aparecer 1 sola vez
    //y solo puede estar él antes del string, nada más, ni siquiera los que pueden
    //estar más de una vez...

public void pruebasAsignacion(){
   this.getPruebasSintaxis().varGlobal = 3;
   //this.getPruebasSintaxis()= new PruebasSintaxis();//yo me recuerdo que el inge Moi hizo algo así y le funcionaba...
}

public PruebasSintaxis getPruebasSintaxis(){
    return new PruebasSintaxis();
}

public int getVar(){
    return varGlobal;
}


}

import java.io.IOException;
import java.util.ArrayList;

public class Concatenador {
    charByChar cbc=new charByChar();
    char caracter;
    String[] palabras_validas=new String[]{"CON","VAR","PRO","ENT","PUN","STR",
    "CHR","RUN","RD","WR",">STR","END<","WHEN","THEN","WHL","RUN","=","=?","=/",
    "<<","=<",">>","=>","+","-","*","/",".",",",";","(",")","<NUM>","<IDENT>"};
    char[] caracteres;
    char aux;
    int contador=0;
    int contador_letras=0;
    int entro=0;
    ArrayList<String> palabras_totales = new ArrayList<>();
    StringBuilder[] tokens;
    public void previous_token(){
        
        this.contador_letras = palabras_totales.get(palabras_totales.size()-1).length();
        // /System.out.println("La palabra que se saco fue:"+palabras_totales.get(palabras_totales.size()-1)+"Y su tamano es:"+palabras_totales.get(palabras_totales.size()-1).length());
        this.contador = this.contador - (this.contador_letras);
        

    }
public String nextToken(){
    StringBuilder sb= new StringBuilder();

    try{
        //-------BLOQUE DE CODIGO PARA LAS CADENAS QUE SE SEPARAN POR ESPACIO Y NUMEROS----------//

        caracteres=charByChar.ReadFileToCharArray();
        
        if(entro>=1){
            if(caracteres[contador-1]==32){

            }else{
            contador++;//Contador para las multiples cadenas que se analizan
            }
        }
        else{
            
        }
        caracter=caracteres[contador];
    entro++;
    

    if((caracter>=65 && caracter<=90) || (caracter>=97 && caracter<=122)){
        sb.append(caracter);
        contador++;
        caracter=caracteres[contador];
    while(((caracteres[contador]>=65 && caracteres[contador]<=90) || (caracteres[contador]>=97 && caracteres[contador]<=122) || (caracteres[contador]>=49 && caracteres[contador]<=57)) && caracteres.length>contador && caracteres[contador]!=32){
        caracter=caracteres[contador];                  
        sb.append(caracter);            
        contador++;
    }
    palabras_totales.add(sb.toString());
    return sb.toString();
    }
    else if((caracter>=49 && caracter<=57) ){
        do{
            caracter=caracteres[contador];
            contador++;
            
            sb.append(caracter);
            
        }while(((caracter>=48 && caracter<=57)|| caracter=='.') && contador<caracteres.length);
        palabras_totales.add(sb.toString());
        return sb.toString();
    }
    //-----------------------------PARES DE CARACTERES A DEVOLVER---------------------------------//
    else if(caracter=='='){
        aux=caracter;
        contador++;
        caracter=caracteres[contador];
        sb.append(aux);
        if(caracter=='?'||caracter=='/'||caracter=='<'||caracter=='>'){
            
            sb.append(caracter);
            
        }
        palabras_totales.add(sb.toString());
        return sb.toString();
    }
    else if(caracter=='<'){
        aux=caracter;
        contador++;
        caracter=caracteres[contador];
        if(caracter=='<'){
            sb.append(aux);
            sb.append(caracter);
            palabras_totales.add(sb.toString());
            return sb.toString();
        }
        
    }
    else if(caracter=='>'){
        aux=caracter;
        contador++;
        caracter=caracteres[contador];
        if(caracter=='>'){
            sb.append(aux);
            sb.append(caracter);
            palabras_totales.add(sb.toString());
            return sb.toString();
        }
    }
    else if(caracter==';'){
        sb.append(caracter);
        contador++;
        palabras_totales.add(sb.toString());
        return sb.toString();
    }
    else if(caracter=='('){
        sb.append(caracter);
        contador++;
        palabras_totales.add(sb.toString());
        return sb.toString();
    }
    else if(caracter==')'){
        sb.append(caracter);
        contador++;
        palabras_totales.add(sb.toString());
        return sb.toString();
    }
    
    else if(caracter=='-'){
        sb.append(caracter);
        contador++;
        palabras_totales.add(sb.toString());
        return sb.toString();
    }
    
    else if(caracter=='+'){
        sb.append(caracter);
        contador++;
        palabras_totales.add(sb.toString());
        return sb.toString();
    }
    
    else if(caracter=='*'){
        sb.append(caracter);
        contador++;
        palabras_totales.add(sb.toString());
        return sb.toString();
    }
    
    else if(caracter=='/'){
        sb.append(caracter);
        contador++;
        palabras_totales.add(sb.toString());
        return sb.toString();
    }
    else if(caracter == '.'){
        
        sb.append(caracter);
        contador++;
        palabras_totales.add(sb.toString());
        return sb.toString();
    }
}

    catch(IOException e){
        e.printStackTrace();
    }
    return sb.toString();
}
public boolean validar(String palabra){
    boolean esvalida=false;
    for(int i=0;i<palabras_validas.length;i++){
    if(palabras_validas[i].equals(palabra)){
        esvalida=true;
    }
}
    return esvalida;
}

public void hacer_tokens(String cadena){
    int c=0;
    tokens = new StringBuilder[1000];
    for(int i = 0; i<cadena.length(); i++){
        if(cadena.charAt(i) != 32){
            tokens[c].append(cadena.charAt(i));
        }
    }
}
public void siguiente_token(){
    contador++;
}
public static void main(String []as){
    Concatenador c = new Concatenador();
    int contador = 0;
    while(contador<16){
    String prueba = c.nextToken();
    System.out.println(prueba);
    contador++;
    }

    c.previous_token();
    String prueba = c.nextToken();
    System.out.println(prueba);
    
}
}

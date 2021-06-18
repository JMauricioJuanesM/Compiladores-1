import java.util.ArrayList;

public class Compilador {
    Concatenador c = new Concatenador();
    static ArrayList<String> identificadores = new ArrayList<String>();
    static ArrayList<String> tipos = new ArrayList<String>();
    private Boolean compilo = true;
    
    //<PROGRAMA>::=BLOQUE
    public void Programa(){
        String X;
        Bloque();
        X = c.palabras_totales.get(c.palabras_totales.size()-1);
        if(X.equals(".") ){
            if(compilo){
                System.out.println("El programa compilo exitosamente.");
            }else{
                System.out.println("Fallo en la compilación.");
            }
            
        }
        else{
            Error(1);
        }
    }
    
    //<BLOQUE>::=<AUXCON<AUXVAR><AUXPRO><PROPOSICION>
    public void Bloque(){
        Auxcon();//Si paso
        Auxvar();//Si paso
        Auxpro();//Si paso
        Proposicion();//Si lo paso
    }
    //<PROPOSICION>:=<IDENT> = <EXPRE>
    public void Proposicion(){
       String X = "";
        if(Ident(X)){
            String last_word = c.palabras_totales.get(c.palabras_totales.size()-1);
            int index = returnIndexOf(identificadores, last_word);
            if(index!=-1){
                if(!tipos.get(index).equals("ENT")){
                    Error(16);
                }
            }
            X = c.nextToken();
            if(X.equals("=")){
                Expre();
            }
            else{
                Error(9);
            }
        }
        //<PROPOSICION>:= RUN <IDENT>
      

        else if(X == "RUN"){
            if(Ident(X)){
               
            }
            else{

            }
            }
        //<PROPOSICION>:= RD <IDENT>
        else if(X == "RD"){
            if(Ident(X)){

            }
            }
        //<PROPOSICION>::= WR<AUX3>
        else if(X == "WR"){
            Aux3();
            }
        
        else if(X == "STR"){
            Proposicion();
            Aux4();
            X = c.nextToken();
            if(X == "END"){

            }
            else{

            }
        }

        // <PROPOSICION>: WHEN<CONDICION> THEN <PROPOSICION><AUX5>
        else if(X == "WHEN"){
            Condicion();
            X = c.nextToken();
            if(X == "THEN"){
                Proposicion();
                Aux5();
            }
        }
        // <PROPOSICION>::=WHL <CONDICION> RUN <PROPOSICION>
        else if(X == "WHL"){
            Condicion();
            X = c.nextToken();
            if(X == "RUN"){
                Proposicion();
            }
            else{

            }
        }
        }
//<CONDICION>::=<EXPRE><SIMREL><EXPRE>
    public void Condicion(){
        Expre();
        Simrel();
        Expre();
    }
    public boolean Simrel(){
        String X = c.nextToken();
        boolean resultado = false;
        switch(X){
            case "=?":
                resultado = true;
            break;
            case "=/":
            resultado = true;
            break;
            case ">>":
            resultado = true;
            break;
            case "=>":
            resultado = true;
            break;
            case "<<":
            resultado = true;
            break;
            case "=<":
            resultado = true;
            break;
            default:
            Error(10);
        }
            return resultado;
        }
    /*
    <AUX5>::= ELSE <PROPOSICION> <AUX5>
    <AUX5>::=  ε
    */
    public void Aux5(){
        String X = c.nextToken();
        if(!X.equals(";") && !X.equals(".") && !X.equals("END")){
        
        if(X == "ELSE"){
            Proposicion();
            
        }
    }
        else{
            c.previous_token();
        }
    }


   /*
   <AUX4>::= ; <PROPOSICION> <AUX4>
    <AUX4>::= ε
   */
    public void Aux4(){
        String X =c.nextToken();
        if(X == ";"){
            Proposicion();
            Aux4();
        }
        else{
            
        }
    }
    /*
    <AUX3>::=<IDENT>
    <AUX3>::=<NUM>

    */
    public void Aux3(){
       String X = c.nextToken();

       if (Ident(X)){

       }    
        else if (Num(X)){

        }
        else{

        }    
    }
    //<EXPRE>::=<FACTOR><AUX7>
    public void Expre(){
        Factor();
        Aux7();
    } 
    // <AUX7>::=<SIMART><FACTOR><AUX7>
    public void Aux7(){
        //TODO: Checar si aquí se debe avanzar un token
        String X = " ";
        if(!X.equals(")") && !X.equals("=?") && !X.equals("=/") && !X.equals(">>") && !X.equals("=>")  && !X.equals("<<") && !X.equals("=<") && !X.equals("THEN") && !X.equals("RUN") && !X.equals("RD") && !X.equals("IDENT") && !X.equals("STR") && !X.equals("WR")  && !X.equals("WHEN") && !X.equals("WHL") && !X.equals(";") && !X.equals(",")){
        Simart(X);
        Factor();
        //Aux7();
        }
        else{
            c.previous_token();
        }
    }    
    public void Simart(String X){
        boolean resultado;
        switch(X){
            case "*":
                resultado = true;
            break;
            case "/":
                resultado = true;
            break;
            case "+":
                resultado = true;
            break;
            case "-":
                resultado = true;
            break;
            default:
            //Error(390);

        }
    }
    /*
    <FACTOR>::= <IDENT>
    <FACTOR>::= <NUM>
    <FACTOR>::= (<EXPRE>)

    */
    public void Factor(){
        //TODO: Checar si aquí se debe avanzar un token
        String X = " ";
        if(Ident(X)){
            X = c.nextToken();
        }else if(Num(X)){
            X = c.nextToken();
        }else if(X.equals("(")){
            Expre();
            if(X.equals(")")){
                
            }
            else{
                
            }
        }else{
            //c.previous_token();
        }
    }
    //<AUXVAR>:== VAR <TIPOS> <IDENT><AUX2>
    public void Auxvar(){
        String X = c.nextToken();
        if(!X.equals("PRO") && !X.equals("IDENT") && !X.equals("RUN") && !X.equals("RD") && !X.equals("WR")  && !X.equals("STR") && !X.equals("WHEN") && !X.equals("WHL")){
        
        if(X.equals("VAR")){
            
            X = c.nextToken();
            if(Tipos(X)){
                tipos.add(X);
                if(IdentDeclaration(X)){
                    Aux2();
                }
                else{
                    Error(12);
                }
            }else{
                Error(13);
            }
        }
        else{
            Error(6);
        }
    }
    else{
        c.previous_token();
    }
    }
    
    /*
    <AUX2>:== , <IDENT> <AUX2>
    <AUX2>:== ;
    <AUX2>:== CON <IDENT> = <NUM> <AUX2>
    <AUX2>:==Epsilon
*/
    public void Aux2(){
        String X = c.nextToken();
        if(!X.equals("PRO") && !X.equals("IDENT") && !X.equals("RUN") && !X.equals("RD") && !X.equals("WR")  && !X.equals("STR") && !X.equals("WHEN") && !X.equals("WHL")){
        if(X.equals(",")){
            tipos.add("CON");
            IdentDeclaration(X);
            Aux2();
        }
        else if(X.equals("CON")){
                tipos.add(X);
                IdentDeclaration(X);
                X = c.nextToken();
                if(X == "="){
                    Num(X);
                    Aux2();
                }
            }
            else if(X.equals(";")){

            }}
        else{
            c.previous_token();
        }
        }
    
    //<AUXPRO>:== PRO <IDENT> => <BLOQUE>; <AUXPRO>
    public void Auxpro(){
        String X = c.nextToken();
        if(!X.equals("IDENT") && !X.equals("RUN") && !X.equals("RD")  && !X.equals("WR") && !X.equals("STR") && !X.equals("WHL") && !X.equals("WHEN")){
    
        if(X.equals("PRO")){
            tipos.add("PRO");
            if(IdentDeclaration(X)){
                
            }else{
                Error(8);
            }
            

        }else{
            Error(7);
        }

    }
    else{
        c.previous_token();
    }
    }
    public boolean Tipos( String X ){
        boolean validacion = false;
        String[] tipos = new String[]{"ENT","PUN","STR"};
        for(int i=0;i<tipos.length;i++){
            if(X.equals(tipos[i])){
              
                validacion = true;
            }

        }
        return validacion;
    }

    public int returnIndexOf(ArrayList<String> lista, String buscado){
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).equals(buscado)){
                return i;
            }
        }
        return -1;
    }
    /*<AUXCON>::=CON <IDENT> = <NUM><AUX1>
    FOLLOWS(AUXCON) = {FIRST(AUXVAR)} = {“VAR” + FIRST(AUXPRO)}} = 
    {“VAR + “PRO” + FIRST(PROPOSICION) = 
    { “VAR” + “PRO” +  “IDENT” + “RUN” + “RD” + “WR” + “STR” + “WHEN” + “WHL”}*/

    public void Auxcon(){
        String X = c.nextToken();
        String identificador;
        String valor;
        if(!X.equals("VAR") && !X.equals("PRO") && !X.equals("IDENT") && !X.equals("RUN") && !X.equals("RD")  && !X.equals("WR") && !X.equals("STR") && !X.equals("WHL") && !X.equals("WHEN")){
            
            if(X.equals("CON")){
                //X = c.nextToken();
                tipos.add(X);
            if(IdentDeclaration(X)){
                identificador = X;
                X = c.nextToken();
                if(X.equals("=")){
                    if(Num(X)){
                        Aux1();
                       /* try{
                        valor = Integer.parseInt(X);
                        }catch(NumberFormatException e){
                            Error(5);
                        }*/

                    }
                }
                else{
                    Error(4);
                }
            }
            else{
                Error(3);
            }
        }
        else{
            Error(2);
        }
    }
    else{
        c.previous_token();
    }
}
    /* <AUX1>:== ;
    <AUX1>:==, CON <IDENT> = <NUM> <AUX1>
    */
    public void Aux1(){
        String X = c.nextToken();
        // if(X.equals("CON")){
        //     tipos.add(X);
        //     IdentDeclaration(X);
        //     if(X.equals("=")){
        //         Num(X);
        //         Aux1();
        //     }
        // else if(X.equals(";")){

            if(X == "CON"){
                IdentDeclaration(X);
                if(X == "="){
                    Num(X);
                    Aux1();
                }
            else if(X == ";"){

        }
        else{
            Error(10);
        }
        }
    }
//<NUM>
public boolean Num( String X ){
    boolean es_Valido = true;
    char caracter;
    caracter = X.charAt(0);
    for(int i = 1; i<X.length(); i++){
    if( caracter>=48 && caracter<=57 ){
        caracter = X.charAt(i);   
    }
    else{
        es_Valido = false;
        break;
    }
}
    return es_Valido;
}
//<IDENT>
public boolean Ident( String X ){
    X = c.nextToken();
    boolean es_valido = true;
    String[] palabras_invalidas=new String[]{"VAR","PRO","ENT","PUN","STR",
    "CHR","RUN","RD","WR",">STR","END<","WHEN","THEN","WHL","RUN","=","=?","=/",
    "<<","=<",">>","=>","+","-","*","/",".",",",";","(",")","NUM","IDENT"};
    
    for(int i = 0; i < palabras_invalidas.length; i++){
        if(X.equals(palabras_invalidas[i])){
            es_valido = false;
            break;
        }
    }

    if(!existeIdentificador(X)){
        System.out.print("Error en: "+X+", ");
        Error(15);
    }

    if(es_valido == false){
        c.previous_token();
    }

   return es_valido;
}

public boolean IdentDeclaration( String X ){
    X = c.nextToken();
    boolean es_valido = true;
    String[] palabras_invalidas=new String[]{"VAR","PRO","ENT","PUN","STR",
    "CHR","RUN","RD","WR",">STR","END<","WHEN","THEN","WHL","RUN","=","=?","=/",
    "<<","=<",">>","=>","+","-","*","/",".",",",";","(",")","NUM","IDENT, "};
    
    for(int i = 0; i < palabras_invalidas.length; i++){
        if(X.equals(palabras_invalidas[i])){
            es_valido = false;
            break;
        }
    }

    if(existeIdentificador(X)){
        System.out.print("Error en: "+X+", ");
        Error(14);
    }

    if(es_valido == false){
        c.previous_token();
    }else{
        identificadores.add(X);
    }

   return es_valido;
}

public Boolean existeIdentificador(String identificador){
    for(int i = 0; i < identificadores.size(); i++){
        if(identificador.equals(identificadores.get(i))){
            return true;
        }
    }
    return false;
}
    
    public void Error(int i){
        compilo = false;
        switch(i){
            case 1:
                System.err.println("Error en PROGRAMA, se esperaba un punto.");
                break;
            case 2: 
                System.err.println("Error en AUXCON, se esperaba la palabra const");
                break;
            case 3:
                System.err.println("Error en AUXCON, se esperaba un identificador");
                break;
            case 4:
                System.err.println("Error en AUXCON, se esperaba un igual");
                break;
            case 5:
                System.err.println("Error en AUXCON, error en la asignacion del numero");
                break;
            case 6:
                System.err.println("Error en AUXVAR, se esperaba la palabra VAR");
                break;
            case 7:
                System.err.println("Error en AUXPRO, se esperaba la palabra PRO");
                break;
            case 8:
                System.err.println("Error en AUXPRO, se esperaba  un identificador");
                break;
            case 9:
                System.err.println("Error en PROPOSICION, identificador requerido");
                break;
            case 10:
                System.err.println("Error en AUX1, se esperaba un CON o un ;");
                break;
            case 11:
                System.err.println("Error en AUX1, se esperaba un CON o un ;");
                break;
            case 12:
                System.err.println("Error en AUXVAR, se esperaba un IDENTIFICADOR");
                break;
            case 13:
                System.err.println("Error en AUXVAR, se esperaba un TIPO");
                break;
            case 14:
                System.err.println("El IDENTIFICADOR ya existe.");
                break;
            case 15:
                System.err.println("IDENTIFICADOR no declarado.");
                break;
            case 16:
                System.err.println("Error en PROPOSICION, tipo de dato no válido.");
                break;
            default:
            System.err.println("Error desconocido");
            break;
        }

    }

    public static void main(String[]as){
        Compilador c = new Compilador();
        c.Programa();
        // for(int i = 0;i<identificadores.size();i++){
        //     System.out.print("\n"+tipos.get(i)+ " ");
        //     System.out.print(identificadores.get(i)+"\n");
        // }
    }
}

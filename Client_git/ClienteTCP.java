/**
 * 
 * @ Victor Aldama Mesado & Emilio Molina Tercero
 * @version (02/11/2017)
 */
import java.net.*;
import java.io.*;
import java.util.*;
import java.security.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class ClienteTCP {
    Socket s;
    PrintWriter out;
    BufferedReader in;
    String idSession;
    String recibido;
    String pw;
    String MD5;
    Scanner kbd;
    ClienteTCP(){
        String idSession = "111111";
    } // fin constructor
    public int connect() //Inicializa las conexiones de socket, bufferedReader y PrintWriter
    {
        try {
            s=new Socket("192.168.1.3",5000);
            in=new BufferedReader (new InputStreamReader(s.getInputStream()));
            out=new PrintWriter (new OutputStreamWriter(s.getOutputStream()));


        } catch (IOException e) {
            System.out.println(e);
            return 1;
        }
        return 0;
    }

    private int checkAnswer(String answer)
    {
        if (answer.startsWith("200"))
            return 0;
        else 
            return 1;
    }// fin checkAnswer, comprueba que la respuesta del servidor es la correcta.
    ////////////////////////////////////////////////////////////
    public int hello(String ip) //Este método manda un primer mensaje al servidor para comprobar que la conexión se ha establecido
    {
        String answer="";
        System.out.println("HELLO "+ ip);
        out.println("HELLO "+ ip);
        out.flush();

        try{
            answer=in.readLine();
            System.out.println(answer);
        }
        catch (IOException e) {
            System.out.println("ERROR: No llega respuesta de hello");
            System.out.println(e);

        }

        if (checkAnswer(answer)==0)
        {

            String[] results =answer.split(" ");
            idSession = results[2];
            System.out.println("id session = "+idSession);
            return 0;
        }

        return 1;   
    }

    public int user(String email) // Este método envía el usuario al servidor
    {
        String answer="";
        out.println("USER "+ email);
        out.flush();
        try{
            answer=in.readLine();
            System.out.println(answer);
        }
        catch (IOException e) {
            System.out.println("ERROR: No llega respuesta de user");
            System.out.println(e);

        }
        if (checkAnswer(answer)==0)
            return 0;
        return 1;
    }

    public int pass(String grupo) // Este método envía la pass al servidor
    {
        String hash=createPass(grupo);
        String answer="";
        System.out.println("PASS "+ hash);
        out.println("PASS "+ hash);
        out.flush();
        try{
            answer=in.readLine();
            System.out.println(answer);
        }
        catch (IOException e) {
            System.out.println("ERROR: Pass");
            System.out.println(e);
        }
        if (checkAnswer(answer)==0){
            return 0;}

        return 1;       
    }

    private String createPass(String grupo) // Este método se encarga de crear la contraseña, concatenando y haciendo el hash
    {  
        pw= grupo+idSession;
        String pwcoded = encriptaEnMD5(pw);
        return pwcoded;
    } // fin createPass.
    public static String encriptaEnMD5(String stringAEncriptar) // Este método calcula el Hash MD5 del mensaje
    {
        char[] CONSTS_HEX = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };
        try
        {
            MessageDigest msgd = MessageDigest.getInstance("MD5");
            byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
            StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++)
            {
                int bajo = (int)(bytes[i] & 0x0f);
                int alto = (int)((bytes[i] & 0xf0) >> 4);
                strbCadenaMD5.append(CONSTS_HEX[alto]);
                strbCadenaMD5.append(CONSTS_HEX[bajo]);
            }
            return strbCadenaMD5.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    } 

    public int startData() // Este método inicia el flujo de datos
    {
        String answer="";
        System.out.println("Inicia data");

        out.println("DATA");
        out.flush();
        try{
            answer=in.readLine();
            System.out.println(answer);
        }
        catch (IOException e) {
            System.out.println("ERROR: DATA");
            System.out.println(e);
        }

        
        if (checkAnswer(answer)==0)
            return 0;
        return 1;}

    public int elegirUser() // Este método inicia el flujo de datos
    {
        String answer="";
        kbd=new Scanner(System.in);
        String elegiruser="";
        System.out.println("Elige con quien quieres hablar");

        try{
            answer=in.readLine();
            System.out.println(answer);
        }
        catch (IOException e) {
            System.out.println("ERROR: DATA");
            System.out.println(e);
        }

        elegiruser=kbd.nextLine();
        out.println(elegiruser);
        out.flush();
        return 0;}

    public void data() // Este método envía datos
    {
        kbd=new Scanner(System.in);
        String mensaje="";
        String [] aux= {"","","",""};
        
        while(!mensaje.equals(".")){
            mensaje=kbd.nextLine();
            if(mensaje.equals("Send_Picture")){
                TakePictureExample2.main(aux);
                }
            else { out.println(mensaje);
                out.flush();
                kbd.nextLine();}

        }
    }

    public int finData() //este método corta el flujo de datos si detecta un "."
    {
        String answer="";
        System.out.println("- .");
        out.println(".");
        out.flush();
        try{
            answer=in.readLine();
            System.out.println(answer);
        }
        catch (IOException e) {
            System.out.println("ERROR: DATA");
            System.out.println(e);
        }
        if (checkAnswer(answer)==0)
            return 0;
        return 1;}

    public void quit() //este método cierra conexion con servidor
    {
        System.out.println("QUIT");
        out.println("QUIT");
        out.flush();
    }

    public void disconnect() throws Exception//Cierra las conexiones de socket, bufferedReader y PrintWriter

    {
        out.close();
        in.close();
        s.close();  
    }

    public static void main(String args[]) throws Exception{
        InetAddress ipLocal = InetAddress.getLocalHost();
        String ip = "192.168.1.3";
        Scanner kbd=new Scanner(System.in);
        Socket s;
        PrintWriter out;
        BufferedReader in;
        String idSession;
        int puerto= 5000; 
        int ans=1;
        String recibido ="";
        int checkAnswer;
        System.out.println("Introduzca email");
        String email = "";
        email=kbd.nextLine();
        System.out.println("Introduzca A3A");
        String grupo = "";
        grupo=kbd.nextLine();

        ClienteTCP clienteTCP = new ClienteTCP();

        
       
        if (clienteTCP.connect()!=0)
        {
            System.out.println("ERROR: No se puede conectar con el servidor");
            return;
        }

        if (clienteTCP.hello(ip)!=0)
        {
            System.out.println("ERROR: No se puede completar hello");
            return;
        }

        if (clienteTCP.user(email)!=0)
        {
            System.out.println("ERROR: No se puede completar user");
            return;
        }

        if (clienteTCP.pass(grupo)!=0)
        {
            System.out.println("ERROR: No se puede completar pass");
            return;
        }
        
        clienteTCP.data();
        //          if (clienteTCP.elegirUser()!=0)
        //         {
        //             System.out.println("ERROR: No se puede completar startData");
        //             return;
        //         }
        if (clienteTCP.startData()!=0)
        {
            System.out.println("ERROR: No se puede completar startData");
            return;
        }

        if (clienteTCP.finData()!=0)
        {
            System.out.println("ERROR: No se puede completar finData");
            return;
        }

        clienteTCP.quit();
        clienteTCP.disconnect();
    }
}

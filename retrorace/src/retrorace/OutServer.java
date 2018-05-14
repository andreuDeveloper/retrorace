/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Andreu
 */
public class OutServer extends Thread{

    //Atributes
    private int PORT;
    private String HOST;
    private Socket socket;
    private final ClientProject cp;

    /**
     * Constructor
     * @param cp ClientProject 
     */
    public OutServer(ClientProject cp) {
        this.cp = cp;
    }

    /**
     * Seteamos el puerto al que nos queremos conectar
     * @param PORT 
     */
    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    /**
     * Seteamos el host al que nos queremos conectar
     * @param HOST 
     */
    public void setHOST(String HOST) {
        this.HOST = HOST;
    }
    
    /**
     * Loop trying to connect to the server
     */
    @Override
    public void run(){
        while (true) {
            if (!this.cp.hayServer()) {
                tryToConnect();
            }
            //Sleep 1.5 sec
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    /**
     * It tries to connect to the server doing a new socket 
     * If the connection is succesfull, send a message to the server saying that
     * I'm a client
     */
    public void tryToConnect(){
        try {
            socket = new Socket(HOST, PORT);
            System.out.println("Connected to: " + HOST + ":" + PORT);
            this.cp.addServer(socket);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("$CLIENT-RETRORACE$");
        } catch (IOException e) {
            System.out.println("Cant connect to: "+ e.getMessage());
        }
    }


}

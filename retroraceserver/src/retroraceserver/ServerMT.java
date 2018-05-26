/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retroraceserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Andreu
 */
public class ServerMT extends Thread {

    //Atributos
    private final ServerProject sp;
    private final int PORT;
    private ServerSocket serverSocket;
    private Socket socket;

    /**
     * Constructor server MT (multithreading)
     *
     * @param sp ServerPorject
     * @param PORT Rendez-vous port
     */
    public ServerMT(ServerProject sp, int PORT) {
        System.out.println("ServerMT created");
        this.sp = sp;
        this.PORT = PORT;
    }

    /**
     * Loop listening all the petitions of the port
     */
    @Override
    public void run() {
        try {
            System.out.println("ServerMT started and listening at " + PORT);
            this.sp.addMessageToLog("ServerMT started and listening at " + PORT);
            this.serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("Waiting someone...");
                this.sp.addMessageToLog("Waiting someone");
                this.socket = this.serverSocket.accept();

                checkIfIsClient(socket);
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "PORT server already in use");
            System.exit(0);
        }
    }

    /**
     * Method that check if the in is from a client or other font and do
     * the pertinent thinhs
     *
     * @param in
     */
    private void checkIfIsClient(Socket socket) {

        try {
            String host = socket.getInetAddress().getHostAddress();
            System.out.println("HOST: " + host + " detected");
            sp.addMessageToLog("HOST: " + host + " detected");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = in.readLine().toUpperCase();
            System.out.println("LINE DETECTED: "+ line);
            switch (line) {
                case "$PLAYER-RETRORACE$": //Client
                    System.out.println("Adding new client");
                    this.sp.addClient(this.socket);
                    break;
                default:
                    System.out.println("Ignoring connection request");
                    sp.addMessageToLog("Ignoring connection request");
            }
        } catch (IOException | NumberFormatException ex) {
            System.out.println(ex.toString());
            
        }
    }

}

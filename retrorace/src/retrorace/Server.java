/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

/**
 *
 * @author Andreu
 */
public class Server implements Runnable {

    private Partida partida;
    private final ClientProject cp;
    private final Socket socket;
    private final String address;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Constructor
     *
     * @param cp ClientProject
     * @param socket Socket del cliente-servidor
     */
    public Server(ClientProject cp, Socket socket) {
        this.cp = cp;
        this.socket = socket;
        this.address = socket.getInetAddress().getHostAddress();
        System.out.println("Server created");
    }

    /**
     * Loop that will process the in / out of the server, processing that
     * petitions
     */
    @Override
    public void run() {
        try {
            System.out.println("Thread for Server created");
            // Get I/O streams from the socket
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            processServer(in, out); // interact with a client

            // Close client connection
            socket.close();
            System.out.println("Client (" + address + ") connection closed\n");
            cp.removeServer();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Process the in of the server if it have sense
     *
     * @param in
     * @param out
     */
    public void processServer(BufferedReader in, PrintWriter out) {
        String line;
        boolean done = false;
        try {
            while (!done) {
                if ((line = in.readLine()) == null) {
                    done = true;
                } else {
                    System.out.println("Server sent something");
                    System.out.println(line);
                    String parts[] = line.split(",");
                    if (parts.length > 0) {
                        switch (parts[0]) {
                            case "$ID$":
                                this.partida.getPersonaje(0).setUniqueID(parts[1]);
                                break;
                            case "$ALL-PLAYERS$":
                                addNewPlayers(line);
                                break;
                            case "$PLAYER-STATUS$":
                                setPlayerStatus(line);
                            default:
                                System.out.println("Ignoring input line");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * It recievs a msg line of the server and get the username and the msg of
     * it
     *
     * @param line
     */
    protected void getMessage(String line) {
        try {
            line = line.substring(3).trim();
            System.out.println(line);
            line = line.replace(" &", ":");
            System.out.println(line);
        } catch (Exception ex) {
            ex.printStackTrace();
            
        }
    }

    /**
     * Send message to the server specificating the user and the message
     *
     * @param user
     * @param msg
     */
    public void sendMessage(String msg) {
        String s = "$PLAYER-STATUS$," + msg;
        //System.out.println("SENDING MSG "+s);
        out.println(s);
    }

    private void addNewPlayers(String line) {
        String parts[] = line.split(",");
        for (int i = 1; i < parts.length; i++) {
            boolean nuevo = true;
            //Unique ID del jugador
            for (int p = 0; p < partida.totalPersonajes() && nuevo; p++) {
                if (partida.getPersonaje(p).getUniqueID().equals(parts[i])) {
                    nuevo = false;
                }
            }
            if (nuevo) {
                Personaje personaje = new Personaje(partida);
                personaje.setUniqueID(parts[i]);
                personaje.setEsJugadorOnline(true);
                partida.addPersonaje(personaje);
            }
        }
    }

    private void setPlayerStatus(String line) {
        String parts[] = line.split(",");
        for (int i = 1; i < this.partida.totalPersonajes(); i++) {
            if (this.partida.getPersonaje(i).getUniqueID().equals(parts[1])){
                Personaje p = this.partida.getPersonaje(i);
                p.setX(Float.parseFloat(parts[2]));
                p.setY(Float.parseFloat(parts[3]));
                p.setJumping(Boolean.valueOf(parts[4]));
                p.setMuerto(Boolean.valueOf(parts[5]));
                System.out.println(p.getInfo());
            }
        }
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

}

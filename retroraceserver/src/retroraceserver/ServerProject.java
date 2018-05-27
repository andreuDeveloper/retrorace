/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retroraceserver;

import handlers.Client;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author Andreu
 */
public class ServerProject {

    //Server Project
    private final ServerFrame serverFrame;

    private int myPORT;
    private ServerMT serverMT;

    private ArrayList<Client> players;
    private String colores[] = {"Blue", "Green", "Red", "White"};

    /**
     * Just the constructor, initialize the server and clients list
     *
     * @param serverFrame ServerFrame
     */
    public ServerProject(ServerFrame serverFrame) {
        this.serverFrame = serverFrame;
        this.players = new ArrayList<>();

    }

    /**
     * Init the serverMT (for listening) and the outServer (to speak) with the
     * other server
     *
     * @param myPORT
     * @param outServerHOST
     * @param outServerPORT
     */
    public void initServerProject(int myPORT) {
        this.myPORT = myPORT;
        this.serverMT = new ServerMT(this, myPORT);
        this.serverMT.start();
    }

    /**
     * Add a new client to the list using the socket
     *
     * @param sock Socket of the client - server
     */
    public synchronized void addClient(Socket sock) {
        if (players.size() <= 4) {
            Client c = new Client(this, sock);
            this.players.add(c);
            c.setColor(colores[players.size() - 1]);
            new Thread(c).start();
            System.out.println("ServerProject trying to add client");
            addMessageToLog("Added a client");
        } else {
            addMessageToLog("Server is full (4 players)");
        }

    }

    public synchronized void sendAllPlayers() {
        String msg = "$ALL-PLAYERS$";
        for (int i = 0; i < this.players.size(); i++) {
            msg += "," + this.players.get(i).getUniqueID();
        }

        for (int i = 0; i < this.players.size(); i++) {
            // debe hacer if / else --> !cliente
            players.get(i).sendMessage(msg);
        }
    }

    /**
     * Remove the client of the list
     *
     * @param c Client to remove
     */
    public synchronized void removeClient(Client c) {
        this.players.remove(c);

        System.out.println("Removed client");
        addMessageToLog("Client removed");
    }

    /**
     * Hace un broadcaset del mensaje excepto al cliente c y al otro server
     *
     * @param c Cliente c
     * @param msg Mensaje
     */
    public synchronized void doBroadcastMsgFromClient(Client c, String msg) {
        //Broadcast to all the clients except the Client c
        for (int i = 0; i < this.players.size(); i++) {
            // debe hacer if / else --> !cliente
            if (!c.equals(players.get(i))) {
                players.get(i).sendMessage(msg);
            }
        }
    }

    /**
     * Return the port number of out server
     *
     * @return port number
     */
    public int getMyPort() {
        return this.myPORT;
    }

    /**
     * Parse the host to a host adress (Localhost is 127.0.0.1)
     *
     * @param host
     * @return address
     */
    public String parseHost(String host) {
        try {
            return InetAddress.getByName(host).getHostAddress();
        } catch (UnknownHostException ex) {
            addMessageToLog("The port name not exists");
            System.out.println("The port name not exists");
            return null;
        }
    }

    /**
     * Return our number of clients linked to our server
     *
     * @return Number of clients
     */
    public int getNumberOfClients() {
        return this.players.size();
    }

    /**
     * Add a message to the log frame
     *
     * @param msg Msg to add
     */
    public void addMessageToLog(String msg) {
        this.serverFrame.addMessageToLog(msg);
    }

    public String getRandomColor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

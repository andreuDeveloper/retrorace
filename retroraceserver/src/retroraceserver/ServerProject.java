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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andreu
 */
public class ServerProject {

    //Server Project
    private final ServerFrame serverFrame;

    private int myPORT;
    private ServerMT serverMT;

    private final ArrayList<Client> clients;

    /**
     * Just the constructor, initialize the server and clients list
     *
     * @param serverFrame ServerFrame
     */
    public ServerProject(ServerFrame serverFrame) {
        this.serverFrame = serverFrame;
        this.clients = new ArrayList<>();

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
        Client c = new Client(this, sock);
        this.clients.add(c);
        new Thread(c).start();

        System.out.println("ServerProject trying to add client");
        addMessageToLog("Added a client");
    }

    /**
     * Remove the client of the list
     *
     * @param c Client to remove
     */
    public synchronized void removeClient(Client c) {
        this.clients.remove(c);

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
        System.out.println("Broadcasting msg.. to " + (clients.size() - 1));
        addMessageToLog("Broadcasting msg.. to " + (clients.size() - 1));

        for (int i = 0; i < this.clients.size(); i++) {
            // debe hacer if / else --> !cliente
            if (!c.equals(clients.get(i))) {
                clients.get(i).sendMessage(msg);
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
        return this.clients.size();
    }

    /**
     * Add a message to the log frame
     *
     * @param msg Msg to add
     */
    public void addMessageToLog(String msg) {
        this.serverFrame.addMessageToLog(msg);
    }

}

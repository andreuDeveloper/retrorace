
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.net.Socket;

/**
 *
 * @author Andreu
 */
public class ClientProject {

    private final OutServer outServer;

    private Server server;
    private String host;
    private int port;
    private Partida partida;
    

    public ClientProject() {
        //this.chatFrame = chatFrame;
        this.outServer = new OutServer(this);
    }

    /**
     * Will start the connection to that server
     *
     * @param userName Username of the chat
     * @param host Host to do connection
     * @param port Port to do connection
     */
    public void createConnection(String host, int port) {
        this.host = host;
        this.port = port;
        this.outServer.setHOST(host);
       //this.outServer.setHOST("localhost");
        this.outServer.setPORT(port);
        this.outServer.setConnecting(true);
        //this.outServer.setPORT(8888);
        this.outServer.start();
    }

    /**
     * Send the message to the server
     *
     * @param msg Message to send
     */
    public void sendMessage(String msg) {
        System.out.println("MESSAGE SENDED: " + msg);
    }

    /**
     * Information about there is a server connected or not
     *
     * @return True if connected false if not
     */
    public boolean hayServer() {
        return (this.server != null);
    }

    /**
     * Add a new server with the specificated socket and inizialize the thread
     *
     * @param sock Socket for communicate with the server
     */
    public void addServer(Socket sock) {
        this.server = new Server(this, sock);
        this.server.setPartida(this.partida);
        
        new Thread(this.server).start();
        this.partida.setServer(server);

        System.out.println("Server created");
    }

    /**
     * It remove the actual server putting him in null
     */
    public void removeServer() {
        this.server = null;
        System.out.println("Server Removed");

    }

    void setPartida(Partida partida) {
        this.partida = partida;
    }

    void closeConnection() {
        this.outServer.setConnecting(false);
    }

}

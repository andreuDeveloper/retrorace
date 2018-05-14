
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

    private final OutServer serverHandler;

    private Server server;
    private String host;
    private int port;
    
    private String userName;

    public ClientProject() {
        //this.chatFrame = chatFrame;
        this.serverHandler = new OutServer(this);
    }

    /**
     * Will start the connection to that server
     *
     * @param userName Username of the chat
     * @param host Host to do connection
     * @param port Port to do connection
     */
    public void createConnection(String userName, String host, int port) {
        this.userName = userName;
        this.host = host;
        this.port = port;
        this.serverHandler.setHOST(host);
        this.serverHandler.setPORT(port);
        this.serverHandler.start();
    }

    /**
     * Send the message to the server
     *
     * @param msg Message to send
     */
    public void sendMessage(String msg) {
        System.out.println("MESSAGE SENDED: " + msg);
        this.server.sendMessage(this.userName, msg);
    }

    /**
     * Add a new message to the text area
     *
     * @param msg
     */
    public void addMessage(String msg) {
        System.out.println("MESSAGE RECEIVED: " + msg);
        //this.chatFrame.addMessage(msg);
        
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
        new Thread(this.server).start();

        System.out.println("Server created");
        this.addMessage("Connected to: " + this.host + " : " + this.port);
    }

    /**
     * It remove the actual server putting him in null
     */
    public void removeServer() {
        this.server = null;

        System.out.println("Server Removed");
        this.addMessage("Connection lost, trying to reconnect ...");
    }

}

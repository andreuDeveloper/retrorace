
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retroraceserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Andreu
 */
public class ServerFrame extends JFrame implements ActionListener {

    //FRAME
    private JTabbedPane jTabPane;
    private JPanel jPanelConfig;

    private JPanel jPanelLog;
    private JTextArea jTxAreaLog;
    private JScrollPane jScrollLog;

    private JLabel jLbTitle;
    private JLabel jLbMyServer;
    private JTextField jTxHostMyServer;
    private JTextField jTxPortMyServer;
    private JButton jBtnConnect;

    //Clases
    private ServerProject serverProject;

    /**
     * Just the constructor, that creates the Frame and initialize the server
     * and clients list
     */
    public ServerFrame() {
        serverProject = new ServerProject(this);

        initWindow();
        initComponentsWindow();
    }

    /**
     * Action performed of the window
     *
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource().equals(this.jBtnConnect)) {
            if (checkConfigurations()) {
                createConnection();
                this.jTxPortMyServer.setEnabled(false);
                this.jBtnConnect.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "Wrong configuration");
            }
        }
    }


    /** 
     * It add a message to the console log of the frame
     * @param msg Message to add
     */
    public void addMessageToLog(String msg) {
        this.jTxAreaLog.setText(this.jTxAreaLog.getText() + "\n" + msg);
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    private ImageIcon createImageIcon(String path) {
        if (path != null) {
            return new ImageIcon(path);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Check if the configurations are ok
     * @return True if ok, no if not
     */
    private boolean checkConfigurations() {
        //Check incomplete fields and that are port numbers
        try {
            if ((jTxHostMyServer.getText().length() > 0) && (jTxPortMyServer.getText().length() == 4)) {
                Integer.parseInt(jTxPortMyServer.getText());
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Init the basic things of the window
     */
    private void initWindow() {
        this.setTitle("Server");
        this.setSize(800, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    /**
     * Init the components of the windows
     */
    private void initComponentsWindow() {
        //TabPane
        this.jTabPane = new JTabbedPane();

        this.jPanelConfig = new JPanel();
        this.jPanelConfig.setLayout(new GridBagLayout());
        this.jPanelConfig.setBackground(new Color(0x263238));

        this.jPanelLog = new JPanel();
        this.jPanelLog.setLayout(new BorderLayout());

        jTabPane.addTab("  CONFIGURATION  ", createImageIcon("./img/settings.png"), jPanelConfig);
        jTabPane.addTab("  LOG  ", createImageIcon("./img/log.png"), jPanelLog);

        Container cpanel = this.getContentPane();
        cpanel.add(jTabPane);

        //Components CONFIG
        this.jLbTitle = new JLabel("RETRORACE GAME - SERVER CONFIGURATION");
        this.jLbTitle.setHorizontalAlignment(0);
        this.jLbTitle.setForeground(Color.white);
        this.jLbTitle.setFont(new Font("Arial", 1, 25));
        this.jLbMyServer = new JLabel("My Server");
        this.jLbMyServer.setHorizontalAlignment(0);
        this.jLbMyServer.setForeground(Color.white);
        this.jLbMyServer.setFont(new Font("Arial", 1, 15));
        Font f = new Font("Arial", 1, 14);
        this.jTxHostMyServer = new JTextField("localhost");
        this.jTxHostMyServer.setEnabled(false);
        this.jTxHostMyServer.setHorizontalAlignment(0);
        this.jTxHostMyServer.setFont(new Font("Arial", 3, 13));
        this.jTxPortMyServer = new JTextField("8888");
        this.jTxPortMyServer.setHorizontalAlignment(0);
        this.jTxPortMyServer.setFont(f);
        this.jBtnConnect = new JButton("CONNECT");
        this.jBtnConnect.addActionListener(this);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1; //Esto es como ser redimensiona 
        c.weightx = 1;

        //Titulo
        c.weighty = 1;
        c.insets = new Insets(10, 0, 10, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.gridheight = 1;
        this.jPanelConfig.add(this.jLbTitle, c);

        //LABELS SERVERS
        c.weighty = 0.2;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.jPanelConfig.add(this.jLbMyServer, c);

        //TextFields HOST
        c.insets = new Insets(10, 5, 10, 5);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.jPanelConfig.add(this.jTxHostMyServer, c);

        //TextFields PORT
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.jPanelConfig.add(this.jTxPortMyServer, c);

        //lABELS
        c.insets = new Insets(10, 30, 10, 10);
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        this.jPanelConfig.add(this.jBtnConnect, c);

        //Components LOG
        this.jTxAreaLog = new JTextArea();
        this.jTxAreaLog.setFont(new Font("Arial", Font.PLAIN, 20));
        this.jTxAreaLog.setBackground(new Color(0x263238));
        this.jTxAreaLog.setForeground(Color.WHITE);
        this.jTxAreaLog.setEditable(false);
        this.jTxAreaLog.setLineWrap(true);
        this.jTxAreaLog.setWrapStyleWord(true);
        this.jTxAreaLog.setBorder(BorderFactory.createCompoundBorder(
                jTxAreaLog.getBorder(),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        DefaultCaret caret = (DefaultCaret) jTxAreaLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        this.jScrollLog = new JScrollPane(jTxAreaLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.jPanelLog.add(jScrollLog, BorderLayout.CENTER);
    }

    /**
     * Createa connection main to the rendebo port
     */
    private void createConnection() {
        addMessageToLog("Trying to create a connection.. ");
        int myPort = Integer.parseInt(this.jTxPortMyServer.getText());
        this.serverProject.initServerProject(myPort);

        System.out.println("Configurated");
        System.out.println("MY PORT-> " + myPort);
        this.setTitle("Server - (localhost : " + myPort + ")");
        addMessageToLog("Configurated at localhost : " + myPort);
    }
}

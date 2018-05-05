/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


    
   
public class GUI extends JFrame implements ActionListener, KeyListener {

    private Juego juego;
    private Sesion sesion;

    private Gamescreen gamescreen;

    private JPanel panelLogin;
    private JPanel panelMenu;
    private JPanel panelMapChoice;
    private JPanel panelGamescreen;

    private JButton btnExit;
    private JButton btnBack;

    /*Login panel*/
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JLabel lblErrorLogin;

    /*Menu panel*/
    private JButton btnSinglePlayer;
    private JButton btnMultiPlayer;
    private JButton btnRanking;

    /*Choice Map panel*/
    private ArrayList<JButton> btnMap;

    //ranking
    /*CONSTRUCTOR*/
    public GUI(Juego j) {

        this.juego = j;
    }

    /*GETTERS Y SRTTERS*/
    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    public void initGUI() {
        this.setResizable(true);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        GraphicsDevice gd
                = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
        if (gd.isFullScreenSupported()) {
            setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            System.err.println("Full screen not supported");
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addUIComponents(getContentPane());

        setVisible(true);
    }

    void setPartidaInGamescreen(Partida partida) {
        gamescreen.setPartida(partida);
    }
    
    private void addUIComponents(Container panel) {
        panel.add(createComponentExit(), BorderLayout.NORTH);
        panel.add(createComponentLogin(), BorderLayout.CENTER);
        txtUsername.requestFocus();
    }

    private JPanel createComponentExit() {
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        btnBack = new JButton("Volver");
        btnBack.addActionListener(this);
        btnBack.setVisible(false);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0d;
        c.anchor = GridBagConstraints.LINE_START;

        p.add(btnBack, c);

        btnExit = new JButton("X");
        btnExit.addActionListener(this);

        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_END;

        p.add(btnExit, c);

        return p;
    }

    private JPanel createComponentGamescreen() {
        panelGamescreen = new JPanel();
        panelGamescreen.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //panelGamescreen.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panelGamescreen.add(gamescreen,c);
        panelGamescreen.setBackground(Color.BLACK);

        return panelGamescreen;
    }

    private JPanel createComponentLogin() {
        panelLogin = new JPanel();
        int margin = 2 * this.getWidth() / 5;
        panelLogin.setBorder(BorderFactory.createEmptyBorder(0, margin, 0, margin));
        panelLogin.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5, 5, 5, 5);

        JLabel lblWelcome = new JLabel("BIENVENIDO");
        lblWelcome.setFont(new Font("Arial Black", 0, 28));
        lblWelcome.setForeground(Color.BLUE);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.ipady = 35;

        c.fill = GridBagConstraints.CENTER;
        panelLogin.add(lblWelcome, c);

        JLabel lblUsername = new JLabel("Usuario");

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.ipady = 10;

        c.fill = GridBagConstraints.HORIZONTAL;
        panelLogin.add(lblUsername, c);

        txtUsername = new JTextField();
        txtUsername.addKeyListener(this);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.BOTH;
        panelLogin.add(txtUsername, c);

        JLabel lblPassword = new JLabel("Password");

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.HORIZONTAL;
        panelLogin.add(lblPassword, c);

        txtPassword = new JPasswordField();
        txtPassword.setEnabled(false);
        txtPassword.setEditable(false);//CAMBIA

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.BOTH;
        panelLogin.add(txtPassword, c);

        btnLogin = new JButton("Entrar");
        btnRegister = new JButton("Registrarse");
        btnLogin.addActionListener(this);
        btnRegister.addActionListener(this);
        btnRegister.setEnabled(false);//CAMBIA

        JPanel panelAuxBtn = new JPanel(new GridLayout(1, 2, 5, 5));
        panelAuxBtn.add(btnLogin);
        panelAuxBtn.add(btnRegister);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;

        c.weightx = 1.0d;

        panelLogin.add(panelAuxBtn, c);

        lblErrorLogin = new JLabel("");
        lblErrorLogin.setFont(new Font("Arial", 0, 12));
        lblErrorLogin.setForeground(Color.RED);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.CENTER;

        panelLogin.add(lblErrorLogin, c);

        /*c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        c.ipady=10;
        c.weightx=0.4d;
        
        panelLogin.add(btnLogin, c);
        
        
        c.gridx = 1;
        c.gridy = 5;
   
        panelLogin.add(btnRegister, c);*/
        return panelLogin;
    }

    private JPanel createComponentMapChoice() {
        panelMapChoice = new JPanel();
        int margin = 2 * this.getWidth() / 5;
        panelMapChoice.setBorder(BorderFactory.createEmptyBorder(0, margin, 0, margin));
        panelMapChoice.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        JLabel lblWelcomeMap = new JLabel("Elige un mapa");
        lblWelcomeMap.setFont(new Font("Arial Black", 0, 28));
        lblWelcomeMap.setForeground(Color.BLUE);

        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 35;

        c.fill = GridBagConstraints.CENTER;

        panelMapChoice.add(lblWelcomeMap, c);

        int numMaps = sesion.getMapas().size();
        btnMap = new ArrayList();
        
        sesion.loadMaps();
        
        JPanel panelAuxMaps = new JPanel(new GridLayout(sesion.getMapas().size(), 1, 5, 15));
        
        for(Mapa m:sesion.getMapas()){
            btnMap.add(new JButton(m.getNombre()));
        }

        for (JButton btnAux : btnMap) {
            panelAuxMaps.add(btnAux);
            btnAux.addActionListener(this);
        }

        c.gridx = 0;
        c.gridy = 1;
c.ipady=15;
        c.weightx = 1.0d;
        c.fill = GridBagConstraints.BOTH;

        panelMapChoice.add(panelAuxMaps, c);

        return panelMapChoice;
    }

    private JPanel createComponentMenu() {
        panelMenu = new JPanel();
        int margin = 2 * this.getWidth() / 5;
        panelMenu.setBorder(BorderFactory.createEmptyBorder(0, margin, 0, margin));
        panelMenu.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        JLabel lblWelcome = new JLabel("Bienvenido " + sesion.getUsername());
        lblWelcome.setFont(new Font("Arial Black", 0, 28));
        lblWelcome.setForeground(Color.BLUE);

        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 35;

        c.fill = GridBagConstraints.CENTER;

        panelMenu.add(lblWelcome, c);

        btnSinglePlayer = new JButton("Un jugador");
        btnSinglePlayer.addActionListener(this);

        btnMultiPlayer = new JButton("Multijugador");
        btnMultiPlayer.addActionListener(this);
        btnMultiPlayer.setEnabled(false);

        btnRanking = new JButton("Ranking");
        btnRanking.addActionListener(this);
        btnRanking.setEnabled(false);

        JPanel panelAuxMenu = new JPanel(new GridLayout(3, 1, 5, 15));
        panelAuxMenu.add(btnSinglePlayer);
        panelAuxMenu.add(btnMultiPlayer);
        panelAuxMenu.add(btnRanking);

        c.gridx = 0;
        c.gridy = 1;

        c.weightx = 1.0d;
        c.fill = GridBagConstraints.BOTH;

        panelMenu.add(panelAuxMenu, c);

        return panelMenu;
    }

    private void initSesion() {
        //CAMBIAR 
        String username = txtUsername.getText();
        if (checkUser(username)) {
            panelLogin.setVisible(false);
            juego.initSesion(username);
            this.getContentPane().add(createComponentMenu(), BorderLayout.CENTER);
        } else {
            lblErrorLogin.setText("ERROR: Introduce un nombre de usuario");
        }
    }

    private void initSinglePlayer() {
        panelMenu.setVisible(false);
        btnBack.setVisible(true);
        if (panelMapChoice == null) {
            this.getContentPane().add(createComponentMapChoice(), BorderLayout.CENTER);
        } else {
            panelMapChoice.setVisible(true);
        }

    }

    private void initPartida(int numMap) {
        panelMapChoice.setVisible(false);
        if (panelGamescreen == null) {
            gamescreen = new Gamescreen(this,sesion.initPartida(numMap));
            this.getContentPane().add(createComponentGamescreen(), BorderLayout.CENTER);
            gamescreen.setSize(getWidth(), getHeight() - btnExit.getHeight() - 2 * btnExit.getY());
//            gamescreen.setSize(1280, 700);
        } else {
            panelGamescreen.setVisible(true);
            gamescreen.setPartida(sesion.initPartida(numMap));
        }

        new Thread(this.gamescreen).start();
        this.gamescreen.setBackground(new Color(208, 244, 247));  
        this.gamescreen.requestFocus();
    }

    private boolean checkExit() {
        int resp = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return resp == 0;
    }

    private boolean checkUser(String username) {
        //CAMBIAR checkear user y password
        return !(username.equals(""));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnAux = (JButton) e.getSource();

        if (btnAux == btnExit) {
            if (checkExit()) {
                System.exit(0);
            }
        }

        //Acciones en panel login
        if (panelLogin.isVisible()) {
            if (btnAux == btnLogin) {
                initSesion();
            }
            //Acciones panel menu
        } else if (panelMenu.isVisible()) {
            if (btnAux == btnSinglePlayer) {
                initSinglePlayer();
            }
        } else if (panelMapChoice.isVisible()) {
            if (btnAux == btnBack) {
                panelMapChoice.setVisible(false);
                panelMenu.setVisible(true);
                btnBack.setVisible(false);
            }
            for (JButton btnAuxMap : btnMap) {
                if (btnAuxMap == btnAux) {
                    //System.out.println(btnMap.indexOf(btnAuxMap));
                    initPartida(btnMap.indexOf(btnAuxMap));
                }
            }
        }else if(panelGamescreen.isVisible()){
            if (btnAux == btnBack) {
                sesion.endPartida();
                panelMapChoice.setVisible(true);
                panelGamescreen.setVisible(false);

            }
        }
    }
    
     @Override
    public void keyTyped(KeyEvent ke) {
        //System.out.println("keyPressed="+KeyEvent.getKeyText(ke.getKeyCode()));
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getSource() == this.txtUsername) {
            if (ke.VK_ENTER == ke.getKeyCode()) {
                this.initSesion();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //System.out.println("keyPressed="+KeyEvent.getKeyText(ke.getKeyCode()));
    }

    
}

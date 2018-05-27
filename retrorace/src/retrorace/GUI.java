/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class GUI extends JFrame implements ActionListener, KeyListener {

    /*ATRIBUTOS*/
    private final Juego juego;
    private Sesion sesion;
    private Gamescreen gamescreen;

    /*Paneles*/
    private JPanel panelLogin;
    private JPanel panelRegister;
    private JPanel panelMenu;
    private JPanel panelMapChoice;
    private JPanel panelGamescreen;
    private JPanel panelRanking;
    private JPanel panelChoiceServerMultiplayer;

    /*Botones principales*/
    private JButton btnExit;
    private JButton btnBack;

    /*Login panel*/
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JLabel lblErrorLogin;
    private JLabel lblLoaderLogin;

    /*Register Panel*/
    private JTextField txtRegisterUsername;
    private JTextField txtRegisterPassword;
    private JTextField txtRegisterPassword2;
    private JButton btnRegisterComplete;
    private JButton btnRegisterCancel;
    private JLabel lblErrorRegister;
    private JLabel lblLoaderRegister;

    /*Menu panel*/
    private JButton btnSinglePlayer;
    private JButton btnDuoPlayer;
    private JButton btnMultiPlayer;
    private JButton btnRanking;

    /*Choice Map panel*/
    private ArrayList<JButton> btnMap;
    private String tipoPartida;

    /*RankingGUI panel*/
    private JToggleButton btnMyRanking;
    private JToggleButton btnGlobalRanking;
    private ArrayList<JToggleButton> btnMapRanking;
    private ButtonGroup btnGroupMapsRanking;
    private RankingGUI ranking;
    private JLabel lblErrorRanking;

    /*Choice Server Multiplayer Panel*/
    private JTextField txtIP;
    private JTextField txtPort;
    private JButton btnConnectServer;
    private JLabel lblErrorServer;
    private JLabel lblLoaderServer;

    /*CONSTRUCTOR*/
    public GUI(Juego j) {

        this.juego = j;
    }

    /*GETTERS Y SRTTERS*/
    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    /*MÉTODOS PÚBLICOS*/
    /**
     * Inicia los componentes de la GUI
     */
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

    /**
     * Ordena al ranking guardar un record
     *
     * @param idMap
     * @param time
     */
    public void saveRecord(int idMap, int time) {
        if (ranking == null) {
            ranking = new RankingGUI(juego.getDbManager(), sesion.getUsername());
        }
        ranking.saveRecord(idMap, time);
    }

    /*MÉTODOS PRIVADOS*/
    /**
     * Añade el componente de login y exit cuando inicia la GUI
     *
     * @param panel
     */
    private void addUIComponents(Container panel) {
        panel.add(createComponentExit(), BorderLayout.NORTH);
        panel.add(createComponentLogin(), BorderLayout.CENTER);
        txtUsername.requestFocus();
    }

    /**
     * Bloquea/Desbloquea los botones de mapa en el ranking
     *
     * @param state
     */
    private void blockBtnMapsRanking(boolean state) {
        for (JToggleButton btnAux : btnMapRanking) {
            btnAux.setEnabled(!state);
        }
    }

    /**
     * Bloquea/Desbloquea la interfaz del panel de servidor
     *
     * @param state
     */
    private void blockChoiceServer(boolean state) {
        lblLoaderServer.setVisible(state);
        lblErrorServer.setVisible(!state);
        txtIP.setEditable(!state);
        txtPort.setEditable(!state);
        btnConnectServer.setEnabled(!state);
    }

    /**
     * Bloquea/Desbloquea los componentes del panel de Login
     *
     * @param state
     */
    private void blockLoginPanel(boolean state) {
        lblLoaderLogin.setVisible(state);
        lblErrorLogin.setVisible(!state);
        txtUsername.setEditable(!state);
        txtPassword.setEditable(!state);
        btnLogin.setEnabled(!state);
        btnRegister.setEnabled(!state);
    }

    /**
     * Bloquea/Desbloquea los componentes del panel ranking
     *
     * @param state
     */
    private void blockRankingPanel(boolean state) {

        btnMyRanking.setEnabled(!state);
        btnGlobalRanking.setEnabled(!state);
        for (JToggleButton btnAuxMap : btnMapRanking) {
            btnAuxMap.setEnabled(!state);
        }
        lblErrorRanking.setText("");

    }

    /**
     * Bloquea/Desbloquea el panel de registro
     *
     * @param state
     */
    private void blockRegisterPanel(boolean state) {
        lblLoaderRegister.setVisible(state);
        lblErrorRegister.setVisible(!state);
        txtRegisterUsername.setEditable(!state);
        txtRegisterPassword.setEditable(!state);
        txtRegisterPassword2.setEditable(!state);
        btnRegisterComplete.setEnabled(!state);
        btnRegisterCancel.setEnabled(!state);
    }

    /**
     * Comprueba las credenciales de un usuario
     *
     * @param username
     * @param password
     * @return
     */
    private boolean checkCredentials(String username, String password) {
        return juego.checkCredentials(username, password);
    }

    /**
     * Pregunta al jugador si quiere salir
     *
     * @return
     */
    private boolean checkExit() {
        int resp = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return resp == 0;
    }

    /**
     * Comprueba si el usuario existe
     *
     * @param username
     * @return
     */
    private boolean checkIfUsernameExists(String username) {
        return juego.checkIfUsernameExists(username);
    }

    /**
     * Comprueba si puede iniciar el servidor
     *
     * @return
     */
    private boolean checkInitServer() {
        if (txtIP.getText().equals("") || txtPort.getText().equals("")) {
            lblErrorServer.setText("ERROR: Debes rellenar todos los campos");
            return false;
        }
        try {
            Integer.parseInt(txtPort.getText());
        } catch (Exception e) {
            lblErrorServer.setText("ERROR: El puerto debe ser un número");
            return false;
        }

        return true;
    }

    /**
     * Crea el panel para elegir servidor multiplayer
     *
     * @return
     */
    private JPanel createComponentChoiceServer() {
        panelChoiceServerMultiplayer = new JPanel();
        int margin = 2 * this.getWidth() / 5;
        panelChoiceServerMultiplayer.setBorder(BorderFactory.createEmptyBorder(0, margin, 0, margin));
        panelChoiceServerMultiplayer.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5, 5, 5, 5);

        JLabel lblServer = new JLabel("Escoge el servidor");
        lblServer.setFont(new Font("Arial Black", 0, 28));
        lblServer.setForeground(Color.BLUE);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.ipady = 35;

        c.fill = GridBagConstraints.CENTER;
        panelChoiceServerMultiplayer.add(lblServer, c);

        JLabel lblIP = new JLabel("IP Servidor");

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.ipady = 10;

        c.fill = GridBagConstraints.HORIZONTAL;
        panelChoiceServerMultiplayer.add(lblIP, c);

        txtIP = new JTextField();
        txtIP.addKeyListener(this);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.BOTH;
        panelChoiceServerMultiplayer.add(txtIP, c);

        JLabel lblPort = new JLabel("Puerto Servidor");

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.HORIZONTAL;
        panelChoiceServerMultiplayer.add(lblPort, c);

        txtPort = new JTextField();
        txtPort.addKeyListener(this);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.BOTH;
        panelChoiceServerMultiplayer.add(txtPort, c);

        btnConnectServer = new JButton("Conectarse");
        btnConnectServer.addActionListener(this);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;

        c.weightx = 1.0d;

        panelChoiceServerMultiplayer.add(btnConnectServer, c);

        lblErrorServer = new JLabel("");
        lblErrorServer.setFont(new Font("Arial", 0, 12));
        lblErrorServer.setForeground(Color.RED);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.CENTER;

        panelChoiceServerMultiplayer.add(lblErrorServer, c);

        c.gridy = 7;

        Icon icon = new ImageIcon("img/gui/loader.gif");
        lblLoaderServer = new JLabel("Buscando servidor...", icon, JLabel.CENTER);
        lblLoaderServer.setVisible(false);

        panelChoiceServerMultiplayer.add(lblLoaderServer, c);

        return panelChoiceServerMultiplayer;
    }

    /**
     * Crea el panel del componente de salida
     *
     * @return
     */
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

    /**
     * Crea el panel del Gamescreen
     *
     * @return
     */
    private JPanel createComponentGamescreen() {
        panelGamescreen = new JPanel();
        panelGamescreen.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //panelGamescreen.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panelGamescreen.add(gamescreen, c);
        panelGamescreen.setBackground(Color.BLACK);

        return panelGamescreen;
    }

    /**
     * Crea el panel de Login
     *
     * @return
     */
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
        txtPassword.addKeyListener(this);


        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.BOTH;
        panelLogin.add(txtPassword, c);

        btnLogin = new JButton("Entrar");
        btnRegister = new JButton("Registrarse");
        btnLogin.addActionListener(this);
        btnRegister.addActionListener(this);

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

        c.gridy = 7;

        Icon icon = new ImageIcon("img/gui/loader.gif");
        lblLoaderLogin = new JLabel(icon);
        lblLoaderLogin.setVisible(false);

        panelLogin.add(lblLoaderLogin, c);

        return panelLogin;
    }

    /**
     * Crea el panel de elección de Mapa
     *
     * @return
     */
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

        for (Mapa m : sesion.getMapas()) {
            m.cargarImagen();
            Image image = m.getImage(); // transform it 
            Image newimg = image.getScaledInstance(480, 250, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            btnMap.add(new JButton(m.getNombre(), new ImageIcon(newimg)));
        }

        for (JButton btnAux : btnMap) {
            btnAux.setHorizontalTextPosition(JButton.CENTER);
            btnAux.setVerticalTextPosition(JButton.TOP);
            panelAuxMaps.add(btnAux);
            btnAux.addActionListener(this);
        }

        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 15;
        //c.weightx = 1.0d;
        c.fill = GridBagConstraints.BOTH;

        panelMapChoice.add(panelAuxMaps, c);

        return panelMapChoice;
    }

    /**
     * Crea el panel de Menu
     *
     * @return
     */
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

        btnDuoPlayer = new JButton("Dos jugadores");
        btnDuoPlayer.addActionListener(this);

        btnMultiPlayer = new JButton("Multijugador online");
        btnMultiPlayer.addActionListener(this);

        btnRanking = new JButton("Ranking");
        btnRanking.addActionListener(this);

        JPanel panelAuxMenu = new JPanel(new GridLayout(4, 1, 5, 15));
        panelAuxMenu.add(btnSinglePlayer);
        panelAuxMenu.add(btnDuoPlayer);
        panelAuxMenu.add(btnMultiPlayer);
        panelAuxMenu.add(btnRanking);

        c.gridx = 0;
        c.gridy = 1;

        c.weightx = 1.0d;
        c.fill = GridBagConstraints.BOTH;

        panelMenu.add(panelAuxMenu, c);

        return panelMenu;
    }

    /**
     * Crea el panel del Ranking
     *
     * @return
     */
    private JPanel createComponentRanking() {
        panelRanking = new JPanel();
        int margin = this.getWidth() / 3;
        panelRanking.setBorder(BorderFactory.createEmptyBorder(0, margin, 0, margin));
        panelRanking.setLayout(new BorderLayout());

        JPanel panelTop = new JPanel();
        panelTop.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5, 5, 5, 5);

        ButtonGroup btnGroupRanking = new ButtonGroup();
        btnMyRanking = new JToggleButton("MI RANKING");
        btnMyRanking.addActionListener(this);
        btnGlobalRanking = new JToggleButton("RANKING MUNDIAL");
        btnGlobalRanking.addActionListener(this);

        btnGroupRanking.add(btnMyRanking);
        btnGroupRanking.add(btnGlobalRanking);

        JPanel panelChoiceRanking = new JPanel();
        panelChoiceRanking.setLayout(new GridLayout(1, 2, 10, 10));

        panelChoiceRanking.add(btnMyRanking);
        panelChoiceRanking.add(btnGlobalRanking);

        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 20;
        c.weightx = 1.0d;
        c.fill = GridBagConstraints.BOTH;

        panelTop.add(panelChoiceRanking, c);

        sesion.loadMaps();
        btnMapRanking = new ArrayList();

        JPanel panelMapsRanking = new JPanel();
        panelMapsRanking.setLayout(new GridLayout(1, sesion.getMapas().size(), 10, 10));
        btnGroupMapsRanking = new ButtonGroup();
        for (Mapa m : sesion.getMapas()) {
            btnMapRanking.add(new JToggleButton(m.getNombre()));

        }
        for (JToggleButton btnAux : btnMapRanking) {
            panelMapsRanking.add(btnAux);
            btnGroupMapsRanking.add(btnAux);
            btnAux.setEnabled(false);
            btnAux.addActionListener(this);
        }

        c.gridy = 1;
        c.ipady = 10;
        c.weightx = 1.0d;

        c.fill = GridBagConstraints.BOTH;

        panelTop.add(panelMapsRanking, c);

        lblErrorRanking = new JLabel("");
        lblErrorRanking.setFont(new Font("Arial", 0, 12));
        lblErrorRanking.setForeground(Color.RED);

        c.gridy = 2;
        c.ipady = 10;

        c.fill = GridBagConstraints.CENTER;

        panelTop.add(lblErrorRanking, c);
        panelRanking.add(panelTop, BorderLayout.NORTH);

        if (ranking == null) {
            ranking = new RankingGUI(juego.getDbManager(), sesion.getUsername());
        }
        panelRanking.add(ranking, BorderLayout.CENTER);
        ranking.setVisible(false);

        return panelRanking;
    }

    /**
     * Crea el panel de registro
     *
     * @return
     */
    private JPanel createComponentRegister() {
        panelRegister = new JPanel();
        int margin = 2 * this.getWidth() / 5;
        panelRegister.setBorder(BorderFactory.createEmptyBorder(0, margin, 0, margin));
        panelRegister.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5, 5, 5, 5);

        JLabel lblWelcome = new JLabel("REGISTRO");
        lblWelcome.setFont(new Font("Arial Black", 0, 28));
        lblWelcome.setForeground(Color.BLUE);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.ipady = 35;

        c.fill = GridBagConstraints.CENTER;
        panelRegister.add(lblWelcome, c);

        JLabel lblUsername = new JLabel("Usuario");

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.ipady = 10;

        c.fill = GridBagConstraints.HORIZONTAL;
        panelRegister.add(lblUsername, c);

        txtRegisterUsername = new JTextField();
        txtRegisterUsername.addKeyListener(this);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.BOTH;
        panelRegister.add(txtRegisterUsername, c);

        JLabel lblPassword = new JLabel("Password");

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.HORIZONTAL;
        panelRegister.add(lblPassword, c);

        txtRegisterPassword = new JPasswordField();
        txtRegisterPassword.addKeyListener(this);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.BOTH;
        panelRegister.add(txtRegisterPassword, c);

        JLabel lblPassword2 = new JLabel("Repetir Password");

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.HORIZONTAL;
        panelRegister.add(lblPassword2, c);

        txtRegisterPassword2 = new JPasswordField();
        txtRegisterPassword2.addKeyListener(this);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.BOTH;
        panelRegister.add(txtRegisterPassword2, c);

        btnRegisterComplete = new JButton("Registrarse");
        btnRegisterCancel = new JButton("Cancelar");
        btnRegisterComplete.addActionListener(this);
        btnRegisterCancel.addActionListener(this);

        JPanel panelAuxBtn = new JPanel(new GridLayout(1, 2, 5, 5));
        panelAuxBtn.add(btnRegisterComplete);
        panelAuxBtn.add(btnRegisterCancel);

        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;

        c.weightx = 1.0d;

        panelRegister.add(panelAuxBtn, c);

        lblErrorRegister = new JLabel("");
        lblErrorRegister.setFont(new Font("Arial", 0, 12));
        lblErrorRegister.setForeground(Color.RED);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.CENTER;

        panelRegister.add(lblErrorRegister, c);

        c.gridy = 9;

        Icon icon = new ImageIcon("img/gui/loader.gif");
        lblLoaderRegister = new JLabel(icon);
        lblLoaderRegister.setVisible(false);

        panelRegister.add(lblLoaderRegister, c);

        return panelRegister;
    }

    /**
     * Obtiene el ranking global de un mapa
     *
     * @param idMap
     */
    private void getGlobalRankingInMap(int idMap) {
        ranking.setVisible(false);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if (juego.checkConnectionDB()) {
                    ranking.getGlobalRankingInMap(idMap);
                    ranking.setVisible(true);
                } else {
                    lblErrorRanking.setText("ERROR: Error de conexión. Intentalo más tarde.");
                }
                blockRankingPanel(false);
            }
        }
        );
        t.start();
    }

    /**
     * Obtiene el ranking personal de un mapa
     *
     * @param idMap
     */
    private void getPersonalRankingInMap(int idMap) {
        ranking.setVisible(false);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if (juego.checkConnectionDB()) {
                    ranking.getPersonalRankingInMap(idMap);
                    ranking.setVisible(true);                
                } else {
                    lblErrorRanking.setText("ERROR: Error de conexión. Intentalo más tarde.");
                }
                
                blockRankingPanel(false);
            }
        }
        );
        t.start();
    }

    /**
     * En el menú principal inicia el componente para elegir servidor
     * multiplayer
     */
    private void initChoiceServerMultiplayer() {
        panelMenu.setVisible(false);
        btnBack.setVisible(true);
        if (panelChoiceServerMultiplayer == null) {
            this.getContentPane().add(createComponentChoiceServer(), BorderLayout.CENTER);
        } else {
            panelChoiceServerMultiplayer.setVisible(true);
        }
    }

    /**
     * Cuando se elige Single o Duo inicia el componente de elección de Mapa
     */
    private void initMapChoice() {
        panelMenu.setVisible(false);
        btnBack.setVisible(true);
        if (panelMapChoice == null) {
            this.getContentPane().add(createComponentMapChoice(), BorderLayout.CENTER);
        } else {
            panelMapChoice.setVisible(true);
        }

    }

    /**
     * Inicia el gamescreen en duo o single
     *
     * @param numMap
     */
    private void initPartida(int numMap) {
        panelMapChoice.setVisible(false);
        if (panelGamescreen == null) {
            gamescreen = new Gamescreen(this, sesion.initPartida(numMap, tipoPartida));
            this.getContentPane().add(createComponentGamescreen(), BorderLayout.CENTER);
            gamescreen.setSize(getWidth(), getHeight() - btnExit.getHeight() - 2 * btnExit.getY());
//            gamescreen.setSize(1280, 700);
        } else {
            panelGamescreen.setVisible(true);
            gamescreen.setPartida(sesion.initPartida(numMap, tipoPartida));
        }

        new Thread(this.gamescreen).start();
        this.gamescreen.setBackground(new Color(208, 244, 247));
        this.gamescreen.requestFocus();
    }

    /**
     * Inicia el gamescreen en partida online
     */
    private void initPartidaOnline() {
        blockChoiceServer(true);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                lblErrorServer.setText("");
                String host = txtIP.getText();
                int port = Integer.parseInt(txtPort.getText());
                Partida partida = sesion.initPartidaOnline(host, port);
                if (partida == null) {
                    lblErrorServer.setText("ERROR: Servidor no encontrado.");
                } else {
                    if (panelGamescreen == null) {
                        gamescreen = new Gamescreen(sesion.getGui(), partida);
                        getContentPane().add(createComponentGamescreen(), BorderLayout.CENTER);
                        gamescreen.setSize(getWidth(), getHeight() - btnExit.getHeight() - 2 * btnExit.getY());
//            gamescreen.setSize(1280, 700);
                    } else {
                        panelGamescreen.setVisible(true);
                        gamescreen.setPartida(partida);
                    }
                    panelChoiceServerMultiplayer.setVisible(false);
                    new Thread(gamescreen).start();
                    gamescreen.setBackground(new Color(208, 244, 247));
                    gamescreen.requestFocus();
                }
                blockChoiceServer(false);
            }
        });
        t.start();
    }

    /**
     * Inicia el ranking desde el menú
     */
    private void initRanking() {
        panelMenu.setVisible(false);
        btnBack.setVisible(true);
        if (panelRanking == null) {
            this.getContentPane().add(createComponentRanking(), BorderLayout.CENTER);
        } else {
            panelRanking.setVisible(true);
        }
    }

    /**
     * Inicia el panel de registro
     */
    private void initRegister() {
        panelLogin.setVisible(false);
        if (panelRegister == null) {
            this.getContentPane().add(createComponentRegister(), BorderLayout.CENTER);
        } else {
            panelRegister.setVisible(true);
        }
        txtRegisterUsername.requestFocus();
    }

    /**
     * Inicia sesión cuando un jugador hace login
     */
    private void initSesion() {
        blockLoginPanel(true);
        lblErrorLogin.setForeground(Color.RED);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if (txtUsername.getText().equals("") || txtPassword.getText().equals("")) {
                    lblErrorLogin.setText("ERROR: Rellena todos los campos");
                } else if (juego.checkConnectionDB()) {
                    if (!checkIfUsernameExists(txtUsername.getText())) {
                        lblErrorLogin.setText("ERROR: No existe ningún usuario con ese nombre.");
                    } else if (checkCredentials(txtUsername.getText(), txtPassword.getText())) {
                        panelLogin.setVisible(false);
                        juego.initSesion(txtUsername.getText());
                        getContentPane().add(createComponentMenu(), BorderLayout.CENTER);
                    } else {
                        lblErrorLogin.setText("ERROR: Contraseña incorrecta.");
                    }

                } else {
                    lblErrorLogin.setText("ERROR: Error de conexión. Intentalo más tarde.");
                }
                blockLoginPanel(false);
            }
        }
        );
        t.start();
    }

    /**
     * Registra a un usuario
     */
    private void registerUser() {
        blockRegisterPanel(true);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if (txtRegisterUsername.getText().equals("") || txtRegisterPassword.getText().equals("") || txtRegisterPassword2.getText().equals("")) {
                    lblErrorRegister.setText("ERROR: Debes rellenar todos los campos");
                } else if (!txtRegisterPassword.getText().equals(txtRegisterPassword2.getText())) {
                    lblErrorRegister.setText("ERROR: Las contraseñas no coinciden");
                } else if (juego.checkConnectionDB()) {
                    if (checkIfUsernameExists(txtRegisterUsername.getText())) {
                        lblErrorRegister.setText("ERROR: El nombre de usuario ya está en uso");
                    } else {
                        juego.registerUser(txtRegisterUsername.getText(), txtRegisterPassword.getText());
                        panelRegister.setVisible(false);
                        panelLogin.setVisible(true);
                        lblErrorLogin.setText("Usuario registrado correctamente.");
                        lblErrorLogin.setForeground(Color.GREEN);
                    }
                } else {
                    lblErrorRegister.setText("ERROR: Error de conexión. Intentalo más tarde.");
                }
                blockRegisterPanel(false);
            }
        });
        t.start();
    }

    /*LISTENER*/
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().getClass() == btnExit.getClass()) {
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
                } else if (btnAux == btnRegister) {
                    initRegister();
                }

            } else if (panelRegister != null && panelRegister.isVisible()) {
                if (btnAux == btnRegisterComplete) {
                    registerUser();
                } else if (btnAux == btnRegisterCancel) {
                    panelRegister.setVisible(false);
                    panelLogin.setVisible(true);
                }
                //Acciones panel menu
            } else if (panelMenu.isVisible()) {
                if (btnAux == btnSinglePlayer) {
                    initMapChoice();
                    tipoPartida = "Single";
                } else if (btnAux == btnDuoPlayer) {
                    initMapChoice();
                    tipoPartida = "Duo";
                } else if (btnAux == btnMultiPlayer) {
                    tipoPartida = "Online";
                    initChoiceServerMultiplayer();
                } else if (btnAux == btnRanking) {
                    initRanking();
                }
            } else if (panelMapChoice != null && panelMapChoice.isVisible()) {
                if (btnAux == btnBack) {
                    panelMapChoice.setVisible(false);
                    panelMenu.setVisible(true);
                    btnBack.setVisible(false);
                }
                for (JButton btnAuxMap : btnMap) {
                    if (btnAuxMap == btnAux) {

                        initPartida(btnMap.indexOf(btnAuxMap));
                    }
                }
            } else if (panelGamescreen != null && panelGamescreen.isVisible()) {
                if (btnAux == btnBack) {
                    sesion.endPartida();
                    panelGamescreen.setVisible(false);
                    if (tipoPartida.equals("Online")) {
                        panelChoiceServerMultiplayer.setVisible(true);
                    } else {
                        panelMapChoice.setVisible(true);
                    }

                }
            } else if (panelChoiceServerMultiplayer != null && panelChoiceServerMultiplayer.isVisible()) {
                if (btnAux == btnBack) {
                    blockChoiceServer(false);
                    panelMenu.setVisible(true);
                    btnBack.setVisible(false);
                    panelChoiceServerMultiplayer.setVisible(false);
                }
                if (btnAux == btnConnectServer) {
                    if (checkInitServer()) {
                        initPartidaOnline();
                    }
                }
            } else if (panelRanking.isVisible()) {
                if (btnAux == btnBack) {
                    panelMenu.setVisible(true);
                    panelRanking.setVisible(false);
                    ranking.setVisible(false);
                    btnMapRanking.clear();
                    blockBtnMapsRanking(true);
                    lblErrorRanking.setText("");

                }
            }
        } else if (e.getSource().getClass() == btnMyRanking.getClass()) {
            JToggleButton btnAux = (JToggleButton) e.getSource();

            if (panelRanking.isVisible()) {
                lblErrorRanking.setText("");
                if (!btnMapRanking.get(0).isEnabled()) {
                    blockBtnMapsRanking(false);
                }
                if (btnAux == btnMyRanking || btnAux == btnGlobalRanking) {
                    btnGroupMapsRanking.clearSelection();
                    ranking.setVisible(false);
                } else {
                    blockRankingPanel(true);
                    for (JToggleButton btnAuxMap : btnMapRanking) {
                        if (btnAuxMap == btnAux) {

                            if (btnMyRanking.isSelected()) {
                                this.getPersonalRankingInMap(btnMapRanking.indexOf(btnAuxMap));
                            } else {
                                this.getGlobalRankingInMap(btnMapRanking.indexOf(btnAuxMap));

                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        if (panelLogin.isVisible()) {
            if (txtUsername.hasFocus() && txtUsername.getText().length() >= 16
                    || txtPassword.hasFocus() && txtPassword.getText().length() >= 14) {
                ke.consume();
            }
        } else if (panelRegister != null && panelRegister.isVisible()) {
            if (txtRegisterUsername.hasFocus() && txtRegisterUsername.getText().length() >= 16
                    || txtRegisterPassword.hasFocus() && txtRegisterPassword.getText().length() >= 14
                    || txtRegisterPassword2.hasFocus() && txtRegisterPassword2.getText().length() >= 14) {
                ke.consume();
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getSource() == this.txtUsername || ke.getSource() == this.txtPassword) {
            if (ke.VK_ENTER == ke.getKeyCode()) {
                this.initSesion();
            }
        }
        if (ke.getSource() == this.txtRegisterUsername || ke.getSource() == this.txtRegisterPassword || ke.getSource() == this.txtRegisterPassword2) {
            if (ke.VK_ENTER == ke.getKeyCode()) {
                this.registerUser();
            }
        }
        if (ke.getSource() == txtIP || ke.getSource() == txtPort) {
            if (ke.VK_ENTER == ke.getKeyCode()) {
                if (checkInitServer()) {
                    initPartidaOnline();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //System.out.println("keyPressed="+KeyEvent.getKeyText(ke.getKeyCode()));
    }

}

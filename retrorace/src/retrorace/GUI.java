/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author sosan
 */
public class GUI extends JFrame implements ActionListener {
    
    private Juego juego;
    
    private Gamescreen gamescreen;
    
    private JPanel panelLogin;
    private JPanel panelMenu;
    private JPanel panelMap;
    
    private JButton btnExit;

    /*Login panel*/
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JLabel lblErrorLogin;

    //ranking
    public GUI(Juego j) {
        this.gamescreen = new Gamescreen();
        this.juego = j;
    }
    
    public void initGUI() {
        this.setResizable(false);
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
    
    private void addUIComponents(Container panel) {
        panel.add(createComponentExit(), BorderLayout.NORTH);
        panel.add(createComponentLogin(), BorderLayout.CENTER);
    }
    
    private JPanel createComponentExit() {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        btnExit = new JButton("X");
        btnExit.addActionListener(this);
        p.add(btnExit);
        
        return p;
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
    
    private void initSesion() {
        //CAMBIAR 
        String username = txtUsername.getText();
        if (checkUser(username)) {
            panelLogin.setVisible(false);
            juego.initSesion(username);
        } else {
            lblErrorLogin.setText("ERROR: Introduce un nombre de usuario");
        }
        
    }
    
    private boolean checkExit() {
        int resp = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        return resp==0;
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
        } else if (btnAux == btnLogin) {
            initSesion();
        }
    }

    
    
}

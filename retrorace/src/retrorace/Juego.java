/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

/**
 *
 * @author sosan
 */
public class Juego {
    
    private Sesion sesion;
    private GUI gui;
    
    public void initJuego(){
        gui = new GUI(this);
        this.gui.initGUI();
    }

    void initSesion() {
        
    }

    void initSesion(String username) {
        this.sesion=new Sesion(this.gui,username);
    }
}

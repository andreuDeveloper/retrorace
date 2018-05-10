/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sosan
 */
public class Sesion {
    
    private ArrayList<Mapa> mapas;
    private Partida partida;
    private GUI gui;
    private String username;
    
    //ranking
    
    public Sesion(GUI gui,String user){
        this.gui = gui;
        this.username=user;
        this.mapas=new ArrayList();
        this.gui.setSesion(this);
    }
    
    /*GETTERS Y SETERS*/
    public String getUsername() {
        return username;
    }

    public ArrayList<Mapa> getMapas() {
        return mapas;
    }

    
    public void loadMaps(){
        try {
            Gson gson = new Gson();
            mapas = gson.fromJson(new FileReader("data/mapas.json"), new TypeToken<List<Mapa>>() {
            }.getType());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sesion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Partida initPartida(int numMap){
        partida = new Partida(mapas.get(numMap));
        partida.addPersonaje();
        partida.setTipoPartida("Duo");
        new Thread(partida).start();
        return partida;
    }
    
    public void endPartida(){
        this.partida.setActiva(false);
    }
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.util.ArrayList;

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

    
    private void loadMaps(){
        /*ArrayList<Mapa> mapas;
        Gson gson = new Gson();
        mapas = gson.fromJson(new FileReader("data/mapas.json"), new TypeToken<List<Mapa>>() {
        }.getType());*/
    }
    
    
    
    
}

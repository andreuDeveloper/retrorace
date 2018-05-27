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
/*ATRIBUTOS*/
    private ArrayList<Mapa> mapas;
    private Partida partida;
    private final GUI gui;
    private final String username;
    
/*CONSTRUCTOR*/
    public Sesion(GUI gui, String user) {
        this.gui = gui;
        this.username = user;
        this.mapas = new ArrayList();
        this.gui.setSesion(this);
    }

    /*GETTERS Y SETERS*/
    public GUI getGui() {
        return gui;
    }
    
        public ArrayList<Mapa> getMapas() {
        return mapas;
    }
    
    public String getUsername() {
        return username;
    }

/*METODOS PÚBLICOS*/
   /**
    * Finaliza la partida actual
    */
    public void endPartida() {
        this.partida.setActiva(false);
        this.partida.getMapa().pararAnimacion();
        this.partida.getMapa().recargarDistribucion();

    }
    
    /**
     * Inicia una partida duo o single
     * @param numMap
     * @param tipoPartida
     * @return 
     */
    public Partida initPartida(int numMap, String tipoPartida) {
        partida = new Partida(mapas.get(numMap));
        this.partida.setActiva(true);
        if (tipoPartida.equals("Duo")) {
            partida.addPersonaje();
        }
        partida.setTipoPartida(tipoPartida);
        partida.setSesion(this);
        new Thread(partida).start();
        return partida;
    }

    /**
     * Inicia una partida online
     * @param host
     * @param port
     * @return 
     */
    public Partida initPartidaOnline(String host, int port) {
        ClientProject cp = new ClientProject();
        if (mapas.isEmpty()) {
            loadMaps();
        }
        partida = new Partida(mapas.get(0));
        this.partida.setActiva(true);
        partida.setTipoPartida("Online");
        partida.setSesion(this);

        cp.setPartida(partida);
        cp.createConnection(host, port);       
        
        int cont=0;
        while(!cp.hayServer() && cont<5){      
            try {
                System.out.println("NADA");
                Thread.sleep(1000);
                cont++;
            } catch (InterruptedException ex) {               
            }
        }
        if(cp.hayServer()){
            new Thread(partida).start();
        return partida;
        }
        cp.closeConnection();
        return null;     
    }

    /**
     * Carga los mapas desde un json
     */
    public void loadMaps() {
        try {
            Gson gson = new Gson();
            mapas = gson.fromJson(new FileReader("data/mapas.json"), new TypeToken<List<Mapa>>() {
            }.getType());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sesion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Guarda los records en BD.
     * @param mapa
     * @param contador 
     */
    public void saveRecord(Mapa mapa, int contador) {
        gui.saveRecord(mapas.indexOf(mapa), contador);
    }

}

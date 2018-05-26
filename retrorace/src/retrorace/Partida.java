/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.applet.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

/**
 *
 * @author sosan
 */
public class Partida implements Runnable {

    private ArrayList<Personaje> personajes; //Por defecto 0 es el nuestro
    private boolean activa;
    private Sesion sesion;
    private String tipoPartida = "";
    private final double gravedad = 1;
    private Mapa mapa;
    private Server server = null;
    private Timer tiempo;
    private final String colores[] = {"Blue", "Green", "White", "White"};
    //Music
    private Clip audioClip;

    public Partida(Mapa mapa) {
        this.personajes = new ArrayList<Personaje>();
        this.activa = false;
        this.personajes.add(new Personaje(this));
        this.mapa = mapa;
        this.tiempo = new Timer(1000); //1 sec
    }

    private void iniciarPartida() {
        //Init music
        try {
            File audioFile = new File("music/track1.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            audioClip = (Clip) AudioSystem.getLine(info);
 
            audioClip.open(audioStream);
             
            //audioClip.start();

        } catch (Exception e) {
            e.printStackTrace();

        }

        this.activa = true;
        for (int i = 0; i < this.personajes.size(); i++) {
            this.personajes.get(i).setColor(colores[i]);
            new Thread(this.personajes.get(i)).start();
        }
        new Thread(this.tiempo).start();
    }

    @Override
    public void run() {

        iniciarPartida();
        
        while (activa) {
            try {
                if (tipoPartida.equals("Online")) {
                    //El player 0 es el local, enviamos su info
                    if (server != null) {
                        this.server.sendMessage(this.personajes.get(0).getInfo());
                    }
                }
                audioClip.loop(Clip.LOOP_CONTINUOUSLY);
                Thread.sleep(100);
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }        
        if (this.getTipoPartida().equals("Single") && (this.getPersonaje(0).isEnMeta())) {
            this.sesion.saveRecord(this.getMapa(), this.tiempo.getContador());
        }
        this.tiempo.end();
        this.audioClip.stop();
        this.audioClip = null;

    }

    public Sesion getSesion() {
        return sesion;
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }
    
    public void setServer(Server server) {
        this.server = server;
    }

    public int getTiempoPartida() {
        return this.tiempo.getContador();
    }

    public Mapa getMapa() {
        return mapa;
    }

    public String getTipoPartida() {
        return this.tipoPartida;
    }

    public void setTipoPartida(String tipoPartida) {
        this.tipoPartida = tipoPartida;
    }

    public void addPersonaje() {
        if (this.personajes.size() < 4) {
            this.personajes.add(new Personaje(this));
        }
    }

    public void addPersonaje(Personaje p) {
        this.personajes.add(p);
    }

    public Personaje getPersonaje(int i) {
        return this.personajes.get(i);
    }

    public int totalPersonajes() {
        return this.personajes.size();
    }

    public boolean isActiva() {
        return this.activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public double getGravedad() {
        return gravedad;
    }

    public void comprobarDerecha(Personaje personaje) {
        int anchoCasillas = 40;
        int xDerecha = ((int) personaje.getX() + (int) personaje.getAncho() + 1) / anchoCasillas;
        int ySuelo = ((int) personaje.getY() + (int) personaje.getAlto() + 10) / anchoCasillas;
        String propiedad = this.mapa.getCasilla(xDerecha, ySuelo).getPropiedad();
        switch (propiedad) {
            case "checkpointOff":
                this.mapa.activarAntorcha(xDerecha, ySuelo);
                personaje.setLastCheckPoint((xDerecha) * anchoCasillas, (int) personaje.getY());
                break;
            case "checkpointOn":
                if (this.tipoPartida.equals("Duo")) {
                    personaje.setLastCheckPoint((xDerecha) * anchoCasillas, (int) personaje.getY());
                }
                break;
            default:
        }

    }

    public void comprobarIzquierda(Personaje personaje) {
        int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
        int xIzquierda = (int) personaje.getX() / anchoCasillas;
        int ySuelo = ((int) personaje.getY() + (int) personaje.getAlto() + 10) / anchoCasillas;
        String propiedad = this.mapa.getCasilla(xIzquierda, ySuelo).getPropiedad();

        switch (propiedad) {
            case "checkpointOff":
                this.mapa.activarAntorcha(xIzquierda, ySuelo);
                personaje.setLastCheckPoint((xIzquierda) * anchoCasillas, (int) personaje.getY());
                break;
            case "checkpointOn":
                if (this.tipoPartida.equals("Duo")) {
                    personaje.setLastCheckPoint((xIzquierda) * anchoCasillas, (int) personaje.getY());
                }
                break;
            default:
        }
    }

    public boolean puedeMoverIzquierda(Personaje personaje) {
        int x = (int) personaje.getX();
        int y = (int) personaje.getY();
        if (x <= 5) {
            return false;
        }

        int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
        int xIzquierda = x / anchoCasillas;
        int yPies = (y + (int) personaje.getAlto() - 1) / anchoCasillas;
        String propiedadPiesIzq = this.mapa.getCasilla(xIzquierda, yPies).getPropiedad();

        switch (propiedadPiesIzq) {
            case "intransitable":
                return false;
            case "sostenedor":
                return false;
            case "trampolin":
                return false;
            default:
                return true;

        }
    }

    public boolean puedeMoverDerecha(Personaje personaje) {
        int x = (int) personaje.getX();
        int y = (int) personaje.getY();
        if (x >= 1880) {
            return false;
        }
        try {
            int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
            int xDerecha = (x + (int) personaje.getAncho() + 1) / anchoCasillas;
            int yPies = (y + (int) personaje.getAlto() - 1) / anchoCasillas;

            String propiedadPiesDer = this.mapa.getCasilla(xDerecha, yPies).getPropiedad();

            switch (propiedadPiesDer) {
                case "intransitable":
                    return false;
                case "sostenedor":
                    return false;
                case "trampolin":
                    return false;
                default:
                    return true;

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public void commprobarSuelo(Personaje personaje) {
        int anchoCasillas = 40;
        int xCentral = ((int) personaje.getX() + (int) personaje.getAncho() / 2) / anchoCasillas;
        int ySuelo = ((int) personaje.getY() + (int) personaje.getAlto() + 10) / anchoCasillas;

        String propiedad = this.mapa.getCasilla(xCentral, ySuelo).getPropiedad();
        switch (propiedad) {
            case "checkpointOff":
                personaje.setLastCheckPoint((xCentral) * anchoCasillas, (int) personaje.getY());
            case "transitable":
            case "agua":
                personaje.setFalling(true);
                personaje.setEstaSobreSuelo(false);
                break;
            case "sostenedor":
                personaje.setEstaSobreSuelo(true);
                personaje.setY((ySuelo) * anchoCasillas - (int) personaje.getAlto());
                break;
            case "eliminatorio":
                personaje.matar();
                break;
            case "checkpointOn":
                if (this.tipoPartida.equals("Duo")) {
                    personaje.setLastCheckPoint((xCentral) * anchoCasillas, (int) personaje.getY());
                }
                break;
            case "trampolin":
                personaje.setEstaSobreSuelo(true);
                personaje.setJumping(false);
                personaje.setY((ySuelo) * anchoCasillas - (int) personaje.getAlto());
                personaje.saltar(15);
                break;
            default:
        }
    }

    public void comprobarPies(Personaje personaje) {
        int anchoCasillas = 40;
        int xCentral = ((int) personaje.getX() + (int) personaje.getAncho() / 2) / anchoCasillas;
        int yPies = ((int) personaje.getY() + (int) personaje.getAlto() - 1) / anchoCasillas;
        String propiedad = this.mapa.getCasilla(xCentral, yPies).getPropiedad();
        switch (propiedad) {
            case "checkpointOff":
                this.mapa.activarAntorcha(xCentral, yPies);
                personaje.setLastCheckPoint((xCentral) * anchoCasillas, (int) personaje.getY());
                break;
            case "checkpointOn":
                if (this.tipoPartida.equals("Duo")) {
                    personaje.setLastCheckPoint((xCentral) * anchoCasillas, (int) personaje.getY());
                }
                break;
            case "finalizableOff":
                personaje.setEnMeta(true);
                this.mapa.activarMeta(xCentral, yPies);
                this.tiempo.pausar();
                break;
            case "finalizableOn":
                if (this.tipoPartida.equals("Duo")) {
                    personaje.setEnMeta(true);
                }
                break;
            case "eliminatorio":
                personaje.matar();
                break;
        }

        if (propiedad.equals("agua")) {
            personaje.setBuffMovimiento(-3);
        } else {
            personaje.setBuffMovimiento(0);
        }
    }

}

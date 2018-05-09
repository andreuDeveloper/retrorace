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

/**
 *
 * @author sosan
 */
public class Partida implements Runnable {

    private ArrayList<Personaje> personajes; //Por defecto 0 es el nuestro
    private boolean activa;
    private String tipoPartida = "";
    private final double gravedad = 1;
    private Mapa mapa;
    private Timer tiempo;
    private final String colores [] =  {"White", "Blue" , "White", "White"};

    public Partida(Mapa mapa) {
        this.personajes = new ArrayList<Personaje>();
        this.activa = false;
        this.personajes.add(new Personaje(this));
        this.mapa = mapa;
        this.tiempo = new Timer(1000); //1 sec
    }

    private void iniciarPartida() {
        this.activa = true;
        for (int i = 0; i < this.personajes.size(); i++) {
            //this.personajes.get(i).setColor(colores[i]);
            new Thread(this.personajes.get(i)).start();
            //this.personajes.get(i).start();
        }
        new Thread(this.tiempo).start();
    }

    @Override
    public void run() {
        iniciarPartida();
        while (activa) {
            //Check position casilla
            for (Personaje personaje : personajes) {
                //if (!personaje.esTipoOnline) {
                if (!personaje.isMuerto()) {
                    checkPosition(personaje);
                }
                
                //}
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(Partida.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.tiempo.end();
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

    private void checkPosition(Personaje personaje) {
        try {
            //Recoger la casilla donde nos encontramos, y hacer x cosas segun la casilla
            Casilla aux = this.mapa.getCasilla(0, 0);

            if (aux != null) {
                int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
                int xIzquierda = (int) personaje.getX() / anchoCasillas;
                int xDerecha = ((int) personaje.getX() + (int) personaje.getAncho() + 1) / anchoCasillas;
                int xCentral = ((int) personaje.getX() + (int) personaje.getAncho() / 2) / anchoCasillas;
                int yCabeza = (int) personaje.getY() / anchoCasillas;
                int ySuelo = ((int) personaje.getY() + (int) personaje.getAlto() + 10) / anchoCasillas;
                int yPies = ((int) personaje.getY() + (int) personaje.getAlto() - 1) / anchoCasillas;

                checkDerecha(xDerecha + 1, ySuelo, personaje);
                checkIzquierda(xIzquierda + 1, ySuelo, personaje);
                checkSuelo(xCentral, ySuelo, personaje);
                checkPies(xCentral, yPies, personaje);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkDerecha(int x, int y, Personaje personaje) {
        String propiedad = this.mapa.getCasilla(x, y).getPropiedad();
        switch (propiedad) {
            case "checkpointOff":
                this.mapa.activarAntorcha(x, y);
                int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
                personaje.setLastCheckPoint((x) * anchoCasillas, (int) personaje.getY());
                break;
            default:
        }
    }

    private void checkIzquierda(int x, int y, Personaje personaje) {
        String propiedad = this.mapa.getCasilla(x, y).getPropiedad();
        int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
        switch (propiedad) {
            case "checkpointOff":
                this.mapa.activarAntorcha(x, y);
                personaje.setLastCheckPoint((x) * anchoCasillas, (int) personaje.getY());
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
        int xCentral = (x + (int) personaje.getAncho() / 2) / anchoCasillas;
        int yPies = (y + (int) personaje.getAlto() - 1) / anchoCasillas;
        int ySuelo = (y + (int) personaje.getAlto() + 10) / anchoCasillas;
        int yCentral = (y + (int) personaje.getAlto() / 2) / anchoCasillas;
        String propiedadPiesIzq = this.mapa.getCasilla(xIzquierda, yPies).getPropiedad();
        String propiedadPechoIzq = this.mapa.getCasilla(xIzquierda, yCentral).getPropiedad();

        return (!(propiedadPiesIzq.equals("intransitable") || propiedadPiesIzq.equals("sostenedor")));
    }

    public boolean puedeMoverDerecha(Personaje personaje) {
        int x = (int) personaje.getX();
        int y = (int) personaje.getY();
        if (x >= 1875) {
            return false;
        }
        try {
            int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
            int xDerecha = (x + (int) personaje.getAncho() + 1) / anchoCasillas;
            int xCentral = (x + (int) personaje.getAncho() / 2) / anchoCasillas;
            int yPies = (y + (int) personaje.getAlto() - 1) / anchoCasillas;
            int yCentral = (y + (int) personaje.getAlto() / 2) / anchoCasillas;
            int ySuelo = (y + (int) personaje.getAlto() + 10) / anchoCasillas;

            String propiedadPiesDer = this.mapa.getCasilla(xDerecha, yPies).getPropiedad();
            String propiedadPechoDer = this.mapa.getCasilla(xDerecha, yCentral).getPropiedad();

            return (!(propiedadPiesDer.equals("intransitable") || propiedadPiesDer.equals("sostenedor")));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    private void checkSuelo(int x, int y, Personaje personaje) {
        String propiedad = this.mapa.getCasilla(x, y).getPropiedad();
        int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
        switch (propiedad) {
            case "checkpointOff":
                personaje.setLastCheckPoint((x) * anchoCasillas, (int) personaje.getY());
            case "transitable":
                personaje.setFalling(true);
                personaje.setEstaSobreSuelo(false);
                break;
            case "sostenedor":
                personaje.setEstaSobreSuelo(true);
                personaje.setY((y) * anchoCasillas - (int) personaje.getAlto());
                break;
            case "eliminatorio":
                personaje.matar();
                break;
            default:
        }
    }

    private void checkPies(int x, int y, Personaje personaje) {
        String propiedad = this.mapa.getCasilla(x, y).getPropiedad();
        int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
        switch (propiedad) {
            case "checkpointOff":
                this.mapa.activarAntorcha(x, y);
                personaje.setLastCheckPoint((x) * anchoCasillas, (int) personaje.getY());
                break;
            case "finalizable":
                personaje.setEnMeta(true);
                this.tiempo.pausar();
                break;
            case "trampolin":
                personaje.saltar(18);
                break;
        }

        if (propiedad.equals("agua")) {
            personaje.setBuffMovimiento(-1);
        } else {
            personaje.setBuffMovimiento(0);
        }
    }

}

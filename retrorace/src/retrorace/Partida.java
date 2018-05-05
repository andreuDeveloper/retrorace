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
    private final double gravedad = 1;
    private Mapa mapa;
    private Point lastCheckPoint = null;

    public Partida(Mapa mapa) {
        this.personajes = new ArrayList<Personaje>();
        this.activa = false;
        this.personajes.add(new Personaje(this));
        this.mapa = mapa;
    }

    private void iniciarPartida() {
        this.activa = true;
        for (int i = 0; i < this.personajes.size(); i++) {
            new Thread(this.personajes.get(i)).start();
        }
    }

    @Override
    public void run() {
        iniciarPartida();
        while (activa) {
            //Check position %xcasilla
            checkPosition(this.personajes.get(0).getCoordenadas());

            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(Partida.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Mapa getMapa() {
        return mapa;
    }

    public Point getLastCheckPoint() {
        return this.lastCheckPoint;
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

    private void checkPosition(Point pos) {
        try {
            //Recoger la casilla donde nos encontramos, y hacer x cosas segun la casilla
            Casilla aux = this.mapa.getCasilla(0, 0);
            if (aux != null) {
                int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
                int xIzquierda = pos.x / anchoCasillas;
                int xDerecha = (pos.x + (int) this.personajes.get(0).getAncho() + 1) / anchoCasillas;
                int xCentral = (pos.x + (int) this.personajes.get(0).getAncho() / 2) / anchoCasillas;
                int yCabeza = pos.y / anchoCasillas;
                int ySuelo = (pos.y + (int) this.personajes.get(0).getAlto() + 10) / anchoCasillas;
                int yPies = (pos.y + (int) this.personajes.get(0).getAlto() - 1) / anchoCasillas;

                //System.out.println("Y: " + ySuelo + " .. X: " + xIzquierda + " -- " + this.mapa.getCasilla(xIzquierda, ySuelo).getPropiedad());
                checkDerecha(xDerecha + 1, ySuelo);
                checkIzquierda(xIzquierda + 1, ySuelo);
                checkSuelo(xCentral, ySuelo);
                checkPies(xCentral, yPies);

                /*
            if (this.personajes.get(0).getY() + this.personajes.get(0).getAlto() > ) {
                this.personajes.get(0).reset(lastCheckPoint);
            }
                 */
            }
        } catch (Exception e) {

        }
    }

    private void checkDerecha(int x, int y) {
        String propiedad = this.mapa.getCasilla(x, y).getPropiedad();
        switch (propiedad) {
            case "checkpointOff":
                this.mapa.activarAntorcha(x, y);
                int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
                this.lastCheckPoint = new Point();
                this.lastCheckPoint.x = (x - 1) * anchoCasillas + (anchoCasillas / 2);
                this.lastCheckPoint.y = (int) this.personajes.get(0).getY();
                break;
            default:
        }
    }

    private void checkIzquierda(int x, int y) {
        String propiedad = this.mapa.getCasilla(x, y).getPropiedad();
        int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
        switch (propiedad) {
            case "checkpointOff":
                this.mapa.activarAntorcha(x, y);
                this.lastCheckPoint = new Point();
                this.lastCheckPoint.x = (x - 1) * anchoCasillas + (anchoCasillas / 2);
                this.lastCheckPoint.y = (int) this.personajes.get(0).getY();
                break;
            default:
        }
    }

    public boolean puedeMoverIzquierda(int x, int y) {

        if (x <= 5) {
            return false;
        }

        int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
        int xIzquierda = x / anchoCasillas;
        int yPies = (y + (int) this.personajes.get(0).getAlto() - 1) / anchoCasillas;
        int ySuelo = (y + (int) this.personajes.get(0).getAlto() + 10) / anchoCasillas;
        String propiedadPies = this.mapa.getCasilla(xIzquierda, yPies).getPropiedad();
        String propiedadSuelo = this.mapa.getCasilla(xIzquierda, ySuelo).getPropiedad();

        return !(("intransitable".equals(propiedadPies) || "sostenedor".equals(propiedadPies))
                && !"transitable".equals(propiedadSuelo));
    }

    public boolean puedeMoverDerecha(int x, int y) {

        //if (x >= 1915) return false;
        try {
            int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
            int xDerecha = (x + (int) this.personajes.get(0).getAncho() + 1) / anchoCasillas;
            int yPies = (y + (int) this.personajes.get(0).getAlto() - 1) / anchoCasillas;
            int ySuelo = (y + (int) this.personajes.get(0).getAlto() + 10) / anchoCasillas;
            String propiedadPies = this.mapa.getCasilla(xDerecha, yPies).getPropiedad();
            String propiedadSuelo = this.mapa.getCasilla(xDerecha, ySuelo).getPropiedad();

            return !(("intransitable".equals(propiedadPies) || "sostenedor".equals(propiedadPies))
                    && !"transitable".equals(propiedadSuelo));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    private void checkSuelo(int x, int y) {
        String propiedad = this.mapa.getCasilla(x, y).getPropiedad();
        int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
        switch (propiedad) {
            case "checkpointOff":
                this.mapa.activarAntorcha(x, y);
                this.lastCheckPoint = new Point();
                this.lastCheckPoint.x = (x - 1) * anchoCasillas + (anchoCasillas / 2);
                this.lastCheckPoint.y = (int) this.personajes.get(0).getY();
            case "transitable":
                this.personajes.get(0).setFalling(true);
                this.personajes.get(0).setEstaSobreSuelo(false);
                break;
            case "sostenedor":
                this.personajes.get(0).setEstaSobreSuelo(true);
                this.personajes.get(0).setY((y) * anchoCasillas - (int) this.personajes.get(0).getAlto());
                break;
            case "eliminatorio":
                this.personajes.get(0).reset(lastCheckPoint);
            default:
        }
    }

    private void checkPies(int x, int y) {
        String propiedad = this.mapa.getCasilla(x, y).getPropiedad();
        int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
        switch (propiedad) {
            case "checkpointOff":
                this.mapa.activarAntorcha(x, y);
                this.lastCheckPoint = new Point();
                this.lastCheckPoint.x = (x - 1) * anchoCasillas + (anchoCasillas / 2);
                this.lastCheckPoint.y = (int) this.personajes.get(0).getY();
                break;
        }
    }

}

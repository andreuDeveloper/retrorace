/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Andrés
 */
public class Mapa {

    private String nombre, imgRoute;
    private ArrayList<Casilla> casillas;
    private int[][] distribucion;
    private int[][] distribucionAux;
    private Image image;
    private boolean estadoAnimacion;

    public Mapa() {

    }

    /**
     * Si en las coordenadas proporcionadas hay una antorcha desactivada es
     * activada.
     *
     * @param x
     * @param y
     */
    public void activarAntorcha(int x, int y) {
        if (this.distribucion[y][x] == 4) {
            this.distribucion[y][x] = 9;
        }
    }

    /**
     * Si en las coordenadas proporcionadas hay una meta desactivada es
     * activada.
     *
     * @param x
     * @param y
     */
    public void activarMeta(int x, int y) {
        if (this.distribucion[y][x] == 11) {
            this.distribucion[y][x] = 5;
        }
    }

    /*
    Carga todos los tipos de casillas.
     */
    public void cargarCasillas() throws FileNotFoundException {
        Gson gson = new Gson();
        this.casillas = gson.fromJson(new FileReader("data/casillas.json"), new TypeToken<List<Casilla>>() {
        }.getType());
        for (Casilla casilla : this.casillas) {
            casilla.loadImage();
        }
    }

    /*
    Carga la imagen del Mapa.
     */
    public void cargarImagen() {
        ImageIcon ii = new ImageIcon(this.imgRoute);
        this.image = ii.getImage();
    }

    /**
     * Devuelve la casilla en relación a las coordenadas proporcionadas
     *
     * @param x
     * @param y
     * @return Casilla
     */
    public Casilla getCasilla(int x, int y) {
        try {
            if (((this.distribucion.length - 1) < y) && ((this.distribucion[0].length - 1) < x)) {
                return this.casillas.get(this.distribucion[this.distribucion.length - 1][this.distribucion[0].length - 1]);
            } else if ((this.distribucion.length - 1) < y) {
                return this.casillas.get(this.distribucion[this.distribucion.length - 1][x]);
            } else if ((this.distribucion[0].length - 1) < x) {
                return this.casillas.get(this.distribucion[y][this.distribucion[0].length - 1]);
            } else {
                return this.casillas.get(this.distribucion[y][x]);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Inicializa el mapa
     */
    public void iniciarMapa() {
        try {
            cargarCasillas();
            this.distribucionAux = new int[this.distribucion.length][this.distribucion[0].length];
            for (int i = 0; i < this.distribucion.length; i++) {
                for (int j = 0; j < this.distribucion[i].length; j++) {
                    this.distribucionAux[i][j] = this.distribucion[i][j];
                }
            }
            this.estadoAnimacion = true;
            this.animar();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void paint(Graphics g) {
        for (int row = 0; row <= 26; row++) {
            for (int col = 0; col <= 47; col++) {

                Image i = this.casillas.get(this.distribucion[row][col]).getImage();
                int xs = (col * i.getWidth(null));
                int y = (row * i.getHeight(null));
                int w = i.getWidth(null);
                int h = i.getHeight(null);
                g.drawImage(i, xs, y, w, h, null);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Para el Thread que anima este mapa.
     */
    public void pararAnimacion() {
        this.estadoAnimacion = false;
    }

    /**
     * Recarga la distibucion.
     */
    public void recargarDistribucion() {
        for (int i = 0; i < this.distribucion.length; i++) {
            for (int j = 0; j < this.distribucion[i].length; j++) {
                this.distribucion[i][j] = this.distribucionAux[i][j];
            }
        }
    }

    /**
     * Recorre la distribucion y en caso de tener algun elemento activo, este es
     * animado.
     *
     */
    private void animar() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (estadoAnimacion) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    for (int row = 0; row <= 26; row++) {
                        for (int col = 0; col <= 47; col++) {
                            switch (distribucion[row][col]) {
                                case 5:
                                    distribucion[row][col] = 10;
                                    break;
                                case 10:
                                    distribucion[row][col] = 5;
                                    break;
                                case 9:
                                    distribucion[row][col] = 12;
                                    break;
                                case 12:
                                    distribucion[row][col] = 9;
                                    break;

                            }
                        }
                    }
                }
            }
        }
        );
        thread.start();
    }

    public int[][] getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(int[][] distribucion) {
        this.distribucion = distribucion;
    }

    public String getNombre() {
        return nombre;
    }

    public Image getImage() {
        return image;
    }

}

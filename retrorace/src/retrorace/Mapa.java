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
    private int x_pj;
    private Image image;

    public Mapa() {

    }

    /**
     * Inicializa el mapa
     */
    public void iniciarMapa() {
        try {
            cargarCasillas();
            ImageIcon ii = new ImageIcon(imgRoute);
            image = ii.getImage();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sirve para mover el mapa en relación a la coordenada x proporcionada.
     *
     * @param g Graphics
     * @param x coordenada Int
     */
//    public void mover(Graphics g, int x) {
//        if (x > this.distribucion[0].length - 18) {
//            this.x_pj = this.distribucion[0].length - 18;
//        } else if (x < 0) {
//            this.x_pj = 0;
//        } else {
//            this.x_pj = x;
//        }
//        paint(g);
//    }

    /**
     * Devuelve la casilla en relación a las coordenadas proporcionadas
     *
     * @param x
     * @param y
     * @return Casilla
     */
    public Casilla getCasilla(int x, int y) {
        try {
            if ((this.distribucion.length - 1) < y) {
                return this.casillas.get(this.distribucion[this.distribucion.length - 1][x]);
            } else {
                return this.casillas.get(this.distribucion[y][x]);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public void paint(Graphics g) {
        for (int row = 0; row <= 26; row++) {
            for (int col = 0; col <= 47; col++) {

                Image i = this.casillas.get(this.distribucion[row][col + x_pj]).getImage();
                int xs = (col * i.getWidth(null));
                int y = (row * i.getHeight(null));
                int w = i.getWidth(null);
                int h = i.getHeight(null);
                g.drawImage(i, xs, y, w, h, null);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    public void activarAntorcha (int x, int y){
        if(this.distribucion[y][x]==4){
            this.distribucion[y][x]=9;
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

    public int[][] getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(int[][] distribucion) {
        this.distribucion = distribucion;
    }

    public int getX_pj() {
        return x_pj;
    }

    public String getNombre() {
        return nombre;
    }

    public Image getImage() {
        return image;
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.Dimension;
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
import javax.swing.JPanel;

/**
 *
 * @author Andrés
 */
public class Mapa extends JPanel {

    private String nombre, imgRoute;
    private ArrayList<Casilla> casillas;
    private int[][] distribucion;
    private int x_pj, inicioMapa, finMapa, anchoMapa;
    private Dimension screenSize;
    private Image image;

    public Mapa() {

    }

    /**
     * Inicializa el mapa
     */
    public void iniciarMapa() {
        try {
            setDoubleBuffered(true);
            cargarCasillas();
            this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            calcularMapa();
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
    public void mover(Graphics g, int x) {
        if (x > this.distribucion[0].length) {
            this.x_pj = this.distribucion[0].length - this.anchoMapa;
        } else if (x < 0) {
            this.x_pj = 0;
        } else {
            this.x_pj = x;
        }
        paint(g);
    }

    /**
     * Devuelve la casilla en relación a las coordenadas proporcionadas
     *
     * @param x
     * @param y
     * @return Casilla
     */
    public Casilla getCasilla(int x, int y) {
        System.out.println("DIST"+this.distribucion[y][x]);
        System.out.println("CAS"+this.casillas);
        System.out.println("met"+this.casillas.get(this.distribucion[y][x]));
        return this.casillas.get(this.distribucion[y][x]);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        for (int row = this.inicioMapa, aux = 0; row <= this.finMapa; row++, aux++) {
            for (int col = 0; col < this.anchoMapa; col++) {
                Image i = this.casillas.get(this.distribucion[row][col + x_pj]).getImage();
                int xs = (col * i.getWidth(null));
                int y = (aux * i.getHeight(null));
                int w = i.getWidth(null);
                int h = i.getHeight(null);
                g.drawImage(i, xs, y, w, h, null);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    public void cargarCasillas() throws FileNotFoundException {
        Gson gson = new Gson();
        this.casillas = gson.fromJson(new FileReader("data/casillas.json"), new TypeToken<List<Casilla>>() {
        }.getType());
        for (Casilla casilla : this.casillas) {
            casilla.loadImage();
        }
    }

    public void calcularMapa() {
        int mitadCantidadCasillas, mitadDist;
        Image i = this.casillas.get(this.distribucion[0][0]).getImage();
        mitadCantidadCasillas = (this.screenSize.height / i.getHeight(null)) / 2;
        mitadDist = this.distribucion.length / 2;
        this.inicioMapa = mitadDist - mitadCantidadCasillas;
        this.finMapa = mitadDist + mitadCantidadCasillas;
        this.anchoMapa = this.screenSize.width / i.getWidth(null);
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

    public void setX_pj(int x_pj) {
        this.x_pj = x_pj;
    }

    public String getNombre() {
        return nombre;
    }

    public Image getImage() {
        return image;
    }

    public int getAnchoMapa() {
        return anchoMapa;
    }

}

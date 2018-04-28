/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Andrés
 */
public class Mapa extends JPanel {

    private String nombre;
    private String imgRoute;
    private ArrayList<Casilla> casillas;
    private int[][] distribucion;


    public Mapa(String nombre, int[][] distribucion) {
        try {
            this.nombre = nombre;
            this.distribucion = distribucion;
            cargarCasillas();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // the method that does the drawing:
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < this.distribucion.length; row++) {
            for (int col = 0; col < this.distribucion[row].length; col++) {
                Image i = this.casillas.get(this.distribucion[row][col]).getImage();
                int x = (col * i.getWidth(null)) / this.distribucion[row].length;
                int y = (row * i.getHeight(null)) / this.distribucion.length;
                int w = i.getWidth(null) / this.distribucion[row].length;
                int h = i.getHeight(null) / this.distribucion.length;
                g.drawImage(i, x, y, w, h, null);
            }
        }
    }

    private void cargarCasillas() throws FileNotFoundException {
        Gson gson = new Gson();
        this.casillas = gson.fromJson(new FileReader("data/casillas.json"), new TypeToken<List<Casilla>>() {
        }.getType());
        for (Casilla casilla : this.casillas) {
            casilla.loadImage();
        }
    }

}

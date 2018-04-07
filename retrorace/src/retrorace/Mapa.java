/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrés
 */
public class Mapa {

    private String nombre;
    private String imgRoute;
    private Casilla[] casillas;
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

    private void cargarCasillas() throws FileNotFoundException {
        Gson gson = new Gson();
        this.casillas = gson.fromJson(new FileReader("data/casillas.json"), Casilla[].class);
    }

}

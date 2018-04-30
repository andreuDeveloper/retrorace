/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author sosan
 */
public class Retrorace {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        // TODO code application logic here
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGui();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Retrorace.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Retrorace.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        */
        Juego j = new Juego();
        j.initJuego();

    }

    private static void createAndShowGui() throws FileNotFoundException, InterruptedException {
        ArrayList<Mapa> mapas;
        Gson gson = new Gson();
        mapas = gson.fromJson(new FileReader("data/mapas.json"), new TypeToken<List<Mapa>>() {
        }.getType());
        mapas.get(0).iniciarMapa();

        JFrame frame = new JFrame("Mapa");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(mapas.get(0));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize);
        frame.setVisible(true);
        for (int x = 15; x <= (mapas.get(0).getDistribucion()[0].length - mapas.get(0).getAnchoMapa()); x++) {
            mapas.get(0).mover(frame.getGraphics(), x);
            Thread.sleep(100);
        }
    }

}

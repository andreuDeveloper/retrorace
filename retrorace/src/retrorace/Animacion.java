/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

/**
 *
 * @author sosan
 */
public class Animacion implements Runnable {

    private int contador;

    public Animacion() {
        contador = 0;
    }

    public int getContador() {
        return contador;
    }

    public void restartContador() {
        this.contador = 0;
    }
    
    

    @Override
    public void run() {
        while (true) {
            try {
                
                this.contador++;
                
                Thread.sleep(40);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}

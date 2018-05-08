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
public class Timer implements Runnable {

    private int contador;
    private int interval;
    private boolean active;
    private boolean pause;

    public Timer(int interval) {
        contador = 0;
        active = true;
        this.interval = interval;
    }

    public int getContador() {
        return contador;
    }

    public void restartContador() {
        this.contador = 0;
    }
    
    public void pausar() {
        this.pause = true;
    }
    
    public void resume() {
        this.pause = false;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void end() {
        this.active = false;
    }

    @Override
    public void run() {
        while (active) {

            if (!pause) {
                try {
                    this.contador++;
                    Thread.sleep(interval);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}

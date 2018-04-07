/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import javax.swing.Icon;

/**
 *
 * @author Andrés
 */
public class Casilla {
    private int id;
    private String imgRoute,imgAuxRoute;
    private String propiedad;

    public Casilla() {
     
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgRoute() {
        return imgRoute;
    }

    public void setImgRoute(String imgRoute) {
        this.imgRoute = imgRoute;
    }

    public String getImgAuxRoute() {
        return imgAuxRoute;
    }

    public void setImgAuxRoute(String imgAuxRoute) {
        this.imgAuxRoute = imgAuxRoute;
    }

    public String getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(String propiedad) {
        this.propiedad = propiedad;
    }
   
}

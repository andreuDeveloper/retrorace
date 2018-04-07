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
    private Icon img,imgAux;
    private String propiedad;

    public Casilla(int id, Icon img, Icon imgAux, String propiedad) {
        this.id = id;
        this.img = img;
        this.imgAux = imgAux;
        this.propiedad = propiedad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Icon getImg() {
        return img;
    }

    public void setImg(Icon img) {
        this.img = img;
    }

    public Icon getImgAux() {
        return imgAux;
    }

    public void setImgAux(Icon imgAux) {
        this.imgAux = imgAux;
    }

    public String getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(String propiedad) {
        this.propiedad = propiedad;
    }
    
    
    
    
}

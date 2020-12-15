/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import static control.Globales.ALTO_LADRILLO;
import static control.Globales.ANCHO_LADRILLO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Jorge Ivan
 */
public class Ladrillo {
    Aliens aliens;
    int X,Y;
    boolean hayimpacto=false;

    public Ladrillo(Aliens aliens) {
        this.aliens = aliens;
    }

    
    public void setPosition(int X,int Y){
        this.X=X;
        this.Y=Y;
    }
    
    public void dibujarLadrillo(Graphics g){
        if (hayimpacto==false) {
            g.setColor(Color.green);
            g.fillRect(X, Y, ANCHO_LADRILLO, ALTO_LADRILLO);
        }
    }

    public boolean isHayimpacto() {
        return hayimpacto;
    }

    public void setHayimpacto(boolean hayimpacto) {
        this.hayimpacto = hayimpacto;
    }
    public Rectangle getLimitRectangle(){
        return new Rectangle(X, Y, ANCHO_LADRILLO, ALTO_LADRILLO);
    } 
    
}

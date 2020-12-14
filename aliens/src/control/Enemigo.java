/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author hugo
 */
public class Enemigo implements Globales{
    
    private int posicionX = 0;
    private int posicionY = 0;

    private boolean abatido = false;

    private Image alienImage = null;

    Aliens aliens = null;
    
    private int puntaje;

    //Constructor
    public Enemigo(Image imagenAlien, Aliens aliens, int puntaje) {
        this.alienImage = imagenAlien;
        this.aliens = aliens;
        this.puntaje = puntaje;
    }
    
    //setea la posición de dicho alien en pantalla
    public void setPosition(int x, int y) {
        posicionX = x;
        posicionY = y;
    }

    
    public int getXPos() {
        return posicionX;
    }

    
    public int getYPos() {
        return posicionY;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    public boolean isAbatido() {
        return abatido;
    }

    public void setAbatido(boolean abatido) {
        this.abatido = abatido;
    }
    
    //Dibujar Alien con su tamaño
    public void dibujarAlien(Graphics g) {
        if(abatido == false)
          g.drawImage(alienImage, posicionX, posicionY, ANCHO_ALIEN,ALTURA_ALIEN,null);
    }           
    
    public Rectangle getLimitRectangle(){
        return new Rectangle(posicionX, posicionY, ANCHO_ALIEN, ALTURA_ALIEN);
    }
    
}

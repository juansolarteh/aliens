/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author hugo
 */
public class Nave implements  Globales {
   
    
    private int posicionX = 0;
    private int posicionY = 0;

     Aliens aliens = null;    
    boolean naveHerida = false;
    
    
    
    public Nave(Aliens ie) {
        this.aliens = ie;       
        
        //Posicion inicial de la nave
        this.posicionX = (int)(ANCHO_FRAME/2);
        this.posicionY = (int)((ALTO_FRAME)-(ALTURA_JUGADOR));
    }
    
    

    
    public void dibujarNave(Graphics g) {        
        Image img = new javax.swing.ImageIcon(NAVE_IMG).getImage();
        g.drawImage(img, posicionX, posicionY,null);

    }
           

    
    //Si una bomba impacto a la nave
    public boolean checkBomba(int xBomba, int yBomba) {
        
        //vamos a mirar si la nave ha sido impactada
        if ((yBomba >= (posicionY+20)) && (yBomba <= (posicionY+(ALTURA_JUGADOR)))) {
             //Si en el eje Y está bien, ahora miraremos en el eje X
            if ((xBomba >= posicionX) && (xBomba <= (posicionX+ANCHO_JUGADOR))) {
                //La nave fue herida!
                naveHerida = true;
                return true;
            }
        } 
        return false;
    }

    public void ImpactadoPorUnAlien()throws InterruptedException{
//       aliens.descontarVida();
    }

    public void setX(int x) {
        this.posicionX = x;
    }
    public void setY(int y) {
        this.posicionY = y;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }
    
    public boolean isNaveHerida() {
        return naveHerida;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import static control.Globales.ALTURA_ALIEN;
import static control.Globales.ANCHO_ALIEN;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebastian
 */
public class Proyectil  implements Runnable,Globales {
    

    protected int ANCHO_DISPARO = 5;
    protected int ALTURA_DISPARO = 2; 

    protected int x = 0;

    protected int y = 0;

    protected boolean estadoDeDisparo = true;


    
    Graphics g = null;  
    Aliens aliens;
    
    Thread thread;
    
    
    public Proyectil(int xVal, int yVal,  Graphics g,Aliens ie) {
        this.g = g;
        this.x = xVal+(ANCHO_JUGADOR/2)-ANCHO_DISPARO/2;//dirrección del disparo
        this.y = yVal+(ALTURA_JUGADOR-(ALTURA_JUGADOR));
        
        this.aliens = ie;
        
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setG(Graphics g) {
        this.g = g;
    }

    public void setAliens(Aliens aliens) {
        this.aliens = aliens;
    }
    
    
    
    private void impactar(){
        estadoDeDisparo = false;
        thread.interrupt();
    }
    
    public void dispara(){
        thread = new Thread(this);
        thread.start();
        
    }
    
        
    protected boolean moverDisparo() throws SQLException{                
        
        y -=  2;//cambiamos la posicion del proyectil
        dibujarProyectil(g);//lo dibujamos
        
       
        //validamos de que el disparo haya salido de la pantalla
        if (y > ALTO_FRAME) {
            estadoDeDisparo = false;//para no dibujar mas el proyectil
            return true;
        }
        return false;
    }

    //dibujar el disparo
    public void dibujarProyectil(Graphics g){
        //validamos que el disparo se pueda dibujar
        if (estadoDeDisparo) {
            g.setColor(Color.CYAN);
            g.fillRect(x, y, ANCHO_DISPARO,ALTURA_DISPARO );
        }
        
        
    }
    
    public Rectangle getLimitRectangle(){
        return new Rectangle(x, y, ANCHO_DISPARO, ALTURA_DISPARO);
    }

    public boolean getEstadoDeDisparo() {
        return estadoDeDisparo;
    }

    
    public void run() {
        while(true) {
            try {
                Thread.sleep(VELOCIDAD_DEL_PROYECTILES);
            } catch(InterruptedException ie) {
                
            }
            try {
                if (!aliens.isPaused()) {
                    if (moverDisparo() || aliens.getArmada().verifyImpact(getLimitRectangle())) {                        
                        break;//destruir disparo                        
                    }                   
             
                }
        
            } catch (SQLException ex) {
                Logger.getLogger(Proyectil.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

}

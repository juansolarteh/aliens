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
public class ProyectilAlien extends Proyectil{

    public ProyectilAlien(int xVal, int yVal, Graphics g, Aliens ie) {
        super(xVal, yVal, g, ie);
    }   
    
    @Override    
    protected boolean moverDisparo() throws SQLException{                
        
        y +=  2;//cambiamos la posicion del proyectil
        dibujarProyectil(g);//lo dibujamos
        
       
        //validamos de que el disparo haya salido de la pantalla
        if (y >= ALTO_FRAME-20) {
            estadoDeDisparo = false;//para no dibujar mas el proyectil
            return true;
        }
        return false;
    }

    //dibujar el disparo
    @Override
    public void dibujarProyectil(Graphics g){
        //validamos que el disparo se pueda dibujar
        if (estadoDeDisparo) {
            g.setColor(Color.RED);
            g.fillRect(x, y, ANCHO_DISPARO,ALTURA_DISPARO );
        }        
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(VELOCIDAD_DEL_PROYECTILES);
            } catch(InterruptedException ie) {
                
            }
            try {
                if (!aliens.isPaused()) {
                    if (moverDisparo()) {                        
                        break;//destruir disparo                        
                    }                   
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProyectilAlien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

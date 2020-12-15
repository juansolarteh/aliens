/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import static control.Globales.ALTURA_ALIEN;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juan-
 */
public class ControladorDisparoAlien implements Globales, Runnable{

    Aliens aliens;
    ProyectilAlien proyectil;
    Armada armada; 
    Thread thread;
    boolean activo = true;

    public ControladorDisparoAlien(Armada armada, Aliens aliens) {
        this.aliens = aliens;
        this.armada = armada;
    }
    
    public void iniciarDisparoAlienAutomatico(){
        thread = new Thread(this);
        thread.start();
    }
    
    public void frenarDisparoAutomatico(){
        activo = false;
    }
    
    @Override
    public void run() {
        while(activo) {
            try {
                Thread.sleep(tiempoEntreDisparo);
            } catch(InterruptedException ie) {
                
            }
            try {
                int rowEnemy = -1;
                int columnEnemy = 0;
                do {                    
                    columnEnemy = (int) Math.floor(Math.random()*(9+1));
                    rowEnemy = armada.getRowEnemyBelow(columnEnemy);
                } while (rowEnemy < 0);
                int posX = armada.getPositionXEnemy(rowEnemy, columnEnemy);
                int posY = armada.getPositionYEnemy(rowEnemy, columnEnemy) + ALTURA_ALIEN;
                ProyectilAlien proyectil = new ProyectilAlien(posX, posY, aliens.getGraphics(), aliens);
                if (proyectil.getEstadoDeDisparo()) {
                    proyectil.dispara();//disparamos
                }
            } catch (Exception ex) {
                Logger.getLogger(ProyectilAlien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}

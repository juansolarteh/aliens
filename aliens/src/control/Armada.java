/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hugo_Ordoñez
 */
public class Armada implements Globales {

    int enemyCount = 50;
    Enemigo[][] armandaEnemiga = new Enemigo[ALIENS_POR_FILA][FILAS_ALIENS];
    Aliens aliens = null;
    String direction = "RIGHT";
    private int puntajeArmada = 0;
    //Thread thread;

    public Armada(Aliens ai) {
        this.aliens = ai;
        this.iniciarArmada(this.aliens);
    }

    public void iniciarArmada(Aliens ai) {
        this.aliens = ai;
        double inicioEnimigoY = 1;
   
        for (int i = 0; i < ALIENS_POR_FILA; i++) {
            double inicioEnimigoX = 1;
            Image imgEnemigo = new javax.swing.ImageIcon(ALIEN_IMG[i]).getImage();
            for (int j = 0; j < FILAS_ALIENS; j++) {
                armandaEnemiga[i][j] = new Enemigo(imgEnemigo, aliens, (5-i)*10);
                armandaEnemiga[i][j].setPosition((int)(inicioEnimigoX * ANCHO_ALIEN), (int)(inicioEnimigoY* ALTURA_ALIEN));
                inicioEnimigoX += 1.5;
            }
            inicioEnimigoY+=1.5;
        }
        
//        thread = new Thread(this);
//        thread.start();
    }

    public void dibujarArmada(Graphics g) {
        for (int i = 0; i < ALIENS_POR_FILA; i++) {
            for (int j = 0; j < FILAS_ALIENS; j++) {
                armandaEnemiga[i][j].dibujarAlien(g);
            }
        }
    }
    
    public int getEnemyCount(){
        return enemyCount;
    }

    public int getPuntajeArmada() {
        return puntajeArmada;
    }   
    
    private void impact(Enemigo enemigo){
        puntajeArmada += enemigo.getPuntaje();
        enemigo.setAbatido(true);
        enemyCount--;
    }
    
    public boolean verifyImpact(Rectangle limitMissile){
        for (int i = 0; i < FILAS_ALIENS; i++) {
            for (int j = ALIENS_POR_FILA-1; j >= 0; j--) {
                if(armandaEnemiga[j][i].isAbatido() == false)
                    if(armandaEnemiga[j][i].getLimitRectangle().intersects(limitMissile)){
                        impact(armandaEnemiga[j][i]);
                        return true;
                    }else
                        break;
            }
        }
        return false;
    }
    
    public void moverAliens(){
        if (!aliens.isPaused())
            if(direction == "RIGHT")
                moveRight();
            else moveLeft();
    }
    
    private void moveRight(){
        for(Enemigo[] enemyRow : armandaEnemiga)
            for(Enemigo enemy: enemyRow)
                enemy.setPosicionX(enemy.getXPos() + DESPLAZAMIENTO_ALIENS);//cada enemigo se mueve unos pixeles a la derecha
        //validamos si esta en el borde derecho, si lo esta se cambia la direccion 
        if ((this.getXposEnemyRight() > (ANCHO_FRAME - ANCHO_ALIEN -32)))
            direction = "LEFT";
    }
    
    private void moveLeft(){
        for(Enemigo[] enemyRow : armandaEnemiga)
            for(Enemigo enemy: enemyRow)
                enemy.setPosicionX(enemy.getXPos() - DESPLAZAMIENTO_ALIENS);//cada enemigo se mueve unos pixeles a la izquierda
        //validamos si esta en el borde izquierdo, si lo esta se cambia la direccion 
        if ((this.getXposEnemyLeft() < (32))){
            moveBelow();
            direction = "RIGHT";
        }
    }
    
    private void moveBelow(){
       for(Enemigo[] enemyRow : armandaEnemiga)
            for(Enemigo enemy: enemyRow)
                enemy.setPosicionY(enemy.getYPos() + (DESPLAZAMIENTO_ALIENS * 2));//cada enemigo se mueve unos pixeles hacia abajo
    }
    
    // da la la posicion del enemigo que se encuentra mas a la izquierda
    public int getXposEnemyLeft(){
        for (int i = 0; i < FILAS_ALIENS; i++) 
            for (int j = 0; j < ALIENS_POR_FILA; j++)
                if(armandaEnemiga[j][i].isAbatido() == false)
                    return armandaEnemiga[j][i].getXPos();
        return 0;
    }
    
    // da la la posicion del enemigo que se encuentra mas a la derecha
    public int getXposEnemyRight(){
        for (int i = FILAS_ALIENS - 1; i >= 0; i--) 
            for (int j = 0; j < ALIENS_POR_FILA; j++)
                if(armandaEnemiga[j][i].isAbatido() == false)
                    return armandaEnemiga[j][i].getXPos();
        return 0;
    }
    
    // da la la posicion del enemigo que se encuentra mas abajo
    public int getYposEnemyBelow(){
        for (int i = ALIENS_POR_FILA-1; i >= 0; i--) 
            for (int j = 0; j < FILAS_ALIENS; j++)
                if(armandaEnemiga[i][j].isAbatido() == false)
                    return armandaEnemiga[i][j].getYPos();
        return 0;
    }
    
    //da la posicion del enemigo que se encuentre mas abajo en la columna column, si no existe
    //ningun enemigo en esa columna retorna -1
    public int getRowEnemyBelow(int column){
        for (int i = ALIENS_POR_FILA-1; i >= 0; i--) 
            if(armandaEnemiga[i][column].isAbatido() == false){
                System.out.println("posicion en fila: " + i);
                return i;
            }
        System.out.println("posicion en fila: -1");   
        return -1;
    }

//    @Override
//    public void run() {
//        while(true) {
//            try {
//                Thread.sleep(3500);
//            } catch(InterruptedException ie) {
//                
//            }
//            try {
//                int rowEnemy = -1;
//                int columnEnemy = 0;
//                do {                    
//                    columnEnemy = (int) Math.floor(Math.random()*(9+1));
//                    rowEnemy = getRowEnemyBelow(columnEnemy);
//                } while (rowEnemy < 0);
//                int posX = armandaEnemiga[rowEnemy][columnEnemy].getXPos();
//                int posY = armandaEnemiga[rowEnemy][columnEnemy].getYPos() + ALTURA_ALIEN;
//                ProyectilAlien proyectil = new ProyectilAlien(posX, posY, aliens.getGraphics(), aliens);
//                if (proyectil.getEstadoDeDisparo()) {
//                    proyectil.dispara();//disparamos
//                }
//            } catch (Exception ex) {
//                Logger.getLogger(ProyectilAlien.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    
    public int getPositionXEnemy(int rowEnemy, int columnEnemy){
        return armandaEnemiga[rowEnemy][columnEnemy].getXPos();
    }
    
    public int getPositionYEnemy(int rowEnemy, int columnEnemy){
        return armandaEnemiga[rowEnemy][columnEnemy].getYPos();
    }
}

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
 * @author Hugo_Ordoñez
 */
public class Armada implements Globales {

    int enemyCount = 50;
    Enemigo[][] armandaEnemiga = new Enemigo[ALIENS_POR_FILA][FILAS_ALIENS];
    Aliens aliens = null;
    String direction = "RIGHT";
    private int puntajeArmada = 0;

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
            moveDown();
            direction = "RIGHT";
        }
    }
    
    private void moveDown(){
       for(Enemigo[] enemyRow : armandaEnemiga)
            for(Enemigo enemy: enemyRow)
                enemy.setPosicionY(enemy.getYPos() + (DESPLAZAMIENTO_ALIENS * 2));//cada enemigo se mueve unos pixeles hacia abajo
    }
    
    public int getXposEnemyLeft(){
        for (int i = 0; i < FILAS_ALIENS; i++) 
            for (int j = 0; j < ALIENS_POR_FILA; j++)
                if(armandaEnemiga[j][i].isAbatido() == false)
                    return armandaEnemiga[j][i].getXPos();
        return 0;
    }
    
    public int getXposEnemyRight(){
        for (int i = FILAS_ALIENS - 1; i >= 0; i--) 
            for (int j = 0; j < ALIENS_POR_FILA; j++)
                if(armandaEnemiga[j][i].isAbatido() == false)
                    return armandaEnemiga[j][i].getXPos();
        return 0;
    }
    
    public int getYposEnemyDown(){
        for (int i = ALIENS_POR_FILA-1; i >= 0; i--) 
            for (int j = 0; j < FILAS_ALIENS; j++)
                if(armandaEnemiga[i][j].isAbatido() == false)
                    return armandaEnemiga[i][j].getYPos();
        return 0;
    }
    
    public void setArmyPositionX(){
        
    }
}

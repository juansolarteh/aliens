/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Jorge Ivan
 */
public class Bloque implements Globales{
    Ladrillo[][] Matriz = new Ladrillo[2][3];
    Ladrillo[][] Matriz2 = new Ladrillo[2][3];
    Ladrillo[][] Matriz3 = new Ladrillo[2][3];
    Aliens aliens;
    Armada armada; 
    Thread thread;
    Graphics g;

    public Bloque(Aliens aliens, Graphics g) {
        this.aliens = aliens;
        this.g=g;
        iniciarBloques();
        
    }

    private void iniciarBloque(int inicioX, int inicioY,Ladrillo[][] ld){
        
        for (int i = 0; i < 2; i++) {
            int initX=inicioX;
            for (int j = 0; j < 3; j++) {
                ld[i][j]= new Ladrillo(aliens);
                ld[i][j].setPosition(initX*ANCHO_LADRILLO,inicioY*ALTO_LADRILLO);
                initX=initX+1;
            }
            inicioY=inicioY+1;
        }
        
    }
   private void iniciarBloques(){
       iniciarBloque(3,18,Matriz);
       iniciarBloque(9,18,Matriz2);
       iniciarBloque(15,18,Matriz3);
   }
    public void dibujarBloques(Graphics g){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
               Matriz[i][j].dibujarLadrillo(g);
               Matriz2[i][j].dibujarLadrillo(g);
               Matriz3[i][j].dibujarLadrillo(g);
            }
        }
    }
    
    public boolean verificarImpacto(Rectangle limitMissile){
        if(verificarImpacto(limitMissile,Matriz)==false){
            if (verificarImpacto(limitMissile,Matriz2)==false) {
                return verificarImpacto(limitMissile,Matriz3);
            }
        }
        return true;
    }
    private boolean verificarImpacto(Rectangle limitMissile,Ladrillo[][] ld){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                if (ld[i][j].isHayimpacto()==false) {
                    if (ld[i][j].getLimitRectangle().intersects(limitMissile)) {
                        impacto(ld[i][j]);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private void impacto(Ladrillo lad){
        lad.setHayimpacto(true);
    }
    
    public ArrayList<Rectangle> getLimiteBloques(){
        ArrayList<Rectangle> rectangulos = getLimiteBloque(Matriz);
        rectangulos.addAll(getLimiteBloque(Matriz2));
        rectangulos.addAll(getLimiteBloque(Matriz3));
        return rectangulos;
    }
    
    private ArrayList<Rectangle> getLimiteBloque(Ladrillo[][] ld){
        ArrayList<Rectangle> rectangulos = new ArrayList();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (ld[j][i].isHayimpacto()==false) {        
                    rectangulos.add(ld[j][i].getLimitRectangle());
                    break;
                }
            }
        }
        return rectangulos;
    }
    
}

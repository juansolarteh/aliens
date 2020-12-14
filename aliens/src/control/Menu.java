/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import static control.Globales.ALTO_FRAME;
import static control.Globales.ANCHO_FRAME;
import static control.Globales.ICONO_APLICATION;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author juan-
 */
public class Menu extends JFrame implements KeyListener{
    
    private Graphics graficos;
    private BufferedImage imgBuffered;
    private int mejorPuntaje = 0;
    private boolean juegoIniciado = false;
    
    public Menu(String titulo){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        Image iconoAplicacion = Toolkit.getDefaultToolkit().getImage(ICONO_APLICATION);
        this.setIconImage(iconoAplicacion);
        imgBuffered = new BufferedImage(ANCHO_FRAME, ALTO_FRAME, BufferedImage.TYPE_INT_RGB);
        graficos = imgBuffered.createGraphics();
        this.setBackground(Color.black);
        this.setSize(ANCHO_FRAME, ALTO_FRAME);
        this.setLocationRelativeTo(this);
        this.setVisible(true);
        addKeyListener(this);
    }
    
    public void paint(Graphics g) {
        //El fondo del frame de juego
        graficos.setColor(Color.black);
        graficos.fillRect(0, 0, ANCHO_FRAME, ALTO_FRAME);


        //cadenas de texto dibujadas en el frame
        graficos.setColor(Color.CYAN);
        graficos.drawString("MEJOR PUNTAJE: " + mejorPuntaje, 275, 250);
        graficos.drawString("PRESIONE LA TECLA ESPACIO PARA EMPEZAR EL JUEGO", 165, 270);

        //dibujamos el buffer que contiene todo
        g.drawImage(imgBuffered, 0, 0, this);    
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            juegoIniciado = true;
            this.setVisible(false);
            Aliens aliens = new Aliens("Aliens Invasores");
            aliens.nuevaRonda();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

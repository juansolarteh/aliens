/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 *
 * @author hugo
 */
public final class Aliens extends JFrame implements Globales, Runnable, KeyListener {

    private int numAliens;
    private int vidas = CANTIDAD_VIDAS;
    ;    
    private int puntaje = 0;
    int contador;// variable para controlar la velocidad de movimiento de los aliens
    int reguladorDeVelocidad;
    private boolean pausa = false;
    Proyectil proyectil;
    private Nave nave = new Nave(this);
    private Armada armada = new Armada(this);

    private Graphics graficos;
    private BufferedImage imgBuffered;

    private Image backGroundImage = null;
    private Image alienImage = null;
    private Thread thread;

    private int ronda;
    private int velocidad;
    
    private Menu menu;
    
    public Armada getArmada(){
        return armada;
    }
    
    public int getPuntaje(){
        return puntaje;
    }

    public Aliens(String titulo, Menu menu) {
        super(titulo);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        Image iconoAplicacion = Toolkit.getDefaultToolkit().getImage(ICONO_APLICATION);
        this.setIconImage(iconoAplicacion);
        this.menu = menu;
        
        //Imagen de fondo
        thread = new Thread(this);
        ronda = 0;
        contador = 0;
        reguladorDeVelocidad = 5;
        velocidad = VELOCIDAD_DEL_JUEGO;
    }

    public void paint(Graphics g) {
        updateEnemyNumber();
        
        //El fondo del frame de juego
        graficos.setColor(Color.black);
        graficos.fillRect(0, 0, ANCHO_FRAME, ALTO_FRAME);

        //dibujamos la armada y la nave con los mismo graficos
        armada.dibujarArmada(graficos);
        nave.dibujarNave(graficos);

        //cadenas de texto dibujadas en el frame
        graficos.setColor(Color.CYAN);
        graficos.drawString("Ronda: " + ronda, 20, 50);
        graficos.drawString("Vidas: " + vidas, 20, 70);
        graficos.drawString("Aliens: " + numAliens, 20, 90);
        graficos.drawString("Puntaje: " + puntaje, 20, 110);

        //dibujamos el buffer que contiene todo
        g.drawImage(imgBuffered, 0, 0, this);
        
    }
    //-----------------------------------
    
    
    
    public void updateEnemyNumber(){
        numAliens = armada.getEnemyCount();
    }

    public void nuevaRonda() {
        //Crea la nave
        Nave nave = new Nave(this);
        Armada armada = new Armada(this);
       

        //cantadidad de aliens
        numAliens = 50;

        //crear gráficos
        imgBuffered = new BufferedImage(ANCHO_FRAME, ALTO_FRAME, BufferedImage.TYPE_INT_RGB);
        graficos = imgBuffered.createGraphics();

        //componentes del frame
        this.setBackground(Color.black);
        this.setSize(ANCHO_FRAME, ALTO_FRAME);
        this.setLocationRelativeTo(this);
        this.setVisible(true);

        //acumulador de rondas sobrevividas
        ronda++;

        //comienza a correr el thread
        iniciaJuego();

    }

    //Método para iniciar nuestro hilo
    public void iniciaJuego() {
        if (!thread.isAlive()) {
            thread.start();
        }

    }

    //vuelve a ejecutar el metodo paint()
    public void update(Graphics g) {
        paint(g);
    }

    public void run() {
        addKeyListener(this);//activo los eventos de teclado
//        controlNivel();
        while (true) {

            try {
                Thread.sleep(velocidad);//tiempo de espera de movimiento de los aliens
            } catch (InterruptedException ex) {

            }

            if (!pausa) {//determino si está en pausa el juego
                if (contador >= reguladorDeVelocidad) {
                    armada.moverAliens();
                    definirVelocidad();
                    contador = 0;    
                }
                repaint();//Actualiza el frame
                contador++;
                if(armada.getYposEnemyDown() >= 420){
                    if(vidas == 1)
                        break;
                    thread.interrupt();
                    armada = new Armada(this);
                    vidas--;
                    nuevaRonda();
                }
            }
        }
        this.setVisible(false);
        menu.rePaint();
    }
    
    private void definirVelocidad(){
        if (armada.getEnemyCount() <= 10 && velocidad == 20)
            velocidad = 10;
        else if(armada.getEnemyCount() <= 20 && velocidad == 30)
            velocidad = 20;
        else if(armada.getEnemyCount() <= 30 && velocidad == 40)
            velocidad = 30;
        else if(armada.getEnemyCount() <= 40 && velocidad == 50)
            velocidad = 40;
    }

    public boolean isPaused() {
        return pausa;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //primero validamos que no esten en los bordes y que no este en pausa la ejecución        
        if (!pausa && e.getKeyCode() == KeyEvent.VK_LEFT && !(nave.getPosicionX() < (32))) {
            nave.setX(nave.getPosicionX() - DESPLAZAMIENTO_JUGADOR);//nos movemos unos pixeles a la izquierda         
        }
        if (!pausa && e.getKeyCode() == KeyEvent.VK_RIGHT && !(nave.getPosicionX() > (ANCHO_FRAME - ANCHO_JUGADOR - 32))) {
            nave.setX(nave.getPosicionX() + DESPLAZAMIENTO_JUGADOR);//nos movemos unos pixeles a la derecha
        }
//      
//        //evento de disparo
        if (!pausa && e.getKeyCode() == KeyEvent.VK_SPACE) {
            //creamos un proyectil
            proyectil = new Proyectil(nave.getPosicionX(), nave.getPosicionY(), getGraphics(), this);
            if (proyectil.getEstadoDeDisparo()) {
                proyectil.dispara();//disparamos
            }
        }
//        

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}

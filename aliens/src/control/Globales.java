/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author hugo
 */
public interface Globales {
    
    public static final int ALTURA_ALIEN = 32;
    public static final int ANCHO_ALIEN = 32;
    
    public static final int ALTURA_JUGADOR = 32;
    public static final int ANCHO_JUGADOR = 32;
    
    public static final int FILAS_ALIENS = 10;
    public static final int ALIENS_POR_FILA = 5; 
    
    public static final int DESPLAZAMIENTO_ALIENS = 10;
    public static final int DESPLAZAMIENTO_JUGADOR = 10;
    
    public static final int CANTIDAD_VIDAS = 3;    
    
    //Imagen 650*486
    public static final int ANCHO_FRAME = 650;
    public static final int ALTO_FRAME = 486;
    
    public static final int VELOCIDAD_DEL_JUEGO = 50;
    public static final int VELOCIDAD_DEL_PROYECTILES = 10;
    
    public final String[] ALIEN_IMG = {"src/imagenes/ufo1.png", "src/imagenes/UFO2.png","src/imagenes/ufo2.png","src/imagenes/UFO-32.png","src/imagenes/UFO-32.png"};
    public final String NAVE_IMG = "src/imagenes/nave1.png"; 
    public final String EXPLOSION_IMG = "src/imagenes/explosion.jpg"; 
    public final String ICONO_APLICATION = "src/imagenes/icono-aplicacion.png";
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import control.Aliens;
import control.Menu;
import javax.swing.JFrame;

/**
 *
 * @author hugo
 */
public class Jugar{
    static JFrame jf;
    public static void main(String[] args) {
        Menu menu = new Menu("Aliens Invasores");
        Aliens aliens = new Aliens("Aliens Invasores");
        aliens.nuevaRonda();
    }
}

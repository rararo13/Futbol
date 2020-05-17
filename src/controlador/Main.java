/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import vista.Vista;

/**
 *
 * @author Rafael
 */
public class Main {
    
    public static void main(String[] args) {
        //ejecuta el Controlador y este la vista
        new Controlador( new Vista() ).iniciar() ;
    }
}

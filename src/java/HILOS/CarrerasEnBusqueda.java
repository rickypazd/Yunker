/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HILOS;

import java.util.HashMap;

/**
 *
 * @author ricardopazdemiquel
 */
public class CarrerasEnBusqueda {
    
    private static HashMap<String,BuscarConductor> carreras;
    
    public static HashMap<String,BuscarConductor> get_carreras(){
       if(carreras==null){
           carreras= new HashMap<>();
       }
       return carreras;
    }
    
}

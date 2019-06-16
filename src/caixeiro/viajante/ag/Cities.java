/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caixeiro.viajante.ag;

import java.util.ArrayList;


/**
 *
 * @author wachsmann
 */
public class Cities {
    
    public Cities(Integer quantity){
        if(quantity < 21 && quantity > 0){
            this.quantity = quantity;
            this.distances = new ArrayList<>();
            this.matriz = new int[quantity][quantity];
        }else{
            throw new ExceptionInInitializerError("Quantidade inválida");
        }
        
    }
    
    private Integer quantity = 0;
    
    private ArrayList<Integer> distances;

    public int matriz[][];
    
    public Integer getQuantity() {
        return this.quantity;
    }
    public ArrayList<Integer> getDistances() {
        return distances;
    }

    public void addDistance(Integer distance){
            this.distances.add(distance);
    }
    
}

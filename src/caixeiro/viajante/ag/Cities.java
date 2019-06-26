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
            this.matrice = new int[quantity][quantity];
            this.population = getProportionalPopulation();
        }else{
            throw new ExceptionInInitializerError("The number of cities is invalid");
        }
        
    }
    private Integer population = 0;
    private Integer quantity = 0;
    
    private ArrayList<Integer> distances;
    
    public int[][] matrice;
    
    public Integer getQuantity() {
        return this.quantity;
    }
    public Integer getPopulation() {
        return this.population;
    }
    public ArrayList<Integer> getDistances() {
        return distances;
    }

    public void addDistance(Integer distance){
            this.distances.add(distance);
    }

    private Integer getProportionalPopulation() {
        if(this.quantity >= 10){
              return (int)(factorialUsingRecursion(this.quantity)*0.05);
        }else{
            return (int)(factorialUsingRecursion(this.quantity)*0.10);     
        }
       
       
    }
    private long factorialUsingRecursion(int n) {
    if (n <= 2) {
        return n;
    }
    return n * factorialUsingRecursion(n - 1);
    }
}

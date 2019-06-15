/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caixeiro.viajante.ag;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wachsmann
 */
public class CaixeiroViajanteAg {

    /**
     * @param args the command line arguments
     */
    public static ArrayList<Cities> citiesList;
    
    public static void main(String[] args) {
        try{
            citiesList = new ArrayList<Cities>();
            readFile("teste.txt");
            for (Cities cities : citiesList) {
                ArrayList<Integer> distances = cities.getDistances();
                System.out.println("Quantidade de cidades: "+ cities.getQuantity());
                
                for (int i = 1; i < distances.size(); i++) {
                    System.out.println(" Distância entre duas cidades " + distances.get(i));
                }
            }
                
            
        } catch (IOException ex) {
            Logger.getLogger(CaixeiroViajanteAg.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /*
        Lê arquivo e faz importação de dados pra dentro da lista citiesList
    */
    
    public static void readFile(String name) throws FileNotFoundException, IOException{
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(name))) {
            String line;
           
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
                
            }
            for (List<String> record : records) {
                
                Cities cities = new Cities(Integer.parseInt(record.get(0)));
                for (int i = 0; i < record.size(); i++) {
                    System.out.println();
                    cities.addDistance(Integer.parseInt(record.get(i).replaceAll("\\s+","")));
                }
                citiesList.add(cities);
            }
          
        }catch(ExceptionInInitializerError ex){
            System.out.println(ex.getMessage());
        }
          
    }
    
}

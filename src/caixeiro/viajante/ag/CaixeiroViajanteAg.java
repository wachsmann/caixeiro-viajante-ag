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
            
            formatMatriceData();
            
            for (Cities cities : citiesList) {
                System.out.println("Solution for " + cities.getQuantity() + " cities:");
                GeneticAlgorithm.resolve(cities.getQuantity(),
                    cities.matrice,
                    (float) 0.5,
                    cities.getPopulation(),
                    20000,
                    false
                );
            }
            
        } catch (IOException ex) {
            Logger.getLogger(CaixeiroViajanteAg.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
    * 
    *  Formata data set para matrizes de adjacências
    *
    */
    
    public static void formatMatriceData(){
        for (Cities cities : citiesList) {
                ArrayList<Integer> distances = cities.getDistances();
                distances.remove(0);
                
                int[][] matrice = new int[cities.getQuantity()][cities.getQuantity()];

                //System.out.println(distances);
                //System.out.println("\n");
                
                // Inicialização da matriz com "0"
                for (int i = 0; i < cities.getQuantity(); i++) {
                    for (int j = 0; j < cities.getQuantity(); j++) {
                        if (i == j) {
                            matrice[i][j] = 0;
                        } else {
                            matrice[i][j] = -1;
                        }
                    }
                }
                
                // Declara variaveis para poder utilizar
                int aux = 0, aux_2 = 1;
                
                // Percorre as distancias de cada cidade
                for (int i = 0; i < distances.size(); i++) {
                    
                    // Caso checar no final da matriz entra
                    if (aux_2 >= cities.getQuantity()) {
                        
                        if (matrice[aux][0] == -1 || matrice[0][aux] == -1) {
                            matrice[aux][0] = distances.get(i);
                            matrice[0][aux] = distances.get(i);
                        }
                        
                        
                        // Percorre verificando todas as conexões e adiciona caso não tenha 
                        for (int j = 0; j < cities.getQuantity(); j++) {
                            for (int k = 0; k < cities.getQuantity(); k++) {
                                if (matrice[j][k] == -1 || matrice[k][j] == -1) {
                                    i++;
                                    matrice[j][k] = distances.get(i);
                                    matrice[k][j] = distances.get(i);
                                }
                            }
                        }
                        
                        break;
                    }
                    
                    if (matrice[aux][aux_2] == -1 || matrice[aux_2][aux] == -1) {
                        matrice[aux][aux_2] = distances.get(i);
                        matrice[aux_2][aux] = distances.get(i);
                    }
                    
                    aux++;
                    aux_2++;
                }
                
                String saida = "";
                for (int i = 0; i < cities.getQuantity(); i++) {
                    for (int j = 0; j < cities.getQuantity(); j++) {
                        saida += matrice[i][j] + "\t";
                    }
                    saida += "\n";
                }
                
                //System.out.println(saida);
                
                cities.matrice = matrice;
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

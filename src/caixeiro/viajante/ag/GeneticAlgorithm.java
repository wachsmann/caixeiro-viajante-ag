package caixeiro.viajante.ag;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneticAlgorithm {

    public static int CITY_COUNT;
    public static int POPULATION = 10;
    
    public static void resolve(int numCities,int[][] map, float mortalityRate,
                                int population, int evolutionNumber, boolean showcaseEvolution){
        CITY_COUNT = numCities;
        POPULATION = population;
        String[] cities = nameCities(numCities);
        int[][] chromosomePopulation = new int[POPULATION][CITY_COUNT];
        int[] results = new int[POPULATION];

        generateChromosomesRandomly(chromosomePopulation);
        calculateResults(chromosomePopulation, results, map);
        
        //orderResults(chromosomePopulation, results);
        if (showcaseEvolution)
                printEvolution(chromosomePopulation, results, cities);
        
        for (int i = 0; i < evolutionNumber; i++) {
            
                //TODO: tournament
                int[] parents =new int[2];
                
                parents[0] = applyTournament(results);
                do{
                    parents[1] = applyTournament(results);
                }while(parents[1] == parents[0]);
                

                renovateChromosomes(chromosomePopulation, results, mortalityRate,parents);
                calculateResults(chromosomePopulation, results, map);
                //orderResults(chromosomePopulation, results);
                if (showcaseEvolution) {
                        System.out.println("generation: " + (i + 1));
                        printEvolution(chromosomePopulation, results, cities);
                }
            
                
        }
        // mostrando resultado encontrado
        showResult (chromosomePopulation, results, cities);
        
    }

    public static int applyTournament(int[] results){
        
        int bestOne = -1;
        
       
        for (int i = 0; i < POPULATION; i++) {
            int randomIndividual = new Random().nextInt(POPULATION);
            
            if(bestOne == -1) bestOne = randomIndividual;
            
            if(results[randomIndividual] > results[bestOne]) bestOne = randomIndividual;
            
        }
        return bestOne;
        
    }
    private static void showResult(int[][] chromosomes, int[] results, String[] cities) {
            int i, i2;
            i=0;
            for (i2 = 0; i2 < CITY_COUNT; i2++) {
                    System.out.print(cities[chromosomes[i][i2]] + " --> ");
            }
            System.out.print(cities[chromosomes[i][0]] + " ");
            System.out.println(" Result: " + results[i]);
            System.out.println("\n");


    }

    public static void renovateChromosomes(int[][] chromosomes, int[] results, float mortalityRate,int[] parents) {

            int excludedStart = (int) (mortalityRate * 10);

            int i, i2 = 0;

            for (i = excludedStart; i < POPULATION; i++) {

                    boolean valid = false;

                    while (!valid) {

                            int[] temp_chromosome = resetChromosome();

                            // pegando 2 pais aleatoriamente
                            int father1, father2;
                            father1 = new Random().nextInt(excludedStart);
                            father1 = parents[0];
                            father2 = parents[1];
                            /*
                            do {
                                    father2 = new Random().nextInt(excludedStart);
                            } while ((father1 == father2)
                                            && (results[father1] != results[father2]));
                            */

                            // pegando 3 caracteristicas do pai 1 aleatoriamente
                            for (i2 = 0; i2 < (int) CITY_COUNT / 4; i2++) {
                                    int pos;
                                    pos = new Random().nextInt(CITY_COUNT);
                                    temp_chromosome[pos] = chromosomes[father1][pos];
                            }
                            // pegando restante do pai 2
                            for (i2 = 0; i2 < (int) CITY_COUNT / 4; i2++) {
                                    int pos = new Random().nextInt(CITY_COUNT);
                                    if (temp_chromosome[pos] == -1) {
                                            if (GeneticAlgorithm.validatedValueOnChromosome(chromosomes[father2][pos],
                                                            temp_chromosome)) {
                                                    temp_chromosome[pos] = chromosomes[father2][pos];
                                            }
                                    }
                            }

                            // preenchendo o restante com aleatorios
                            for (i2 = 0; i2 < CITY_COUNT; i2++) {
                                    if (temp_chromosome[i2] == -1) {
                                            int crom_temp = validatedValueOnChromosome(temp_chromosome);
                                            temp_chromosome[i2] = crom_temp;
                                    }
                            }

                            // verificando se é valido
                            valid = chromosomeValidation(temp_chromosome, chromosomes);
                            if (valid) {
                                    chromosomes[i] = temp_chromosome;
                            }
                    }
            }

    }
    
    
    // preenche a população com valores aleatórios não repetidos
    private static int[][] generateChromosomesRandomly(int[][] chromosomes) {

            // inicializando cromossomos aleatoriamente
            int[] temp_chromosome = new int[CITY_COUNT];

            
            // percorre toda a população
            for (int i = 0; i < POPULATION; i++) {
                    boolean valid_chromosome = false;
                    //equanto cromossomo não for valido
                    while (!valid_chromosome) {
                            valid_chromosome = true;
                            //preenche cromossomo com -1
                            temp_chromosome = resetChromosome();

                            // gerando cromossomo - ok
                            // preenche cromossomo com valores aleatórios que não existem nele.
                            for (int i2 = 0; i2 < CITY_COUNT; i2++) {
                                    temp_chromosome[i2] = validatedValueOnChromosome(temp_chromosome);
                            }
                            // checa se existe um cromossomo igual ao criado, na população de cromossomos
                            valid_chromosome = chromosomeValidation(temp_chromosome, chromosomes);
                    }
                    chromosomes[i] = temp_chromosome;
            }
            return chromosomes;
    }

    //preenche um cromossomo (vetor com uma solução para o problema do caixeiro viajante) com -1
    private static int[] resetChromosome() {
            int[] c = new int[CITY_COUNT];
            for (int i = 0; i < CITY_COUNT; i++) {
                    c[i] = -1;
            }
            return c;
    }

    //cria um valor inteiro aleatório e checa se ele existe no cromossomo (vetor) passado por parametro;
    private static int validatedValueOnChromosome(int[] temporaryChromosome) {
            int temp_chromosome;
            boolean valid;
            do {
                    temp_chromosome = new Random().nextInt(CITY_COUNT);
                    valid = true;
                    for (int ii = 0; ii < CITY_COUNT; ii++) {
                            if (temporaryChromosome[ii] == temp_chromosome)
                                    valid = false;
                    }
            } while (!valid);
            return temp_chromosome;
    }

    private static boolean validatedValueOnChromosome(int value, int[] temporaryChromosome) {
            int temp_chromosome = value;
            boolean valid;

            valid = true;
            for (int ii = 0; ii < CITY_COUNT; ii++) {
                    if (temporaryChromosome[ii] == temp_chromosome)
                            valid = false;
            }

            return valid;
    }
    
    //checa se um cromossomo igual já existe na população
    private static boolean chromosomeValidation(int[] temporary_chromosome, int[][] chromosomePopulation) {

            boolean valid_chromosome = true;

            for (int j = 0; j < POPULATION; j++) {
                    int equalCounter = 0;
                    for (int j2 = 0; j2 < CITY_COUNT; j2++) {
                            if (temporary_chromosome[j2] == chromosomePopulation[j][j2]) {
                                    equalCounter++;
                            }
                    }

                    if (equalCounter == CITY_COUNT)
                            valid_chromosome = false;
            }
            return valid_chromosome;
    }

    private static void printEvolution(int[][] chromosomes, int[] results, String[] cities) {
            for (int i = 0; i < POPULATION; i++) {
                    for (int i2 = 0; i2 < CITY_COUNT; i2++) {
                            System.out.print(cities[chromosomes[i][i2]] + " --> ");
                    }
                    System.out.print(cities[chromosomes[i][0]] + " ");
                    System.out.println(" Results: " + results[i]);
            }
    }

    private static void calculateResults(int[][] chromosomePopulation, int[] results, int[][] map) {
            int i, i2;
            // calculando o resultado
            for (i = 0; i < POPULATION; i++) {
                    int tempResult = 0;
                    for (i2 = 0; i2 < CITY_COUNT - 1; i2++) {
                            tempResult += map[chromosomePopulation[i][i2]][chromosomePopulation[i][i2 + 1]];
                    }
                    tempResult+=map[chromosomePopulation[i][0]][chromosomePopulation[i][i2]];
                    results[i] = tempResult;
            }

    }

    private static void orderResults(int[][] chromosomesPopulation, int[] results) {
            // ordenando
            int i, i2;
            for (i = 0; i < POPULATION; i++) {
                    for (i2 = i; i2 < POPULATION; i2++) {
                            if (results[i] > results[i2]) {
                                    int vTmp;
                                    int[] vvTmp = new int[10];
                                    vTmp = results[i];
                                    results[i] = results[i2];
                                    results[i2] = vTmp;

                                    vvTmp = chromosomesPopulation[i];
                                    chromosomesPopulation[i] = chromosomesPopulation[i2];
                                    chromosomesPopulation[i2] = vvTmp;
                            }
                    }
            }
    }

    // nomeia as cidades de acordo com o índice
    private static String[] nameCities (int cityNumber) {
       
        String[] cityNames = new String[cityNumber];
        
        for(int i = 0; i < cityNumber; i++) {
            cityNames[i] = "city " + (i + 1);
        }
        
        return cityNames;
    }




    private static class ProbabilitySizeException extends Throwable{

        public ProbabilitySizeException() {
        }
    }
}
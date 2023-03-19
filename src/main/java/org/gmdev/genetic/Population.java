package org.gmdev.genetic;

public class Population {
	
	private final Individual[] individuals;
	
	/**
	 * Constructors - create a population
	 */
    public Population(int populationSize, boolean initialize) {
        this.individuals = new Individual[populationSize];
        
        // Initialise population
        if (initialize) {
            // Loop and create individuals
            for (int i = 0; i < this.size(); i++) {
                Individual newIndividual = new Individual();
                newIndividual.generateIndividual();
                saveIndividual(i, newIndividual);
            }
        }
    }
    
    public Individual getIndividual(int index) {
        return individuals[index];
    }
    
    public void saveIndividual(int index, Individual individual) {
        individuals[index] = individual;
    }
    
    public Individual getFittest() {
    	Individual fittest = individuals[0];
    	
    	for (int i = 0; i < size(); i++) {
    		if (fittest.getFitness() <= getIndividual(i).getFitness()) {
    			fittest = getIndividual(i);
    		}
    	}
    	
    	return fittest;
    }
    
    public int size() {
        return this.individuals.length;
    }

    
}

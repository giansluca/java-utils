package org.gmdev.genetic;

public class Individual {
	
	private static int defaultGeneLength = 64;
	
    private final byte[] genes = new byte[defaultGeneLength];
    private int fitness = 0;
    
    public byte getGene(int index) {
        return genes[index];
    }
    
    public void setGene(int index, byte value) {
        this.genes[index] = value;
        this.fitness = 0;
    }
    
    /**
     * Create a random individual
     */
    public void generateIndividual() {
        for (int i = 0; i < this.size(); i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }
    
    /**
     * 	Set the genes default length
     *  Use this if you want to create individuals with different gene lengths
     */
    public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }
    
    public int size() {
        return genes.length;
    }
    
    public int getFitness() {
    	if (fitness == 0) {
    		fitness = FitnessCalc.getFitness(this);
    	}
    	
    	return fitness;
    }
    
    @Override
    public String toString() {
        String geneString = "";
        
        for (int i = 0; i < this.size(); i++) {
            geneString += getGene(i);
        }
        
        return geneString;
    }
    
    
}

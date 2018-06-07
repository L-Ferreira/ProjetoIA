package snake.snakeAI.ga;

public abstract class RealVectorIndividual <P extends Problem, I extends RealVectorIndividual> extends Individual<P, I>{
    // TODO
    protected double[] genome;
    
    public RealVectorIndividual(P problem, int size) {
        super(problem);
        // TODO
        genome = new double[size];
        for (int i = 0; i < size; i++) {
            genome[i] = GeneticAlgorithm.random.nextDouble()*2-1;
        }
    }

    public RealVectorIndividual(RealVectorIndividual<P, I> original) {
        super(original);
        // TODO
        genome = new double[original.getNumGenes()];
    }
    
    @Override
    public int getNumGenes() {
        // TODO
        return genome.length;
    }
    
    public double getGene(int index) {

        return genome[index];
    }
    
    public void setGene(int index, double newValue) {
        genome[index] = newValue;
    }

    @Override
    public void swapGenes(RealVectorIndividual other, int index) {
        double aux = genome[index];
        this.setGene(index,other.getGene(index));
        other.setGene(index,aux);
    }
}

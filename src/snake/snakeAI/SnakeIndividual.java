package snake.snakeAI;

import snake.Environment;
import snake.snakeAI.ga.RealVectorIndividual;

public class SnakeIndividual extends RealVectorIndividual<SnakeProblem, SnakeIndividual> {

    private double food;

    public SnakeIndividual(SnakeProblem problem, int size /*TODO?*/) {
        super(problem, size);
        //TODO?
        food = -1;
    }

    public SnakeIndividual(SnakeIndividual original) {
        super(original);
        //TODO
        genome = original.getGenome().clone();
        food = original.getFood();
    }

    @Override
    public double computeFitness() {

        int foods = 0;
        int movs = 0;

        Environment environment = problem.getEnvironment();

        for (int i = 0; i < problem.getNumEvironmentSimulations(); i++) {
            environment.setWeights(genome);
            environment.initialize(i);
            environment.simulate2();
            foods += environment.getFoods();
            movs += environment.getMovs();
        }

        this.fitness = (foods * 1000 + movs)/problem.getNumEvironmentSimulations();

        food = foods/problem.getNumEvironmentSimulations();

        return fitness;
    }

    public double[] getGenome(){
        //TODO
        return genome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nfitness: ");
        sb.append(fitness);
        sb.append("\nfoods: ");
        sb.append(food);
        //TODO
        return sb.toString();
    }

    /**
     *
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(SnakeIndividual i) {
        if( this.fitness > i.getFitness()){
            return 1;
        }
        if( this.fitness == i.getFitness()){
            return 0;
        }
        return -1;
    }

    @Override
    public SnakeIndividual clone() {
        return new SnakeIndividual(this);
    }

    public double getFood() {
        return food;
    }
}

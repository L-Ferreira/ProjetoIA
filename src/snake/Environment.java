package snake;

import snake.snakeAI.nn.SnakeAIAgent;
import snake.snakeAI.nn.SnakeAIAgent2;
import snake.snakeAdhoc.SnakeAdhocAgent;
import snake.snakeRandom.SnakeRandomAgent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Environment {

    public Random random;
    private final Cell[][] grid;
    private final ArrayList<SnakeAgent> agents;
    private Food food;
    private final int maxIterations;
    private int snakeType;
    private int foods,movs;
    private double[] weights;

    public Environment(
            int size,
            int maxIterations) {

        this.maxIterations = maxIterations;

        this.grid = new Cell[size][size];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }

        this.agents = new ArrayList<>();
        this.random = new Random();

        this.foods = 0;
        this.movs = 0;


    }

    public void initialize(int seed) {
        
        foods = 0;
        movs = 0;
        for(SnakeAgent agent: agents){
            for(Cell cell:agent.getTail()){
                cell.setTail(false);
            }
            agent.cell.setAgent(null);
        }
        agents.clear();

        if(food != null){
            food.getCell().setFood(null);
            food = null;
        }

        random.setSeed(seed);
        // place food
        placeFood();
        placeAgents();

    }

    // TODO MODIFY TO PLACE ADHOC OR AI SNAKE AGENTS
    private void placeAgents() {

        SnakeAgent snakeAgent = null;
        SnakeAgent snakeAgent2 = null;


        switch (snakeType){
            case 1:
                snakeAgent = new SnakeAdhocAgent(new Cell(random.nextInt(grid.length), random.nextInt(grid.length)), Color.blue);
                break;
            case 2:
                snakeAgent = new SnakeAIAgent(new Cell(random.nextInt(grid.length), random.nextInt(grid.length)),9,8,4);
                ((SnakeAIAgent)snakeAgent).setWeights(weights);
                break;
            case 3:
                snakeAgent = new SnakeAIAgent2(new Cell(random.nextInt(grid.length), random.nextInt(grid.length)),9,8,4);
                ((SnakeAIAgent2)snakeAgent).setWeights(weights);
                break;
            case 4:
                snakeAgent = new SnakeAIAgent(new Cell(random.nextInt(grid.length), random.nextInt(grid.length)),9,8,4);
                snakeAgent2 = new SnakeAIAgent(new Cell(random.nextInt(grid.length), random.nextInt(grid.length)),9,8,4);
                ((SnakeAIAgent)snakeAgent).setWeights(weights);
                ((SnakeAIAgent)snakeAgent2).setWeights(weights);
                break;
            case 5:
                snakeAgent = new SnakeAIAgent(new Cell(random.nextInt(grid.length), random.nextInt(grid.length)),9,8,4);
                snakeAgent2 = new SnakeAIAgent(new Cell(random.nextInt(grid.length), random.nextInt(grid.length)),9,8,4);
                double[] peso1 = Arrays.copyOfRange(weights,0,weights.length/2);
                double[] peso2 = Arrays.copyOfRange(weights,weights.length/2,weights.length);

                ((SnakeAIAgent)snakeAgent).setWeights(peso1);
                ((SnakeAIAgent)snakeAgent2).setWeights(peso2);
                break;
            default:
                snakeAgent = new SnakeRandomAgent(new Cell(random.nextInt(grid.length), random.nextInt(grid.length)), Color.GREEN);
        }
        agents.add(snakeAgent);//
        if(snakeAgent2 != null){
            agents.add(snakeAgent2);//

        }
    }

    protected void placeFood() {
        Cell cell=null;

        do{
            cell=getCell(random.nextInt(getNumLines()), random.nextInt(getNumColumns()));
        }while(cell.hasAgent() ||  cell.hasTail() || cell.hastFood());
        food = new Food(cell);
        food.setCell(cell);

    }



    public void simulate() {
        for (int i = 0; i < maxIterations; i++) {
            for (SnakeAgent agent : agents) {
                if(agent.isDead()){
                    return;
                }
                agent.act(this);
                fireUpdatedEnvironment();
            }
        }
    }

    public void simulate2() {
        foods = 0;
        movs = 0;
        for (int i = 0; i < maxIterations; i++) {
            for (SnakeAgent agent : agents) {
                agent.act(this);
                if(agent.isDead()){
                    movs = i;
                    for(SnakeAgent a: agents){
                        foods += a.getTail().size();
                    }
                    return;
                }
            }
        }
    }

    public int getSize() {
        return grid.length;
    }

    public Cell getNorthCell(Cell cell) {
        if (cell.getLine() > 0) {
            return grid[cell.getLine() - 1][cell.getColumn()];
        }
        return null;
    }

    public Cell getSouthCell(Cell cell) {
        if (cell.getLine() < grid.length - 1) {
            return grid[cell.getLine() + 1][cell.getColumn()];
        }
        return null;
    }

    public Cell getEastCell(Cell cell) {
        if (cell.getColumn() < grid[0].length - 1) {
            return grid[cell.getLine()][cell.getColumn() + 1];
        }
        return null;
    }

    public Cell getWestCell(Cell cell) {
        if (cell.getColumn() > 0) {
            return grid[cell.getLine()][cell.getColumn() - 1];
        }
        return null;
    }

    public int getNumLines() {
        return grid.length;
    }

    public int getNumColumns() {
        return grid[0].length;
    }

    public final Cell getCell(int linha, int coluna) {
        return grid[linha][coluna];
    }

    public Color getCellColor(int linha, int coluna) {
        return grid[linha][coluna].getColor();
    }

    //listeners
    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }

    public Cell getFood() {
        return food.getCell();
    }

    public void setSnakeType(int snakeType) {
        this.snakeType = snakeType;
    }


    public int getFoods() {
        return foods;
    }

    public int getMovs() {
        return movs;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }
}

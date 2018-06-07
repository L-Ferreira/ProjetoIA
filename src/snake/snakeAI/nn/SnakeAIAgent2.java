package snake.snakeAI.nn;

import snake.Action;
import snake.Cell;
import snake.Perception;
import snake.SnakeAgent;

import java.awt.*;

public class SnakeAIAgent2 extends SnakeAgent {

    final private int inputLayerSize;
    final private int hiddenLayerSize;
    final private int outputLayerSize;

    /**
     * Network inputs array.
     */
    final private int[] inputs;
    /**
     * Hiddden layer weights.
     */
    final private double[][] w1;
    /**
     * Output layer weights.
     */
    final private double[][] w2;
    /**
     * Hidden layer activation values.
     */
    final private double[] hiddenLayerOutput;
    /**
     * Output layer activation values.
     */
    final private double[] output;

    public SnakeAIAgent2(
            Cell cell,
            int inputLayerSize,
            int hiddenLayerSize,
            int outputLayerSize) {
        super(cell, Color.BLUE);
        this.inputLayerSize = inputLayerSize;
        this.hiddenLayerSize = hiddenLayerSize;
        this.outputLayerSize = outputLayerSize;
        inputs = new int[inputLayerSize];
        inputs[inputs.length - 1] = -1; //bias entry
        w1 = new double[inputLayerSize][hiddenLayerSize]; // the bias entry for the hidden layer neurons is already counted in inputLayerSize variable
        w2 = new double[hiddenLayerSize + 1][outputLayerSize]; // + 1 due to the bias entry for the output neurons
        hiddenLayerOutput = new double[hiddenLayerSize + 1];
        hiddenLayerOutput[hiddenLayerSize] = -1; // the bias entry for the output neurons
        output = new double[outputLayerSize];
    }

    /**
     * Initializes the network's weights
     * 
     * @param weights vector of weights comming from the individual.
     */
    public void setWeights(double[] weights) {
        // TODO
        int k = 0;
        for (int i = 0;i< inputLayerSize; i++) {
            for (int j = 0; j < hiddenLayerSize;j++) {
                w1[i][j] = weights[k++];
            }
        }
        for (int i = 0;i< hiddenLayerSize+1; i++) {
            for (int j = 0; j < outputLayerSize;j++) {
                w2[i][j] = weights[k++];
            }
        }
    }
    
    /**
     * Computes the output of the network for the inputs saved in the class
     * vector "inputs".
     *
     */
    private void forwardPropagation() {
        double somaPesada;
        //w1 = new double[inputLayerSize][hiddenLayerSize];
        for (int i = 0; i < hiddenLayerSize; i++) {         //percorre os neruronios da camada escondida
            somaPesada = 0.0;
            for (int j = 0; j < inputLayerSize; j++) {      //percorre os inputs
                somaPesada += (inputs[j] * w1[j][i]);
            }
            //sigmoide function
            hiddenLayerOutput[i] = 1 / (1 + Math.exp(-somaPesada));
        }

        //w2 = new double[hiddenLayerSize + 1][outputLayerSize];
        for (int i = 0; i < outputLayerSize; i++) {             //percorre os neruronios da camada de saida
            somaPesada = 0.0;
            for (int j = 0; j < hiddenLayerSize + 1; j++) {     //percorre os outputs da hidden layer
                somaPesada += hiddenLayerOutput[j] * w2[j][i];
            }
            //sigmoide function
            output[i] = somaPesada;    //passar a double
        }
    }

    @Override
    protected Action decide(Perception perception) {


        for(int i = 0;i < inputLayerSize -1; i++){
            inputs[i]=0;
        }

        if(perception.getS()==null || perception.getS().hasTail() || perception.getS().hasAgent()){
            inputs[0]=1;
        }
        if(perception.getN()==null || perception.getN().hasTail() || perception.getN().hasAgent()){
            inputs[1]=1;
        }
        if(perception.getE()==null || perception.getE().hasTail() || perception.getE().hasAgent()){
            inputs[2]=1;
        }
        if(perception.getW()==null || perception.getW().hasTail() || perception.getW().hasAgent()){
            inputs[3]=1;
        }

        if(cell.getLine()<environment.getFood().getLine()) {
            inputs[4]= Math.abs(cell.getLine()-environment.getFood().getLine());
        }

        if(cell.getLine()>environment.getFood().getLine()) {
            inputs[5]=-Math.abs(cell.getLine()-environment.getFood().getLine());
        }

        if(cell.getColumn()<environment.getFood().getColumn()) {
            inputs[6]=Math.abs(cell.getColumn()-environment.getFood().getColumn());
        }

        if(cell.getColumn()>environment.getFood().getColumn()) {
            inputs[7]=-Math.abs(cell.getColumn()-environment.getFood().getColumn());
        }

        forwardPropagation();

        int indice = 0;
        double maximo = Double.MIN_VALUE;
        for (int i = 1; i < outputLayerSize; i++) {
            if(output[i]>maximo){
                maximo=output[i];
                indice = i;
            }
        }

        switch(indice){
            case 1:
                return Action.NORTH;
            case 2:
                return  Action.EAST;
            case 3:
                return Action.WEST;
            default :
                return Action.SOUTH;

        }
    }
}

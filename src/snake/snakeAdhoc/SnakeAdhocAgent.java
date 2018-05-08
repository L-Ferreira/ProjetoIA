package snake.snakeAdhoc;

import snake.*;

import java.awt.*;


public class SnakeAdhocAgent extends SnakeAgent {
    // TODO

    public SnakeAdhocAgent(Cell cell, Color color) {
        super(cell, color);
    }

    @Override
    protected Action decide(Perception perception) {
        if(perception.getS()!=null &&
                !perception.getS().hasAgent() && !perception.getS().hasTail()  && cell.getLine()<environment.getFood().getLine()) {
            return Action.SOUTH;
        }

        if(perception.getN()!=null &&
                !perception.getN().hasAgent() && !perception.getN().hasTail() && cell.getLine()>environment.getFood().getLine()) {
            return Action.NORTH;
        }

        if(perception.getE()!=null &&
                !perception.getE().hasAgent() && !perception.getE().hasTail() && cell.getColumn()<environment.getFood().getColumn()) {
            return Action.EAST;
        }

        if(perception.getW()!=null &&
                !perception.getW().hasAgent() && !perception.getW().hasTail()&& cell.getColumn()>environment.getFood().getColumn()) {
            return Action.WEST;
        }


        if(perception.getS()!=null && !perception.getS().hasTail()){
            return Action.SOUTH;
        }else if(perception.getN()!=null  && !perception.getN().hasTail()){
            return Action.NORTH;
        }else if(perception.getE()!=null && !perception.getE().hasTail()){
            return Action.EAST;
        }else if(perception.getW()!=null && !perception.getW().hasTail()){
            return Action.WEST;
        }

        return null;
    }

}

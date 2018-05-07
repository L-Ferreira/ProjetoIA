package snake.snakeRandom;

import snake.*;

import java.awt.*;

public class SnakeRandomAgent extends SnakeAgent {
    // TODO

    public SnakeRandomAgent(Cell cell, Color color) {
        super(cell, color);
    }

    @Override
    protected Action decide(Perception perception) {
        if(perception.getS()!=null && perception.getS().hastFood() && !perception.getS().hasTail()){
            return Action.SOUTH;
        }else if(perception.getN()!=null &&
                perception.getN().hastFood() && !perception.getN().hasTail()){
            return Action.NORTH;
        }else if(perception.getE()!=null &&
                perception.getE().hastFood() && !perception.getE().hasTail()){
            return Action.EAST;
        }else if(perception.getW()!=null && perception.getW().hastFood() && !perception.getW().hasTail()){
            return Action.WEST;
        }

        int numVisits[]=new int[4];
        int pos;
        if(perception.getN()!=null &&
                !perception.getN().hasAgent() && !perception.getN().hasTail()) {
            pos = visitedCells.indexOf(perception.getN());
            if (pos == -1) {
                numVisits[0] = 0;
            } else {
                numVisits[0] = numVisitsCell.get(pos);
            }
        }else{
            numVisits[0]=Integer.MAX_VALUE;
        }

        if(perception.getS()!=null &&
                !perception.getS().hasAgent() && !perception.getS().hasTail()) {
            pos = visitedCells.indexOf(perception.getS());
            if (pos == -1) {
                numVisits[1] = 0;
            } else {
                numVisits[1] = numVisitsCell.get(pos);
            }
        }else{
            numVisits[1]=Integer.MAX_VALUE;
        }

        if(perception.getW()!=null &&
                !perception.getW().hasAgent() && !perception.getW().hasTail()){
            pos = visitedCells.indexOf(perception.getW());
            if (pos == -1) {
                numVisits[2] = 0;
            } else {
                numVisits[2] = numVisitsCell.get(pos);
            }
        }else{
            numVisits[2]=Integer.MAX_VALUE;
        }

        if(perception.getE()!=null &&
                !perception.getE().hasAgent() && !perception.getE().hasTail()) {
            pos = visitedCells.indexOf(perception.getE());
            if (pos == -1) {
                numVisits[3] = 0;
            } else {
                numVisits[3] = numVisitsCell.get(pos);
            }
        }else{
            numVisits[3]=Integer.MAX_VALUE;
        }

        int posMenor=0;
        int menor=numVisits[0];
        for (int i = 1; i < numVisits.length; i++) {
            if(numVisits[i]<menor){
                menor=numVisits[i];
                posMenor=i;
            }
        }

        switch (posMenor){
            case 0:
                return Action.NORTH;
            case 1:
                return Action.SOUTH;
            case 2:
                return Action.WEST;
            case 3:
                return Action.EAST;
        }

        return null;
    }
}

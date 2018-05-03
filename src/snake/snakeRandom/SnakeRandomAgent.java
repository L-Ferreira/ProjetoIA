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
        if (perception.getN() != null && !perception.getN().hasAgent()&& !visitedCells.contains(perception.getN())) {
            return Action.NORTH;
        }
        if (perception.getE() != null && !perception.getE().hasAgent()&& !visitedCells.contains(perception.getE())) {
            return Action.EAST;
        }
        if (perception.getS() != null && !perception.getS().hasAgent()&& !visitedCells.contains(perception.getS())) {
            return Action.SOUTH;
        }
        return Action.WEST;
    }
}

package snake;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class SnakeAgent {

    protected Cell cell;
    public LinkedList<Cell> visitedCells;
    public LinkedList<Integer> numVisitsCell;
    private LinkedList<Cell> tail;
    private Boolean killed;
    protected Color color;
    public Environment environment;

    public SnakeAgent(Cell cell, Color color) {
        this.cell = cell;
        if(cell != null){this.cell.setAgent(this);}
        this.color = color;
        this.visitedCells=new LinkedList<>();
        this.numVisitsCell=new LinkedList<>();
        this.tail=new LinkedList<>();
        this.cell.setVisit();
        visitedCells.add(this.cell);
        numVisitsCell.add(1);
        killed=false;
    }

    public void act(Environment environment) {
        this.environment=environment;
        Perception perception = buildPerception(environment);
        Action action = decide(perception);
        execute(action, environment);

    }


    protected Perception buildPerception(Environment environment) {
        return new Perception(
                environment.getNorthCell(cell),
                environment.getSouthCell(cell),
                environment.getEastCell(cell),
                environment.getWestCell(cell));
    }

    protected void execute(Action action, Environment environment)
    {
        Cell nextCell = null;

        if (action == Action.NORTH && cell.getLine() != 0) {
            nextCell = environment.getNorthCell(cell);
        } else if (action == Action.SOUTH && cell.getLine() != environment.getNumLines() - 1) {
            nextCell = environment.getSouthCell(cell);
        } else if (action == Action.WEST && cell.getColumn() != 0) {
            nextCell = environment.getWestCell(cell);
        } else if (action == Action.EAST && cell.getColumn() != environment.getNumColumns() - 1) {
            nextCell = environment.getEastCell(cell);
        }

        if(nextCell == null || nextCell.hasAgent() || nextCell.hasTail()) {
            killed = true;
            return;
        }



        if(nextCell.hastFood()){
            this.tail.add(cell);
            this.cell.setTail(true);
            environment.placeFood();
        }

        if(this.tail.size()>0) {
            Cell c = this.tail.getLast();
            this.tail.remove(c);
            c.setTail(false);
            this.tail.addFirst(this.cell);
            this.cell.setTail(true);
        }
        setCell(nextCell);


    }

    protected abstract Action decide(Perception perception);

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell newCell) {
        if(this.cell != null){this.cell.setAgent(null);}
        this.cell = newCell;
        if(newCell != null){newCell.setAgent(this);}
        if(this.cell.hastFood()) {
            this.cell.setFood(null);
        }
        this.cell.setVisit();
        int pos=visitedCells.indexOf(this.cell);
        if(pos==-1){
            visitedCells.add(this.cell);
            numVisitsCell.add(1);
        }else{
            numVisitsCell.set(pos, numVisitsCell.get(pos)+1);
        }
    }    
    
    public Color getColor() {
        return color;
    }

    public LinkedList<Cell> getTail() {
        return tail;
    }

    public boolean isDead() {
        return killed;
    }
}

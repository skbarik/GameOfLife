package gameoflife;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static gameoflife.CellState.ALIVE;
import static gameoflife.CellState.DEAD;

public class Generation {
    private List<Cell> aliveCells;
    private static final String INPUT_PATTERN_REGULAR_EXPRESSION = "^([0-9]+,[0-9]+( [0-9]+,[0-9]+)*)$";

    private Generation(List<Cell> cells) {
        this.aliveCells = cells;
    }

    public static Generation create(String inputPatternForSeedGeneration) {
        if (inputPatternForSeedGeneration == null
                || inputPatternForSeedGeneration.trim().isEmpty()
                || !Pattern.matches(INPUT_PATTERN_REGULAR_EXPRESSION, inputPatternForSeedGeneration)) {
            throw new InvalidPatternException();
        }

        return new Generation(Arrays.stream(inputPatternForSeedGeneration.split(" "))
                .map(Cell::create)
                .collect(Collectors.toList()));
    }

    public void next() {
        List<Cell> nextGenerationAliveCells = new ArrayList<>();
        List<Cell> processedCells = new ArrayList<>();
        Queue<Cell> stack = new LinkedList<>();
        stack.addAll(aliveCells);
        while(!stack.isEmpty()) {
            Cell cell = stack.poll();
            if(processedCells.contains(cell)) {
                continue;
            }

            List<Cell> neighbors = getNeighbors(cell);

            int numberOfAliveNeighbors = getNumberOfAliveNeighbors(neighbors);
            if(numberOfAliveNeighbors==0) {
                continue;
            }

            stack.addAll(neighbors);

            switch (cell.getState()) {
                case ALIVE:
                    if(!isLonely(numberOfAliveNeighbors)
                            &&!isOverCrowded(numberOfAliveNeighbors)) {
                        nextGenerationAliveCells.add(cell);
                    }
                    break;
                case DEAD:
                    if(canComeToLife(numberOfAliveNeighbors)) {
                        nextGenerationAliveCells.add(cell);
                    }
                    break;
            }

            processedCells.add(cell);

        }

        aliveCells = nextGenerationAliveCells;
    }

    private boolean canComeToLife(int numberOfAliveNeighbors) {
        return numberOfAliveNeighbors==3;
    }

    private boolean isOverCrowded(int numberOfAliveNeighbors) {
        return numberOfAliveNeighbors>3;
    }

    private boolean isLonely(int numberOfAliveNeighbors) {
        return numberOfAliveNeighbors<2;
    }

    private int getNumberOfAliveNeighbors(List<Cell> neighbors) {
        return (int) neighbors.stream().filter(neighbor->neighbor.getState()==ALIVE).count();
    }

    private List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int x = cell.getLocation().getX();
        int y = cell.getLocation().getY();
        x--;
        y--;
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if((x+i)==cell.getLocation().getX()&&(y+j)==cell.getLocation().getY()) {
                    continue;
                }
                Cell neighbor = Cell.create((x+i)+","+(y+j));
                if(!aliveCells.contains(neighbor)) {
                    neighbor.setState(DEAD);
                }
                if(neighbor.getLocation().getX()>=0&&neighbor.getLocation().getY()>=0) {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }

    public List<Cell> getAliveCells() {
        return new ArrayList<>(aliveCells);
    }

}

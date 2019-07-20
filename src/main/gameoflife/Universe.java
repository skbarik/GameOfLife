package gameoflife;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Universe {
    private Generation generation;

    private Universe(Generation generation) {
        this.generation = generation;
    }

    public static Universe create(String inputPatternForSeedGeneration) {
        return new Universe(Generation.create(inputPatternForSeedGeneration));
    }

    public void tick() {
        generation.next();
    }

    public String getCurrentState() {
        List<Cell> aliveCells = generation.getAliveCells();
        Collections.sort(aliveCells);
        return aliveCells.stream()
                .map(cell -> cell.getLocation().getX() + "," + cell.getLocation().getY())
                .collect(Collectors.joining(" "));
    }
}

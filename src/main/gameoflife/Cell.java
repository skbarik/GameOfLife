package gameoflife;

import java.util.Objects;

import static gameoflife.CellState.ALIVE;

public class Cell implements Comparable<Cell> {
    public Location getLocation() {
        return location;
    }

    private Location location;

    public CellState getState() {
        return state;
    }

    public  void setState(CellState state) {
        this.state = state;
    }

    private CellState state = ALIVE;

    private Cell(Location location) {
        this.location = location;
    }

    public static Cell create(String locationCSV) {
        return new Cell(new Location(Integer.parseInt(locationCSV.split(",")[0]), Integer.parseInt(locationCSV.split(",")[1])));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return Objects.equals(location, cell.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, state);
    }

    @Override
    public int compareTo(Cell other) {
        int differenceInX = this.location.getX() - other.location.getX();
        if(differenceInX!=0) {
            return differenceInX;
        }
        return this.location.getY() - other.location.getY();
    }

    @Override
    public String toString() {
        return "Cell{" +
                "location=" + location +
                ", state=" + state +
                '}';
    }
}

package gameoflife;

import org.junit.Test;

import static org.junit.Assert.*;

public class UniverseTest {

    @Test(expected = InvalidPatternException.class)
    public void create_throwsInvalidPatternException_givenUniverseIsInitialisedWithInvalidEmptyPattern() {
        String invalidEmptyPattern = "";

        Universe.create(invalidEmptyPattern);

    }

    @Test(expected = InvalidPatternException.class)
    public void create_throwsInvalidPatternException_givenUniverseIsInitialisedWithInvalidNonEmptyPattern() {
        String invalidNonEmptyPattern = "11,1 2221 22,2333 0,0";

        Universe.create(invalidNonEmptyPattern);

    }

    @Test
    public void create_returnsNewUniverse_givenUniverseIsInitialisedWithValidPattern() {
        String validPattern = "11,1 2,2221 22,2333 0,0 1,2 1,1 1,10";

        Universe universe = Universe.create(validPattern);

        assertEquals("0,0 1,1 1,2 1,10 2,2221 11,1 22,2333",universe.getCurrentState());

    }

    @Test
    public void tick_expectsNextGeneration_givenUniverseIsInitialisedWithBlockPatternStillLife() {
        Universe universe = Universe.create("1,1 1,2 2,1 2,2");

        universe.tick();

        assertEquals("1,1 1,2 2,1 2,2", universe.getCurrentState());

    }

    @Test
    public void tick_expectsNextGeneration_givenUniverseIsInitialisedWithBoatPatternStillLife() {
        Universe universe = Universe.create("0,1 1,0 2,1 0,2 1,2");

        universe.tick();

        assertEquals("0,1 0,2 1,0 1,2 2,1", universe.getCurrentState());

    }

    @Test
    public void tick_expectsNextGeneration_givenUniverseIsInitialisedWithBlinkerPatternOscillatorHorizontal() {
        Universe universe = Universe.create("1,1 1,0 1,2");

        universe.tick();

        assertEquals("0,1 1,1 2,1", universe.getCurrentState());

    }

    @Test
    public void tick_expectsNextGeneration_givenUniverseIsInitialisedWithBlinkerPatternOscillatorVertical() {
        Universe universe = Universe.create("0,1 1,1 2,1");

        universe.tick();

        assertEquals("1,0 1,1 1,2", universe.getCurrentState());

    }

    @Test
    public void tick_expectsNextGeneration_givenUniverseIsInitialisedWithToadPatternTwoPhaseOscillatorPhaseOne() {
        Universe universe = Universe.create("1,1 1,2 1,3 2,2 2,3 2,4");

        universe.tick();

        assertEquals("0,2 1,1 1,4 2,1 2,4 3,3", universe.getCurrentState());

    }

    @Test
    public void tick_expectsNextGeneration_givenUniverseIsInitialisedWithToadPatternTwoPhaseOscillatorPhaseTwo() {
        Universe universe = Universe.create("0,2 1,1 1,4 2,1 2,4 3,3");

        universe.tick();

        assertEquals("1,1 1,2 1,3 2,2 2,3 2,4", universe.getCurrentState());

    }


}
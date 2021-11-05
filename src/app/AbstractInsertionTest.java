package app;

public abstract class AbstractInsertionTest {
    protected double currLat = 39.778259;
    protected double currLong = -105.417931;
    protected int acceptableRange = 1000;

    public abstract void runTests();
}

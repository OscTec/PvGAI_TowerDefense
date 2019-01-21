public class Stopwatch {
    private double start;

    public Stopwatch() {
        start = System.currentTimeMillis();
    }

    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (float) (now - start) / 1000.0;
    }

    void reset() {
        start = System.currentTimeMillis();
    }
}

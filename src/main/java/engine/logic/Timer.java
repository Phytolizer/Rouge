package engine.logic;

public class Timer {
    private double prevTime;
    
    public double tick(double now) {
        double delta = now - prevTime;
        prevTime = now;
        return delta;
    }
}

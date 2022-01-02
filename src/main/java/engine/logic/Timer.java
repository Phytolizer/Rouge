package engine.logic;

/**
 * A {@code Timer} object is used to store the time difference between
 * one frame and the next, the Time Delta, this data is necessary
 * for making sure the movement of objects stays the same regardless
 * of the client's framerate.
 */
public class Timer {
    private double prevTime;
    
    public double tick(double now) {
        double delta = now - prevTime;
        prevTime = now;
        return delta;
    }
}

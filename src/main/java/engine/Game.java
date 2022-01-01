package engine;

import engine.graphics.Window;

public interface Game {
    void runFrame(double deltaTime, boolean[] keyStates, double[] cursorPos, boolean[] buttonStates, Window window);
}

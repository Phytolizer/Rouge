package engine;

import engine.graphics.Window;
import engine.logic.Mouse;

public interface Game {
    void runFrame(double deltaTime, boolean[] keyStates, Mouse mouse, Window window);
}

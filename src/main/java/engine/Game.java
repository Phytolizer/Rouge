package engine;

import engine.graphics.Window;
import engine.logic.Mouse;
import engine.logic.Keyboard;

public interface Game {
    void runFrame(double deltaTime, Keyboard keyboard, Mouse mouse, Window window);
}

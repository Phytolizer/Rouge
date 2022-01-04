package engine;

import engine.graphics.Window;
import engine.logic.Mouse;
import engine.logic.Keyboard;

public abstract class Game {
    public abstract void runFrame(double deltaTime, Keyboard keyboard, Mouse mouse, Window window);
}

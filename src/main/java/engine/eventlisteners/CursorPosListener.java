package engine.eventlisteners;

import engine.core.Coords;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

/**
 * the {@code CursorPosListener} class listens for the
 * cursor position and stores it in its own internal
 * state.
 */
public class CursorPosListener implements GLFWCursorPosCallbackI {
    private final Coords.Position position;

    public CursorPosListener() {
        position = new Coords.Position(0f, 0f, 0f);
    }

    @Override
    public void invoke(long window, double xPos, double yPos) {
        position.setX((float) xPos);
        position.setY((float) yPos);
    }


    public Coords.Position getPosition() {
        return (Coords.Position) position.clone();
    }
}

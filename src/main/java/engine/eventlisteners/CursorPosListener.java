package engine.eventlisteners;

import engine.core.Position;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

/**
 * the {@code CursorPosListener} class listens for the
 * cursor position and stores it in its own internal
 * state.
 */
public class CursorPosListener implements GLFWCursorPosCallbackI {
    private final Position position;

    public CursorPosListener() {
        position = new Position(0f, 0f, 0f);
    }

    @Override
    public void invoke(long window, double xPos, double yPos) {
        position.setX((float) xPos);
        position.setY((float) yPos);
    }


    public Position getPosition() {
        return (Position) position.clone();
    }
}

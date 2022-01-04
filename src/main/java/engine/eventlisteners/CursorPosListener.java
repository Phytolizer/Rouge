package engine.eventlisteners;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;

/**
 * the {@code CursorPosListener} class listens for the
 * cursor position and stores it in its own internal
 * state.
 */
public class CursorPosListener implements GLFWCursorPosCallbackI {
    private float xPos;
    private float yPos;

    public CursorPosListener() {
    }

    @Override
    public void invoke(long window, double xPos, double yPos) {
        this.xPos = (float) xPos;
        this.yPos = (float) yPos;
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }
}

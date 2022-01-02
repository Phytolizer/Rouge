package engine.eventlisteners;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;

/**
 * the {@code CursorPosListener} class listens for the
 * cursor position and stores it in its own internal
 * state.
 */
public class CursorPosListener implements GLFWCursorPosCallbackI {
    private double xPos;
    private double yPos;

    public CursorPosListener() {
    }

    @Override
    public void invoke(long window, double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }
}

package engine.eventlisteners;

import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * The {@code MouseButtonListener} class listens for the
 * mouse button events and stores the state of each one
 * in its own internal state.
 */
public class MouseButtonListener implements GLFWMouseButtonCallbackI {
    private final boolean[] buttonStates;

    public MouseButtonListener() {
        buttonStates = new boolean[8];
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            this.buttonStates[button] = true;
        } else if (action == GLFW_RELEASE) {
            this.buttonStates[button] = false;
        }
    }

    public boolean getButtonState(int button) {
        return buttonStates[button];
    }
}

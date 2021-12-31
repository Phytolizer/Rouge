package engine.eventlisteners;

import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener implements GLFWKeyCallbackI {
    private final boolean[] keyStates;

    public KeyListener() {
        keyStates = new boolean[350];
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_RELEASE) {
            keyStates[key] = false;
        } else if (action == GLFW_PRESS) {
            keyStates[key] = true;
        }
    }

    public boolean[] getAllKeyStates() {
        return keyStates;
    }
}




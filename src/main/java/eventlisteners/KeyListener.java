package eventlisteners;

import java.util.NoSuchElementException;

import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener implements GLFWKeyCallbackI {
    private final boolean[] keyStates;
    
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key > keyStates.length) {
            throw new NoSuchElementException();
        }
    
        if (action == GLFW_RELEASE) {
            keyStates[key] = false;
        }
        if (action == GLFW_PRESS) {
            keyStates[key] = true;
        }
    }
    
    public KeyListener() {
        keyStates = new boolean[350];
    }
    
    public boolean getKeyState(int aKeyValue) {
        if (aKeyValue > keyStates.length) {
            throw new NoSuchElementException();
        }
        return keyStates[aKeyValue];
    }
    
    public void setKeyState(int aKeyValue, boolean aKeyState) {
        if (aKeyValue > keyStates.length) {
            throw new NoSuchElementException();
        }
        keyStates[aKeyValue] = aKeyState;
    }
}




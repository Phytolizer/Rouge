package engine.logic;
import static org.lwjgl.glfw.GLFW.*;

/**
 * The {@code Keyboard} class is an abstraction for representing
 * the state of the users keyboard, this is separate from the key
 * listener for the sake of simplicity.
 */
public class Keyboard {
    private boolean[] keyStates;

    public Keyboard() {
        keyStates = new boolean[350];
    }

    public void setKeyStates(boolean[] stateArray) {
        this.keyStates = stateArray;
    }

    public boolean getKeyPressed(int key) {
        return keyStates[key];
    }
}

package engine.logic;
import engine.core.Position;

/**
 * The {@code Mouse} class is an abstraction for representing
 * the state of the users mouse, this is separate from the mouse
 * listeners for simplicity's sake.
 */
public class Mouse {
    public final Position cursorPosition;
    private boolean[] buttonStates;

    public Mouse() {
        cursorPosition = new Position(0f, 0f, 0f);
        buttonStates = new boolean[8];
    }

    public boolean getLeftButtonState() {
        return buttonStates[0];
    }

    public boolean getRightButtonState() {
        return buttonStates[1];
    }

    public boolean getScrollWheelButtonState() {
        return buttonStates[2];
    }

    public void setButtonStates(boolean[] stateArray) {
        buttonStates = stateArray;
    }

}

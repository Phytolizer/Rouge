package engine.eventlisteners;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    public static class ButtonListener implements GLFWMouseButtonCallbackI {
        private final boolean[] buttonStates;

        public ButtonListener() {
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

        public boolean[] getAllButtonStates() {
            return this.buttonStates;
        }
    }

    public static class CursorPosListener implements GLFWCursorPosCallbackI {
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
}

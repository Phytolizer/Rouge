import engine.eventlisteners.Mouse;
import engine.graphics.Window;
import engine.eventlisteners.KeyListener;
import engine.logic.Clock;
import engine.logic.Entity;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) {
        var mainWindow = new Window();
        var rougeTriangle = new Entity();
        
        mainWindow.setClock(new Clock());
        mainWindow.setMouseCursorPosListener(new Mouse.CursorPosListener());
        mainWindow.setMouseButtonListener(new Mouse.ButtonListener());
        mainWindow.setKeyListener(new KeyListener());

        mainWindow.run((timeDelta, allKeyStates, mouseCursorXYPos, allMouseButtonStates) -> {
            float timeConstant = (float) ((1 / timeDelta) / 60);
            
            if (allKeyStates[GLFW_KEY_D]) {
                rougeTriangle.increaseX(0.01f * timeConstant);
            }
            if (allKeyStates[GLFW_KEY_A]) {
                rougeTriangle.increaseX(-0.01f * timeConstant);
            }
            if (allKeyStates[GLFW_KEY_W]) {
                rougeTriangle.increaseY(0.01f * timeConstant);
            }
            if (allKeyStates[GLFW_KEY_S]) {
                rougeTriangle.increaseY(-0.01f * timeConstant);
            }

            float x = rougeTriangle.getX();
            float y = rougeTriangle.getY();

            mainWindow.drawTriangle(x, y, 0);
            
        });
    }
}

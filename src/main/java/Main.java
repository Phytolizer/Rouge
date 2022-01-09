import engine.Drawer;
import engine.eventlisteners.CursorPosListener;
import engine.eventlisteners.MouseButtonListener;
import engine.Window;
import engine.eventlisteners.KeyListener;
import engine.logic.Timer;
import engine.logic.entities.Rectangle;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;

public class Main {
    public static void main(String[] args) {
        var mainWindow = new Window();
        mainWindow.setTimer(new Timer());
        mainWindow.setCursorPosListener(new CursorPosListener());
        mainWindow.setMouseButtonListener(new MouseButtonListener());
        mainWindow.setKeyListener(new KeyListener());

        var mainDrawer = new Drawer(mainWindow);
        var rougeRectangle = new Rectangle(0.4f,0.4f, 0f, 0f, 0f, mainDrawer);

        rougeRectangle.setTexture("assets/sprites/abooga.png");

        while(!mainWindow.shouldClose()) {
            mainWindow.pollEvents();

            if (mainWindow.getKeyPressed(GLFW_KEY_D)) {
                rougeRectangle.moveBy(0.01f, 0f, 0f);
            }
            if (mainWindow.getKeyPressed(GLFW_KEY_A)) {
                rougeRectangle.moveBy(-0.01f, 0f, 0f);
            }
            if (mainWindow.getKeyPressed(GLFW_KEY_W))   {
                rougeRectangle.moveBy(0f, 0.01f, 0f);
            }
            if (mainWindow.getKeyPressed(GLFW_KEY_S)) {
                rougeRectangle.moveBy(0f, -0.01f, 0f);
            }
            if(mainWindow.getKeyPressed(GLFW_KEY_UP)) {
                rougeRectangle.moveBy(0f, 0f, 0.01f);
            }
            if(mainWindow.getKeyPressed(GLFW_KEY_DOWN)) {
                rougeRectangle.moveBy(0f, 0f, -0.01f);
            }

            rougeRectangle.draw();

            mainWindow.update();
        }
        mainWindow.destroy();
    }
}

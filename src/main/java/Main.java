import engine.Artist;
import engine.core.Coords;
import engine.eventlisteners.CursorPosListener;
import engine.eventlisteners.MouseButtonListener;
import engine.Window;
import engine.eventlisteners.KeyListener;
import engine.graphics.FrameTimer;
import engine.entities.Rectangle;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) {
        var mainWindow = new Window();
        mainWindow.setTimer(new FrameTimer());
        mainWindow.setCursorPosListener(new CursorPosListener());
        mainWindow.setMouseButtonListener(new MouseButtonListener());
        mainWindow.setKeyListener(new KeyListener());

        Coords.setAspectRatio(mainWindow.getAspectRatio());

        var mainDrawer = new Artist(mainWindow);
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

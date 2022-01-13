import engine.Artist;
import engine.core.Coords;
import engine.eventlisteners.CursorPosListener;
import engine.eventlisteners.MouseButtonListener;
import engine.Window;
import engine.eventlisteners.KeyListener;
import engine.graphics.FrameTimer;
import engine.entities.Rectangle;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) throws IOException {
        var mainWindow = new Window();
        var position = 0;
        mainWindow.setTimer(new FrameTimer());
        mainWindow.setCursorPosListener(new CursorPosListener());
        mainWindow.setMouseButtonListener(new MouseButtonListener());
        mainWindow.setKeyListener(new KeyListener());

        Coords.setAspectRatio(mainWindow.getAspectRatio());

        var mainDrawer = new Artist(mainWindow);
        float tempPixelSize = 0.0032345f;
        var rougeRectangle = new Rectangle(tempPixelSize * 500,tempPixelSize * 250, 0f, 0f, 0f, mainDrawer);

        rougeRectangle.setTexture("assets/sprites/blue_slime_test_sprite_2.png");

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
            if (rougeRectangle.isHovered(mainWindow)) {
                System.out.print("\rRECTANGLE'S HOVERED!");
            }
            if (!rougeRectangle.isHovered(mainWindow)) {
                System.out.print("\r ");
            }

            rougeRectangle.draw();

            mainWindow.update();
        }
        mainWindow.destroy();
    }
}

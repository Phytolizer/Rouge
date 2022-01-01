import engine.eventlisteners.Mouse;
import engine.graphics.Window;
import engine.eventlisteners.KeyListener;
import engine.logic.Timer;
import engine.logic.Entity;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) {
        var mainWindow = new Window();
        var rouge = new Rouge();
    
        mainWindow.setTimer(new Timer());
        mainWindow.setMouseCursorPosListener(new Mouse.CursorPosListener());
        mainWindow.setMouseButtonListener(new Mouse.ButtonListener());
        mainWindow.setKeyListener(new KeyListener());
        
        mainWindow.run(rouge);
    }
}

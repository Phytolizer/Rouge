import engine.graphics.Window;
import engine.eventlisteners.KeyListener;
import engine.logic.Clock;
import engine.logic.Entity;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) {
        var mainWindow = new Window();
        var mainKeyListener = new KeyListener();
        var rougeTriangle = new Entity();
        
        mainWindow.setClock(new Clock());
        mainWindow.setKeyListener(mainKeyListener);
        mainWindow.run((timeDelta) -> {
            float timeConstant = (float) ((1 / timeDelta) / 60);
            
            if (mainKeyListener.getKeyState(GLFW_KEY_D)) {
                rougeTriangle.increaseX(0.01f * timeConstant);
            }
            if (mainKeyListener.getKeyState(GLFW_KEY_A)) {
                rougeTriangle.increaseX(-0.01f * timeConstant);
            }
            if (mainKeyListener.getKeyState(GLFW_KEY_W)) {
                rougeTriangle.increaseY(0.01f * timeConstant);
            }
            if (mainKeyListener.getKeyState(GLFW_KEY_S)) {
                rougeTriangle.increaseY(-0.01f * timeConstant);
            }
            
            if (mainKeyListener.getKeyState(GLFW_KEY_UP)) {
                rougeTriangle.increaseZ(0.01f * timeConstant);
            }
            
            if (mainKeyListener.getKeyState(GLFW_KEY_DOWN)) {
                rougeTriangle.increaseZ(-0.01f * timeConstant);
            }
            
            float x = rougeTriangle.getX();
            float y = rougeTriangle.getY();
            float z = rougeTriangle.getZ();
            
            mainWindow.drawTriangle(x, y, z);
            
        });
    }
}

import engine.graphics.Window;
import engine.eventlisteners.KeyListener;
import engine.logic.Clock;
import engine.logic.Entity;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) {
        Window.init();
        
        var rougeKeyListener = new KeyListener();
        var rougeTriangle = new Entity();
        
        Window.setClock(Clock::setTime);
        Window.setKeyListener(rougeKeyListener);
        Window.run(() -> {
            double timeDelta = Clock.getDeltaTime();
            float timeConstant = (float) ((1 / timeDelta) / 60);
            
            if (rougeKeyListener.getKeyState(GLFW_KEY_D)) {
                rougeTriangle.increaseX(0.01f * timeConstant);
            }
            if (rougeKeyListener.getKeyState(GLFW_KEY_A)) {
                rougeTriangle.increaseX(-0.01f * timeConstant);
            }
            if (rougeKeyListener.getKeyState(GLFW_KEY_W)) {
                rougeTriangle.increaseY(0.01f * timeConstant);
            }
            if (rougeKeyListener.getKeyState(GLFW_KEY_S)) {
                rougeTriangle.increaseY(-0.01f * timeConstant);
            }
            
            if (rougeKeyListener.getKeyState(GLFW_KEY_UP)) {
                rougeTriangle.increaseZ(0.01f * timeConstant);
            }
            
            if (rougeKeyListener.getKeyState(GLFW_KEY_DOWN)) {
                rougeTriangle.increaseZ(-0.01f * timeConstant);
            }
            
            float x = rougeTriangle.getX();
            float y = rougeTriangle.getY();
            float z = rougeTriangle.getZ();
            
            Window.drawTriangle(x, y, z);
            
        });
    }
}

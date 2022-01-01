import engine.Game;
import engine.graphics.Window;
import engine.logic.Entity;

import static org.lwjgl.glfw.GLFW.*;

public class Rouge implements Game {
    public Entity rougeTriangle = new Entity();
    
    @Override
    public void runFrame(double deltaTime, boolean[] keyStates, double[] cursorPos, boolean[] buttonStates, Window window) {
        float timeConstant = (float) ((1 / deltaTime) / 60);
        
        if (keyStates[GLFW_KEY_D]) {
            rougeTriangle.increaseX(0.01f * timeConstant);
        }
        if (keyStates[GLFW_KEY_A]) {
            rougeTriangle.increaseX(-0.01f * timeConstant);
        }
        if (keyStates[GLFW_KEY_W]) {
            rougeTriangle.increaseY(0.01f * timeConstant);
        }
        if (keyStates[GLFW_KEY_S]) {
            rougeTriangle.increaseY(-0.01f * timeConstant);
        }
        
        float x = rougeTriangle.getX();
        float y = rougeTriangle.getY();
        
        window.drawTriangle(x, y, 0);
    }
}

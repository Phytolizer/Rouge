import engine.Game;
import engine.graphics.Window;
import engine.entities.Entity;
import engine.logic.Mouse;

import static org.lwjgl.glfw.GLFW.*;

public class Rouge implements Game {
    public Entity rougeTriangle = new Entity();
    
    @Override
    public void runFrame(double deltaTime, boolean[] keyStates, Mouse mouse, Window window) {
        float timeConstant = (float) ((1 / deltaTime) / 60);
        
        if (keyStates[GLFW_KEY_D]) {
            rougeTriangle.position.increaseX(0.01f * timeConstant);
        }
        if (keyStates[GLFW_KEY_A]) {
            rougeTriangle.position.increaseX(-0.01f * timeConstant);
        }
        if (keyStates[GLFW_KEY_W]) {
            rougeTriangle.position.increaseY(0.01f * timeConstant);
        }
        if (keyStates[GLFW_KEY_S]) {
            rougeTriangle.position.increaseY(-0.01f * timeConstant);
        }
        
        float x = rougeTriangle.position.getX();
        float y = rougeTriangle.position.getY();
        
        window.drawRectangle(x, y, 0);
    }
}

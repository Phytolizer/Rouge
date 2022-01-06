import engine.Game;
import engine.graphics.Window;
import engine.logic.entities.Entity;
import engine.logic.Keyboard;
import engine.logic.Mouse;
import engine.logic.entities.Rectangle;

import static org.lwjgl.glfw.GLFW.*;

public class Rouge extends Game {
    public Entity rougeRectangle = new Rectangle(0.1f, 0.1f, 0f, 0f, 0f);
    
    @Override
    public void runFrame(double deltaTime, Keyboard keyboard, Mouse mouse, Window window) {
        float timeConstant = (float) ((1 / deltaTime) / 60);
        
        if (keyboard.getKeyPressed(GLFW_KEY_D)) {
            rougeRectangle.moveBy(0.01f * timeConstant, 0f, 0f);
        }
        if (keyboard.getKeyPressed(GLFW_KEY_A)) {
            rougeRectangle.moveBy(-0.01f * timeConstant, 0f, 0f);
        }
        if (keyboard.getKeyPressed(GLFW_KEY_W))   {
            rougeRectangle.moveBy(0f, 0.01f * timeConstant, 0f);
        }
        if (keyboard.getKeyPressed(GLFW_KEY_S)) {
            rougeRectangle.moveBy(0f, -0.01f * timeConstant, 0f);
        }
        if(keyboard.getKeyPressed(GLFW_KEY_UP)) {
            rougeRectangle.moveBy(0f, 0f, 0.01f * timeConstant);
        }
        if(keyboard.getKeyPressed(GLFW_KEY_DOWN)) {
            rougeRectangle.moveBy(0f, 0f, -0.01f * timeConstant);
        }
        
        window.draw(rougeRectangle);
    }
}

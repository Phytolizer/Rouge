import graphics.Window;
import eventlisteners.KeyListener;
import logic.Entity;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) {
        var rougeWindow = new Window();
        var rougeKeyListener = new KeyListener();
        var rougeTriangle = new Entity();

        rougeWindow.setKeyCallback(rougeKeyListener);
        rougeWindow.run( () -> {

            if(rougeKeyListener.getKeyState(GLFW_KEY_D)) rougeTriangle.increaseX(0.01f);
            if(rougeKeyListener.getKeyState(GLFW_KEY_A)) rougeTriangle.increaseX(-0.01f);
            if(rougeKeyListener.getKeyState(GLFW_KEY_W)) rougeTriangle.increaseY(0.01f);
            if(rougeKeyListener.getKeyState(GLFW_KEY_S)) rougeTriangle.increaseY(-0.01f);

            float x = rougeTriangle.getX();
            float y = rougeTriangle.getY();

            rougeWindow.draw(x, y);

        });
    }
}

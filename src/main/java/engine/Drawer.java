package engine;

import engine.internal.Rendering;
import engine.logic.entities.Rectangle;

import static org.lwjgl.opengl.GL33.*;

public class Drawer {
    private final Window window;

    public Drawer(Window aWindow) {
        this.window = aWindow;
    }

    public void drawRectangle(Rectangle rectangle) {
        window.addDrawFunctionBatch(() -> {
            var position = rectangle.getPosition();
            var size = rectangle.getSize();

            float x = position.getX();
            float y = position.getY() * window.getAspectRatio();
            float z = position.getZ();
            float width = size.getWidth();
            float height = size.getHeight() * window.getAspectRatio();

            var texture = rectangle.getTexture();
            if(texture != null) {
                texture.set();
            }

            float[] vertexArray = {
                    x - (width/2), y + (height/2), z,  1f, 1f, 1f, 1f,  0f, 1f,    // top left
                    x + (width/2), y + (height/2), z,  1f, 1f, 1f, 1f,  1f, 1f,  // top right
                    x - (width/2), y - (height/2), z,  1f, 1f, 1f, 1f,  0f, 0f,  // bottom left
                    x + (width/2), y - (height/2), z,  1f, 1f, 1f, 1f,  1f, 0f  // bottom right
            };

            int[] elementArray = {
                    3, 1, 0,
                    0, 3, 2
            };

            Rendering.VertexBuffer.bind(vertexArray);
            Rendering.ElementBuffer.bind(elementArray);
            glDrawElements(GL_TRIANGLES, elementArray.length,
                    GL_UNSIGNED_INT, 0);
        });
    }
}

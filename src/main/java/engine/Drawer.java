package engine;

import engine.internal.RenderingState;
import engine.logic.entities.Rectangle;

import java.util.ArrayDeque;

import static org.lwjgl.opengl.GL33.*;

public class Drawer {
    private final Window window;
    private final ArrayDeque<Runnable> functionBatch;

    public Drawer(Window aWindow) {
        this.window = aWindow;
        functionBatch = new ArrayDeque<>();
    }

    public void update() {
        window.addDrawFunctionBatch(functionBatch.clone());
        functionBatch.clear();
    }

    public void drawRectangle(Rectangle rectangle) {
        functionBatch.add(() -> {
            var position = rectangle.getPosition();
            var size = rectangle.getSize();

            float x = position.getX();
            float y = position.getY();
            float z = position.getZ();
            float width = size.getWidth();
            float height = size.getHeight();

            float[] newVertexArray = {
                    x - (width/2), y + (height/2), z, 1f, 1f, 1f, 1f,    // top left
                    x + (width/2), y + (height/2), z, 1f, 1f, 1f, 1f,   // top right
                    x - (width/2), y - (height/2), z, 1f, 1f, 1f, 1f,  // bottom left
                    x + (width/2), y - (height/2), z, 1f, 1f, 1f, 1f  // bottom right
            };

            glBufferSubData(GL_ARRAY_BUFFER, 0, newVertexArray);
            glDrawElements(GL_TRIANGLES, RenderingState.ElementBuffer.getElementArray().length,
                    GL_UNSIGNED_INT, 0);
        });
    }
}

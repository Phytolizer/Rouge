package engine;

import engine.internal.Rendering;
import engine.entities.Rectangle;

import static org.lwjgl.opengl.GL33.*;

public class Artist {
    private final Window window;

    public Artist(Window aWindow) {
        this.window = aWindow;
    }

    public void drawRectangle(Rectangle rectangle) {
        window.addDrawFunctionBatch(() -> {
            var texture = rectangle.getTexture();
            if(texture != null) {
                texture.set();
            }

            Rendering.VertexBuffer.bind(rectangle.getVBO());
            Rendering.ElementBuffer.bind(rectangle.getEBO());

            glDrawElements(GL_TRIANGLES, rectangle.getEBO().length,
                    GL_UNSIGNED_INT, 0);
        });
    }
}

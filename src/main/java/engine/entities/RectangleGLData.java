package engine.entities;

import engine.core.Coords;

public class RectangleGLData {
    private float[] VBO;
    private int[] EBO;

    public RectangleGLData(Coords.Position position, Coords.Size size) {
        float x = position.getX();
        float y = position.getY();
        float z = position.getZ();
        float width = size.getWidth();
        float height = size.getHeight();

        VBO = new float[] {
                x - (width/2), y + (height/2), z, 0f, 1f,    // top left
                x + (width/2), y + (height/2), z, 1f, 1f,   // top right
                x - (width/2), y - (height/2), z, 0f, 0f,  // bottom left
                x + (width/2), y - (height/2), z, 1f, 0f  // bottom right
        };

        EBO = new int[] {
                3, 1, 0,
                0, 3, 2
        };
    }

    public void setVertexData(Coords.Position position, Coords.Size size) {
        float x = position.getX();
        float y = position.getY();
        float z = position.getZ();
        float width = size.getWidth();
        float height = size.getHeight();

        VBO = new float[] {
                x - (width/2), y + (height/2), z, 0f, 1f,    // top left
                x + (width/2), y + (height/2), z, 1f, 1f,   // top right
                x - (width/2), y - (height/2), z, 0f, 0f,  // bottom left
                x + (width/2), y - (height/2), z, 1f, 0f  // bottom right
        };
    }

    public float[] getVBO() {
        return VBO;
    }

    public int[] getEBO() {
        return EBO;
    }
}

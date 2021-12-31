package engine.graphics.internal;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL33.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL33.glVertexAttribPointer;

public class VertexBuffer {
    private float[] data;
    private int id;
    private int coordSize;
    private int colorSize;
    private int texCoordSize;
    private int vertexSize;
    private int vapCount;
    
    public VertexBuffer(int vertexSize, int coordSize, int colorSize, int texCoordSize, float[] data) {
        this.data = data;
        this.coordSize = coordSize;
        this.colorSize = colorSize;
        this.texCoordSize = texCoordSize;
        this.vertexSize = vertexSize;
        this.id = glGenBuffers();
        
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        int i = 0;
        int pos = 0;
        vapCount = 0;
        if (coordSize > 0) {
            glVertexAttribPointer(i, coordSize, GL_FLOAT, false, vertexSize * Float.BYTES, pos);
            glEnableVertexAttribArray(i);
            i++;
            pos += coordSize * Float.BYTES;
            vapCount++;
        }
        if (colorSize > 0) {
            glVertexAttribPointer(i, colorSize, GL_FLOAT, false, vertexSize * Float.BYTES, pos);
            glEnableVertexAttribArray(i);
            i++;
            pos += colorSize * Float.BYTES;
            vapCount++;
        }
        if (texCoordSize > 0) {
            glVertexAttribPointer(i, texCoordSize, GL_FLOAT, false, vertexSize * Float.BYTES, pos);
            glEnableVertexAttribArray(i);
            vapCount++;
        }
    }
    
    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
        for (int i = 0; i < vapCount; i++) {
            glEnableVertexAttribArray(i);
        }
    }
}

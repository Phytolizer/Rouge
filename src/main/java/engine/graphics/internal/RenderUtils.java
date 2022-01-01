package engine.graphics.internal;

import static org.lwjgl.opengl.GL33.*;

public class RenderUtils {
    private static int vaoId, vboId, eboId;
    private static int vapCount;

    public static int getVaoId() {
        return vaoId;
    }

    public static int getVboId() {
        return vboId;
    }

    public static int getEboId() {
        return eboId;
    }

    public static class VertexArray {
        public static void init() {
            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);
        }

        public static void enableAttribs() {
            for (int i = 0; i < vapCount; i++) {
                glEnableVertexAttribArray(i);
            }
        }

        public static void disableAttribs() {
            for(int i = 0; i < vapCount; i++) {
                glDisableVertexAttribArray(i);
            }
        }
    }

    public static class VertexBuffer {
        private static float[] data;
        private static int coordSize;
        private static int colorSize;
        private static int texCoordSize;
        private static int vertexSize;

        public static void init(int aVertexSize, int aCoordSize, int aColorSize, int aTexCoordSize, float[] aVertexArray) {
            data = aVertexArray;
            coordSize = aCoordSize;
            colorSize = aColorSize;
            texCoordSize = aTexCoordSize;
            vertexSize = aVertexSize;

            vboId = glGenBuffers();

            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
            int i = 0;
            int pos = 0;
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
    }

    public static class ElementBuffer {
        private static int[] elementArray;

        public static void init(int[] aElementArray) {
            elementArray = aElementArray;
            eboId = glGenBuffers();

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, aElementArray, GL_DYNAMIC_DRAW);
        }

        public static int[] getElementArray() {
            return elementArray;
        }

    }
}

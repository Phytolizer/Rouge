package engine.internal;

import static org.lwjgl.opengl.GL33.*;

/**
 * The {@code RenderingState} class works as an util for abstracting
 * away and managing the current openGL context's state, that being
 * the reason for it being a static class.
 */
public class RenderingState {
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

    /**
     * The {@code VertexArray} class abstracts away the OpenGL
     * Rendering's pipeline VAO, or Vertex Array Object.
     */
    public static class VertexArray {

        /**
         * Creates a VAO ID and binds it to the current OpenGL Context,
         * needs to be called before the VBO and EBO.
         */
        public static void init() {
            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            if(vaoId == 0) {
                throw new ExceptionInInitializerError("Failed to initialize VAO.");
            }
        }

        /**
         * Sets a pointer to each attribute in the vertex data (the position,
         * color, texture coordinates) as an element in the VAO and
         * subsequently enables those attributes.
         *
         * @param coordSize Size of the coordinates attribute in the number of floats.
         * @param colorSize Size of the color attribute in the number of floats.
         * @param texCoordSize Size of the texture coordinates in the number of floats.
         */
        public static void setAttribs(int coordSize, int colorSize, int texCoordSize) {
            int vertexSize = (coordSize + colorSize) * Float.BYTES;

            int i = 0;
            int pos = 0;
            if (coordSize > 0) {
                glVertexAttribPointer(i, coordSize, GL_FLOAT, false, vertexSize, pos);
                glEnableVertexAttribArray(i);
                i++;
                pos += coordSize * Float.BYTES;
                vapCount++;
            }
            if (colorSize > 0) {
                glVertexAttribPointer(i, colorSize, GL_FLOAT, false, vertexSize, pos);
                glEnableVertexAttribArray(i);
                i++;
                pos += colorSize * Float.BYTES;
                vapCount++;
            }
            if (texCoordSize > 0) {
                glVertexAttribPointer(i, texCoordSize, GL_FLOAT, false,
                        vertexSize * Float.BYTES, pos);
                glEnableVertexAttribArray(i);
                vapCount++;
            }
        }

        /**
         * Enables the attributes created on the
         * setAttribs() method.
         */
        public static void enableAttribs() {
            for (int i = 0; i < vapCount; i++) {
                glEnableVertexAttribArray(i);
            }
        }

        /**
         * Disables the attributes created on the
         * setAttribs( method.
         */
        public static void disableAttribs() {
            for (int i = 0; i < vapCount; i++) {
                glDisableVertexAttribArray(i);
            }
        }
    }

    /**
     * The {@code VertexBuffer} class abstracts away the OpenGL
     * Rendering's pipeline VBO, or Vertex Array Object.
     */
    public static class VertexBuffer {

        /**
         * Creates a VBO ID, binds it to the current OpenGL Context and
         * uploads the vertex data to the buffer.
         */
        public static void init(float[] vertexData) {
            vboId = glGenBuffers();
            
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);

            if(vboId == 0) {
                throw new ExceptionInInitializerError("Failed to initialize VBO.");
            }
        }
    }

    /**
     * The {@code ElementBuffer} class abstracts away the OpenGL
     * Rendering's pipeline EBO, or Element Buffer Object.
     */
    public static class ElementBuffer {
        private static int[] elementArray;

        /**
         * Creates an EBO ID, binds it to the current OpenGL Context,
         * and uploads the data to the required buffer.
         * needs to be called after the VAO and VBO.
         */
        public static void init(int[] aElementArray) {
            elementArray = aElementArray;
            eboId = glGenBuffers();
            
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, aElementArray, GL_DYNAMIC_DRAW);

            if(eboId == 0) {
                throw new ExceptionInInitializerError("Failed to initialize EBO.");
            }
        }

        /**
         * @return An array with the vertices in the needed order.
         */
        public static int[] getElementArray() {
            return elementArray;
        }
    }
}

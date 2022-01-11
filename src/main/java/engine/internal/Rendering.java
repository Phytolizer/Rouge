package engine.internal;

import org.lwjgl.stb.*;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * The {@code Rendering} class works as an util for abstracting
 * away and managing the current openGL context's state, that being
 * the reason for it being a static class.
 */
public class Rendering {
    private static int vaoId, vboId, eboId;

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
        private static boolean[] activeAttr;

        /**
         * Creates a VAO ID and binds it to the current OpenGL Context,
         * needs to be called before the VBO and EBO.
         */
        public static void init() {
            vaoId = glGenVertexArrays();
            activeAttr = new boolean[3];
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
            int vertexSize = (coordSize + colorSize + texCoordSize) * Float.BYTES;

            int i = 0;
            int pos = 0;
            if (coordSize > 0) {
                glVertexAttribPointer(i, coordSize, GL_FLOAT, false, vertexSize, pos);
                activeAttr[i] = true;
                i++;
                pos += coordSize * Float.BYTES;

            } else {
                activeAttr[i] = false;
                i++;
            }

            if (colorSize > 0) {
                glVertexAttribPointer(i, colorSize, GL_FLOAT, false, vertexSize, pos);
                activeAttr[i] = true;
                i++;
                pos += colorSize * Float.BYTES;
            } else {
                activeAttr[i] = false;
                i++;
            }

            if (texCoordSize > 0) {
                glVertexAttribPointer(i, texCoordSize, GL_FLOAT, false, vertexSize, pos);
                activeAttr[i] = true;
            } else {
                activeAttr[i] = false;
            }
        }

        /**
         * Enables the attributes created on the
         * setAttribs() method.
         */
        public static void enableAttribs() {
            for (int i = 0; i < activeAttr.length; i++) {
                if(activeAttr[i]) glEnableVertexAttribArray(i);
            }
        }

        /**
         * Disables the attributes created on the
         * setAttribs( method.
         */
        public static void disableAttribs() {
            for (int i = 0; i < activeAttr.length; i++) {
                if(activeAttr[i]) glDisableVertexAttribArray(i);
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
        public static void init() {
            vboId = glGenBuffers();

            if(vboId == 0) {
                throw new ExceptionInInitializerError("Failed to initialize VBO.");
            }
        }

        public static void bind(float[] vertexData) {
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, vertexData, GL_DYNAMIC_DRAW);
        }
    }

    /**
     * The {@code ElementBuffer} class abstracts away the OpenGL
     * Rendering's pipeline EBO, or Element Buffer Object.
     */
    public static class ElementBuffer {

        /**
         * Creates an EBO ID, binds it to the current OpenGL Context,
         * and uploads the data to the required buffer.
         * needs to be called after the VAO and VBO.
         */
        public static void init() {
            eboId = glGenBuffers();

            if(eboId == 0) {
                throw new ExceptionInInitializerError("Failed to initialize EBO.");
            }
        }

        public static void bind(int[] aElementArray) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, aElementArray, GL_DYNAMIC_DRAW);
        }

        }

    public static class Texture {
        private int texID;
        private String imageSrc;

        public Texture(String aImageSrc) {
            this.texID = glGenTextures();
            this.imageSrc = aImageSrc;
        }

        public void set() {
            glBindTexture(GL_TEXTURE_2D, texID);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            try(MemoryStack stack = stackPush()) {
                IntBuffer width = stack.mallocInt(1);
                IntBuffer height = stack.mallocInt(1);
                IntBuffer channels = stack.mallocInt(1);

                STBImage.stbi_set_flip_vertically_on_load(true);
                ByteBuffer imageData = STBImage.stbi_load(imageSrc, width, height, channels, 0);
                Objects.requireNonNull(imageData);

                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData);
                glGenerateMipmap(GL_TEXTURE_2D);
                STBImage.stbi_image_free(imageData);
            }
        }

        public int getTexID() {
            return this.texID;
        }
    }
}

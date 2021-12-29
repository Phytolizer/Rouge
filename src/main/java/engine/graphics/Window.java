package engine.graphics;

import engine.math.Matrix4;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private static Window instance = null;
    private static long windowHandle;
    private static Shader windowShader = null;
    private static Runnable timeSetter = () -> {
    };
    private static float[] vertexArray;
    private static int[] elementArray;
    private static int vaoID, vboID, eboID;
    
    private Window() {
    }
    
    public static void init() {
        if (instance != null) {
            return;
        }
        instance = new Window();
        
        GLFWErrorCallback.createPrint(System.err).set();
        
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        
        windowHandle = glfwCreateWindow(1280, 720, "Rouge", NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        
        glfwSetWindowSizeCallback(windowHandle, (window, x, y) -> {
            glViewport(0, 0, x, y);
            glUniformMatrix4fv(windowShader.getUniformLocation("projection"), false, Matrix4.ortho(0, 1280, 720, 0, -1, 1).values);
        });
        
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*
            
            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowHandle, pWidth, pHeight);
            
            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            
            // Center the window
            assert vidmode != null;
            glfwSetWindowPos(windowHandle,
                    (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        } // the stack frame is popped automatically
        
        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);
        // Enable v-sync
        glfwSwapInterval(1);
        glfwShowWindow(windowHandle);
    }
    
    public static <Listener extends GLFWKeyCallbackI> void setKeyListener(Listener listener) {
        if (instance == null) {
            throw new IllegalStateException();
        }
        
        glfwSetKeyCallback(windowHandle, listener);
    }
    
    public static void setClock(Consumer<Double> clockFunc) {
        if (instance == null) {
            throw new IllegalStateException();
        }
        
        timeSetter = () -> clockFunc.accept(glfwGetTime());
    }
    
    public static void run(Runnable task) {
        if (instance == null) {
            throw new IllegalStateException();
        }
        
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");
        
        GL.createCapabilities();
        
        String vertexSource;
        String fragmentSource;
        try {
            vertexSource = readEntireFile("assets/shaders/vertex.glsl");
            fragmentSource = readEntireFile("assets/shaders/fragment.glsl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        windowShader = new Shader(vertexSource, fragmentSource);
        glUniformMatrix4fv(windowShader.getUniformLocation("projection"), false, Matrix4.ortho(0, 1280, 720, 0, -1, 1).values);
        
        vertexArray = new float[]{
                0, 0.5f, 1f, 1f, 1f, 1f, 1f,
                -0.5f, -0.5f, 1f, 1f, 1f, 1f, 1f,
                0.5f, -0.5f, 1f, 1f, 1f, 1f, 1f
        };
        
        elementArray = new int[]{
                0, 1, 2
        };
        
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();
        
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_DYNAMIC_DRAW);
        
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();
        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_DYNAMIC_DRAW);
        
        int positionSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes,
                positionSize * floatSizeBytes);
        glEnableVertexAttribArray(1);
        
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        
        
        while (!glfwWindowShouldClose(windowHandle)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            timeSetter.run();
            
            task.run();
            
            glfwSwapBuffers(windowHandle); // swap the color buffers
            
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
        
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    
    public static void drawTriangle(float x, float y, float z) {
        glUseProgram(windowShader.getShaderId());
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        
        float[] newVertexArray = {
                x, y + 0.5f, z, 1f, 1f, 1f, 1f,
                x - 0.5f, y - 0.5f, z, 1f, 1f, 1f, 1f,
                x + 0.5f, y - 0.5f, z, 1f, 1f, 1f, 1f
        };
        
        FloatBuffer newVertexBuffer = BufferUtils.createFloatBuffer(newVertexArray.length);
        newVertexBuffer.put(newVertexArray).flip();
        
        glBufferSubData(GL_ARRAY_BUFFER, 0, newVertexBuffer);
        
        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);
        
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        glUseProgram(0);
        
        
    }
    
    private static @NotNull String readEntireFile(String filename) throws IOException {
        var bytes = Files.readAllBytes(Paths.get(filename));
        return new String(bytes, StandardCharsets.UTF_8);
    }
}



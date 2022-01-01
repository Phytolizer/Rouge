package engine.graphics;

import engine.eventlisteners.*;
import engine.graphics.internal.*;
import engine.logic.Timer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long windowHandle;
    private Shader windowShader = null;
    
    private Mouse.CursorPosListener mouseCursorPosListener;
    private Mouse.ButtonListener mouseButtonListener;
    private KeyListener keyListener;
    private Timer timer;
    
    public Window() {
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
        
        glfwSetWindowSizeCallback(windowHandle, (window, x, y) -> glViewport(0, 0, x, y));
        
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
        }
        
        glfwMakeContextCurrent(windowHandle);
        // Enable v-sync
        glfwSwapInterval(1);
        glfwShowWindow(windowHandle);
    }
    
    public void setMouseCursorPosListener(Mouse.CursorPosListener mouseCursorPosListener) {
        glfwSetCursorPosCallback(windowHandle, mouseCursorPosListener);
        this.mouseCursorPosListener = mouseCursorPosListener;
    }
    
    public void setMouseButtonListener(Mouse.ButtonListener mouseButtonListener) {
        glfwSetMouseButtonCallback(windowHandle, mouseButtonListener);
        this.mouseButtonListener = mouseButtonListener;
    }
    
    public void setKeyListener(KeyListener keyListener) {
        glfwSetKeyCallback(windowHandle, keyListener);
        this.keyListener = keyListener;
    }
    
    public void setTimer(Timer timer) {
        this.timer = timer;
    }
    
    public void run(QuadConsumer<Double, boolean[], double[], boolean[]> task) {
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
        
        float[] vertexArray = new float[]{
                0, 0.5f, 1f, 1f, 1f, 1f, 1f,
                -0.5f, -0.5f, 1f, 1f, 1f, 1f, 1f,
                0.5f, -0.5f, 1f, 1f, 1f, 1f, 1f
        };
        
        int positionSize = 3;
        int colorSize = 4;
        int vertexSize = positionSize + colorSize;
        
        int[] elementArray = new int[]{
                0, 1, 2
        };
        
        RenderUtils.VertexArray.init();
        RenderUtils.VertexBuffer.init(vertexSize, positionSize, colorSize, 0, vertexArray);
        RenderUtils.ElementBuffer.init(elementArray);
        
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSize * Float.BYTES, 0);
        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSize * Float.BYTES,
                positionSize * Float.BYTES);
        RenderUtils.VertexArray.enableAttribs();
        
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        
        while (!glfwWindowShouldClose(windowHandle)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            
            double[] cursorXYArray = new double[]{mouseCursorPosListener.getXPos(),
                    mouseCursorPosListener.getYPos()};
            
            task.accept(timer.tick(glfwGetTime()), keyListener.getAllKeyStates(),
                    cursorXYArray,
                    mouseButtonListener.getAllButtonStates());
            
            glfwSwapBuffers(windowHandle);
            glfwPollEvents();
        }
        
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
        
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }
    
    public void drawTriangle(float x, float y, float z) {
        glUseProgram(windowShader.getId());
        glBindVertexArray(RenderUtils.getVaoId());
        RenderUtils.VertexArray.enableAttribs();
        
        float[] newVertexArray = {
                x, y + 0.5f, z, 1f, 1f, 1f, 1f,
                x - 0.5f, y - 0.5f, z, 1f, 1f, 1f, 1f,
                x + 0.5f, y - 0.5f, z, 1f, 1f, 1f, 1f
        };
        
        glBufferSubData(GL_ARRAY_BUFFER, 0, newVertexArray);
        glDrawElements(GL_TRIANGLES, RenderUtils.ElementBuffer.getElementArray().length,
                GL_UNSIGNED_INT, 0);
        
        RenderUtils.VertexArray.disableAttribs();
        glBindVertexArray(0);
        glUseProgram(0);
    }
    
    private static @NotNull String readEntireFile(String filename) throws IOException {
        var bytes = Files.readAllBytes(Paths.get(filename));
        return new String(bytes, StandardCharsets.UTF_8);
    }
}



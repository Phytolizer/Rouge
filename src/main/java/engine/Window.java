package engine;

import engine.core.Position;
import engine.eventlisteners.*;
import engine.graphics.Camera;
import engine.internal.RenderingState;
import engine.internal.Shader;

import engine.logic.Timer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;
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
import java.util.*;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * The {@code Window} class represents a GLFW window
 * and its instances will be where all the drawing
 * and event listening will be done.
 */
public class Window {
    private final long windowHandle;
    private Shader windowShader;

    private float aspectRatio;

    private FloatBuffer projectionBuffer;
    private FloatBuffer viewBuffer;
    
    private CursorPosListener cursorPosListener;
    private MouseButtonListener mouseButtonListener;
    private KeyListener keyListener;

    private Camera camera;
    private Timer timer;

    private Deque<Runnable> functionBatch;

    /**
     * Will create a window, set its resolution,
     * set the v-sync state,
     * and some internal configuring.
     */
    public Window() {
        GLFWErrorCallback.createPrint(System.err).set();
        
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        
        windowHandle = glfwCreateWindow(1280, 720, "Rouge", NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetWindowSizeCallback(windowHandle, (window, x, y) ->  {
            glViewport(0, 0, x, y);
            aspectRatio = (float) x/y;
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*
            
            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowHandle, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            assert vidmode != null;
            aspectRatio = (float) vidmode.width() / vidmode.height();
            // Center the window
            glfwSetWindowPos(windowHandle,
                    (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        }
        
        glfwMakeContextCurrent(windowHandle);
        glfwSwapInterval(1); // Enables v-sync
        glfwShowWindow(windowHandle);
        GL.createCapabilities();

        functionBatch = new ArrayDeque<>();

        String vertexSource;
        String fragmentSource;
        try {
            vertexSource = readEntireFile("assets/shaders/vertex.glsl");
            fragmentSource = readEntireFile("assets/shaders/fragment.glsl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        windowShader = new Shader(vertexSource, fragmentSource);
        camera = new Camera();

        projectionBuffer = BufferUtils.createFloatBuffer(16);
        viewBuffer = BufferUtils.createFloatBuffer(16);

        float[] vertexData = new float[]{
                -0.5f, 0.5f, 1f, 1f, 1f, 1f, 1f,    // top left
                0.5f, 0.5f, 1f, 1f, 1f, 1f, 1f,    // top right
                -0.5f, -0.5f, 1f, 1f, 1f, 1f, 1f, // bottom left
                0.5f, -0.5f, 1f, 1f, 1f, 1f, 1f  // bottom right
        };

        int coordSize = 3;
        int colorSize = 4;

        int[] elementArray = new int[]{
                3, 1, 0,
                0, 3, 2
        };

        RenderingState.VertexArray.init();
        RenderingState.VertexBuffer.init(vertexData);
        RenderingState.ElementBuffer.init(elementArray);

        RenderingState.VertexArray.setAttribs(coordSize, colorSize, 0);
        RenderingState.VertexArray.enableAttribs();

        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }

    /**
     * Defines a cursor position listener to listen for
     * CursorPosCallback events.
     * @param cursorPosListener CursorPosListener object
     */
    public void setCursorPosListener(CursorPosListener cursorPosListener) {
        glfwSetCursorPosCallback(windowHandle, cursorPosListener);
        this.cursorPosListener = cursorPosListener;
    }

    /**
     * Defines a mouse button listener to listen for
     * MouseButtonCallback events.
     * @param mouseButtonListener MouseButtonListener object
     */
    public void setMouseButtonListener(MouseButtonListener mouseButtonListener) {
        glfwSetMouseButtonCallback(windowHandle, mouseButtonListener);
        this.mouseButtonListener = mouseButtonListener;
    }

    /**
     * Defines a keyboard listener to listen for
     * KeyCallback events.
     * @param keyListener KeyListener object
     */
    public void setKeyListener(KeyListener keyListener) {
        glfwSetKeyCallback(windowHandle, keyListener);
        this.keyListener = keyListener;
    }

    /**
     * Defines a timer.
     * @param timer Timer object
     */
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    protected void addDrawFunctionBatch(Runnable drawingCall) {
        functionBatch.add(drawingCall);
    }

    public Position getCursorPos() {
        return cursorPosListener.getPosition();
    }

    public boolean getMouseButtonPressed(int button) {
        return mouseButtonListener.getButtonState(button);
    }

    public boolean getKeyPressed(int key) {
        return keyListener.getKeyState(key);
    }

    public double getTimeDelta() {
        return timer.tick(glfwGetTime());
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    /**
     * Runs a loop where all the game's logic
     * will be contained.
     */
    public void update() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        glUseProgram(windowShader.getId());

        camera.getViewMatrix().get(viewBuffer);
        camera.getProjMatrix().get(projectionBuffer);

        glUniformMatrix4fv(windowShader.getUniformLocation("viewMatrix"), false, viewBuffer);
        glUniformMatrix4fv(windowShader.getUniformLocation("projMatrix"), false, projectionBuffer);

        glBindVertexArray(RenderingState.getVaoId());
        RenderingState.VertexArray.enableAttribs();

        for(Runnable r : functionBatch) Objects.requireNonNull(functionBatch.poll()).run();

        RenderingState.VertexArray.disableAttribs();
        glBindVertexArray(0);
        glUseProgram(0);

        glfwSwapBuffers(windowHandle);
    }

    public void destroy() {
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        glfwTerminate();

        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }
    
    private static @NotNull String readEntireFile(String filename) throws IOException {
        var bytes = Files.readAllBytes(Paths.get(filename));
        return new String(bytes, StandardCharsets.UTF_8);
    }
}



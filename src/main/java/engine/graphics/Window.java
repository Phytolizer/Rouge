package engine.graphics;

import engine.math.Matrix4;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long windowHandle;
    private Shader windowShader = null;
    
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
            glfwSetWindowPos(windowHandle,
                    (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        } // the stack frame is popped automatically
        
        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);
        // Enable v-sync
        glfwSwapInterval(1);
        glfwShowWindow(windowHandle);
    }
    
    public <Listener extends GLFWKeyCallbackI> void setKeyCallback(Listener listener) {
        glfwSetKeyCallback(windowHandle, listener);
    }
    
    public void run(Runnable task) {
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");
        
        this.loop(task);
        
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    
    
    private void loop(Runnable task) {
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
        
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        
        windowShader.use();
        while (!glfwWindowShouldClose(windowHandle)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            
            task.run();
            
            glfwSwapBuffers(windowHandle); // swap the color buffers
            
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
    
    public void draw(float x, float y) {
        glBegin(GL_TRIANGLES);
        glColor3f(1.0f, 0.0f, 0.0f);
        glVertex2f(x - 0.5f, y - 0.5f);
        glColor3f(0.0f, 1.0f, 0.0f);
        glVertex2f(x, y + 0.5f);
        glColor3f(0.0f, 0.0f, 1.0f);
        glVertex2f(x + 0.5f, y - 0.5f);
        glEnd();
    }
    
    private @NotNull String readEntireFile(String filename) throws IOException {
        var bytes = Files.readAllBytes(Paths.get(filename));
        return new String(bytes, StandardCharsets.UTF_8);
    }
}



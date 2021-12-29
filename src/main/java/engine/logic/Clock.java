package engine.logic;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Clock {
    private double prevTime = glfwGetTime();
    
    public double tick() {
        double now = glfwGetTime();
        double delta = now - prevTime;
        prevTime = now;
        return delta;
    }
}

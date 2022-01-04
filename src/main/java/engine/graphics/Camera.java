package engine.graphics;
import org.joml.Matrix4f;

public class Camera {
    private Matrix4f viewMatrix;
    private Matrix4f projMatrix;

    public Camera() {
        viewMatrix = new Matrix4f().lookAt(
                        0f, 0f, 5f,
                        0f, 0f, 0f,
                        0f, 1f, 0f);

        projMatrix = new Matrix4f().perspective((float) Math.toRadians(45.0f), 1.0f, -1, 1);
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getProjMatrix() {
        return projMatrix;
    }
}

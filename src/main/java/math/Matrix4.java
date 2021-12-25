package math;

import org.jetbrains.annotations.NotNull;

public class Matrix4 {
    public float[][] values;
    
    public Matrix4() {
        this.values = new float[4][4];
    }
    
    public static @NotNull Matrix4 ortho(float left, float right, float bottom, float top, float near, float far) {
        var result = new Matrix4();
        result.values[0][0] = 2.0f / (right - left);
        result.values[1][1] = 2.0f / (top - bottom);
        result.values[2][2] = -2.0f / (far - near);
        result.values[3][0] = -(right + left) / (right - left);
        result.values[3][1] = -(top + bottom) / (top - bottom);
        result.values[3][2] = -(far + near) / (far - near);
        return result;
    }
}

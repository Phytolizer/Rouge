package math;

import org.jetbrains.annotations.NotNull;

public class Matrix4 {
    public float[] values;
    
    public Matrix4() {
        this.values = new float[16];
    }
    
    public static @NotNull Matrix4 ortho(float left, float right, float bottom, float top, float near, float far) {
        var result = new Matrix4();
        result.values[0] = 2.0f / (right - left);
        result.values[5] = 2.0f / (top - bottom);
        result.values[10] = -2.0f / (far - near);
        result.values[12] = -(right + left) / (right - left);
        result.values[13] = -(top + bottom) / (top - bottom);
        result.values[14] = -(far + near) / (far - near);
        return result;
    }
}

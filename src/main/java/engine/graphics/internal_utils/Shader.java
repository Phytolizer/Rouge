package engine.graphics.internal_utils;

import static org.lwjgl.opengl.GL33.*;

public class Shader {
    private final int shaderId;
    
    public Shader(String vertexSource, String fragmentSource) {
        int vertexId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexId, vertexSource);
        glCompileShader(vertexId);
        if (glGetShaderi(vertexId, GL_COMPILE_STATUS) == GL_FALSE) {
            var log = glGetShaderInfoLog(vertexId);
            throw new RuntimeException("Vertex shader compilation failed: " + log);
        }
        int fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentId, fragmentSource);
        glCompileShader(fragmentId);
        if (glGetShaderi(fragmentId, GL_COMPILE_STATUS) == GL_FALSE) {
            var log = glGetShaderInfoLog(fragmentId);
            throw new RuntimeException("Fragment shader compilation failed: " + log);
        }
        shaderId = glCreateProgram();
        glAttachShader(shaderId, vertexId);
        glAttachShader(shaderId, fragmentId);
        glLinkProgram(shaderId);
        if (glGetProgrami(shaderId, GL_LINK_STATUS) == GL_FALSE) {
            var log = glGetProgramInfoLog(shaderId);
            throw new RuntimeException("Shader linking failed: " + log);
        }
    }
    
    public int getId() {
        return shaderId;
    }
    
    
}

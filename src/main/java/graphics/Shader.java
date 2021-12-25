package graphics;

import static org.lwjgl.opengl.GL33.*;

public class Shader {
    int id;
    
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
        id = glCreateProgram();
        glAttachShader(id, vertexId);
        glAttachShader(id, fragmentId);
        glLinkProgram(id);
        if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
            var log = glGetProgramInfoLog(id);
            throw new RuntimeException("Shader linking failed: " + log);
        }
    }
    
    public void use() {
        glUseProgram(id);
    }
    
    public int getUniformLocation(String name) {
        return glGetUniformLocation(id, name);
    }
}

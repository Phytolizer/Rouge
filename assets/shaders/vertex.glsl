#version 330 core

uniform mat4 projection;
in vec2 position;
out vec3 fragColor;

void main()
{
    gl_Position = projection * vec4(position, 0.0, 1.0);
    fragColor = vec3(1.0, 1.0, 1.0);
}

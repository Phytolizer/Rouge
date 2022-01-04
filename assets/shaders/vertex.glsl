#version 330 core

layout(location = 0) in vec3 attrPos;
layout(location = 1) in vec4 attrColor;

uniform mat4 viewMatrix;
uniform mat4 projMatrix;

out vec4 fragColor;

void main(void)
{
    gl_Position = projMatrix * viewMatrix * vec4(attrPos, 1.0);
    fragColor = attrColor;
}

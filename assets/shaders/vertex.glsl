#version 330 core

layout(location = 0) in vec3 attrPos;
layout(location = 1) in vec4 attrColor;
layout(location = 2) in vec2 attrTex;

uniform mat4 viewMatrix;
uniform mat4 projMatrix;

out vec4 fragColor;
out vec2 texCoords;

void main(void)
{
    gl_Position = projMatrix * viewMatrix * vec4(attrPos, 1.0);
    fragColor = attrColor;
    texCoords = attrTex;
}

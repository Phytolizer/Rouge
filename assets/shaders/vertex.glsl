#version 330 core

layout(location = 0) in vec3 attrPos;
layout(location = 1) in vec4 attrColor;

out vec4 fragColor;

//uniform mat4 projection;

void main(void)
{
    gl_Position =vec4(attrPos, 1.0);
    fragColor = attrColor;
}

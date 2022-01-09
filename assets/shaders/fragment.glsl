#version 330 core

in vec4 fragColor;
in vec2 texCoords;

out vec4 outColor;

uniform sampler2D textureImage;

void main()
{
    outColor = texture(textureImage, texCoords);
}

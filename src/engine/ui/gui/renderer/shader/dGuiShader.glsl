#start
#version 400 core
out vec2 pos;

layout(location = 0) in vec2 position;

uniform mat4 transformationMatrix;

void main() {
    gl_Position = transformationMatrix * vec4(position,0.0,1.0);
    pos = position;
}

#start
#version 400 core

struct Properties {
    vec4 fillColor;
    vec4 strokeColor;
    vec2 strokeSize;
    sampler2D icon;
};

in vec2 pos;
out vec4 fragColor;

uniform Properties prop;

void main() {
    if(pos.x > 1.0 - prop.strokeSize.x ||
    pos.x < prop.strokeSize.x - 1.0||
    pos.y > 1.0 - prop.strokeSize.y ||
    pos.y < prop.strokeSize.y - 1.0)
    fragColor = vec4(prop.strokeColor.xyz,1.0);
    else
    fragColor = prop.fillColor;
}
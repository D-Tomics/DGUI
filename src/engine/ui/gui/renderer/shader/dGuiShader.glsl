#start
#version 400 core
out vec2 coords;

layout(location = 0) in vec2 iPos;

out vec2 texCoords;

uniform mat4 transformationMatrix;

void main() {
    gl_Position = transformationMatrix * vec4(iPos,0.0,1.0);
    coords = iPos;
    texCoords = vec2(0.5 * (iPos.x + 1), 0.5 * (iPos.y + 1));
}

#start
#version 400 core

struct Properties {
    vec4 fillColor;
    vec4 strokeColor;
    float borderWidth;
    vec2 dimensions;
    float radius;
    sampler2D icon;
};

uniform sampler2D bgTexture;
in vec2 coords;
in vec2 texCoords;
out vec4 fragColor;

uniform Properties prop;

void main() {
    vec2 dimension = prop.dimensions / 2.0;
    vec2 coord = coords * dimension;
    float e = 1.0 - step(prop.radius,length(min(dimension - prop.radius - abs(coord),0.0))) * (1.0 - step(prop.radius, 0.0));
    float d = length(min(dimension - prop.radius - prop.borderWidth - abs(coord),0.0));
    float c = step(prop.radius, d) * (1.0 - step(d,0.0));
    vec4 color = prop.strokeColor * e * c + prop.fillColor * (1.0 - c) * texture(bgTexture,texCoords);
    fragColor = vec4(color);
}
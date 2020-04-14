#start
#version 400 core
out vec2 coords;

layout(location = 0) in vec2 iPos;

uniform mat4 transformationMatrix;

void main() {
    gl_Position = transformationMatrix * vec4(iPos,0.0,1.0);
    coords = iPos;
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

in vec2 coords;
out vec4 fragColor;

uniform Properties prop;

void main() {
    vec2 dimension = prop.dimensions / 2.0;
    vec2 coord = coords * dimension;
    float e = 1.0 - step(prop.radius,length(min(dimension - prop.radius - abs(coord),0.0))) * (1.0 - step(prop.radius, 0.0));
    float d = length(min(dimension - prop.radius - prop.borderWidth - abs(coord),0.0));
    float c = step(prop.radius, d) * (1.0 - step(d,0.0));
    vec4 color = prop.strokeColor * e * c + prop.fillColor * (1.0 - c);
    fragColor = vec4(color);
}
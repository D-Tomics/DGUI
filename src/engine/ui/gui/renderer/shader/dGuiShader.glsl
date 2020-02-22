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
    float borderWidth;
    vec2 dimensions;
    float radius;
    sampler2D icon;
};

in vec2 pos;
out vec4 fragColor;

uniform Properties prop;

void main() {

    vec2 coords = pos * (prop.dimensions) / 2.0f;

    vec2 topRight = prop.dimensions / 2.0f - prop.radius;
    vec2 topLeft = topRight * vec2(-1,1);
    vec2 bottomRight = topRight * vec2(1,-1);
    vec2 bottomLeft = topLeft * vec2(1,-1);

    if(
        length((coords - topLeft) * (vec2(1,0) - step(topLeft, coords)))  > prop.radius ||
        length((coords - topRight) * step(topRight, coords)) > prop.radius ||
        length((coords - bottomLeft) * (vec2(1) - step(bottomLeft, coords))) > prop.radius ||
        length((coords - bottomRight) * (vec2(0,1) - step(bottomRight, coords))) > prop.radius
    )
        discard;
    if(
        coords.x > prop.dimensions.x / 2.0f - prop.borderWidth  && coords.y < topLeft.y && coords.y > bottomLeft.y||
        coords.x < -prop.dimensions.x / 2.0f + prop.borderWidth && coords.y < topLeft.y && coords.y > bottomLeft.y||
        coords.y > prop.dimensions.y / 2.0f - prop.borderWidth && coords.x < topRight.x && coords.x > topLeft.x ||
        coords.y < -prop.dimensions.y / 2.0f + prop.borderWidth && coords.x < topRight.x && coords.x > topLeft.x
    )
        fragColor = vec4(prop.strokeColor.xyz,1.0);
    else {
        if(
        length(coords - topRight) <= prop.radius && length(coords - topRight) >= prop.radius - prop.borderWidth && coords.x > topRight.x && coords.y > topRight.y ||
        length(coords - topLeft) <= prop.radius && length(coords - topLeft) >= prop.radius - prop.borderWidth && coords.x < topLeft.x && coords.y > topRight.y ||
        length(coords - bottomLeft) <= prop.radius && length(coords - bottomLeft) >= prop.radius - prop.borderWidth && coords.x < bottomLeft.x && coords.y < bottomLeft.y ||
        length(coords - bottomRight) <= prop.radius && length(coords - bottomRight) >= prop.radius - prop.borderWidth && coords.x > bottomRight.x && coords.y < bottomRight.y
        )
        fragColor = vec4(prop.strokeColor.xyz, 1.0f);
        else
        fragColor = prop.fillColor;
    }
}
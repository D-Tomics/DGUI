#start
#version 330 core

layout (location = 0) in vec2 aVertex;
layout (location = 1) in vec2 aTexCoords;

out vec2 vertex;
out vec2 texCoords;

uniform mat4 model;

void main() {
    gl_Position = model *  vec4(aVertex,0,1);
    vertex = vec2(model[0][0] * aVertex.x, model[1][1] * aVertex.y);
    texCoords = aTexCoords;
}

#start
#version 330 core

out vec4 aColor;
in vec2 texCoords;

in vec2 vertex;

uniform sampler2D texAtlas;
uniform vec3 textColor;

uniform float alpha;
uniform float width;
uniform float edge;

uniform vec3 borderColor;
uniform float borderWidth;
uniform float borderEdge;

uniform vec2 offset;
uniform vec2 boxScale;

void main() {

    if(vertex.x + offset.x <= 0) discard;
    if(vertex.x + offset.x >= 2 * boxScale.x) discard;
    if(vertex.y + offset.y >= 0) discard;
    if(vertex.y + offset.y <= -2 * boxScale.y)  discard;

    vec4 texColor = texture(texAtlas,texCoords);

    float distance = 1.0 - texColor.a;
    float a = 1.0 - smoothstep(width,width + edge, distance);

    float borderDistance = 1.0 - texColor.a;
    float borderAlpha = 1.0 - smoothstep(width + borderWidth,width + borderWidth + borderEdge,borderDistance);

    float overAlpha = a + (1.0 - a) * borderAlpha;


    aColor = vec4(mix(borderColor,textColor,a / overAlpha),overAlpha * alpha);

}

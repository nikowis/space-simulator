#version 430 core

const int lightsCount = 10;

layout (triangles) in;
layout (triangle_strip, max_vertices=3) out;

in vec3 surfaceNormal[];
in vec3 toLightVector[][lightsCount];
in vec3 toCameraVector[];

out vec4 finalColour;

uniform sampler2D textureSampler;
uniform vec3 lightColour[lightsCount];
uniform vec3 attenuation[lightsCount];
uniform float shineDamper;
uniform float reflectivty;

void main(void){

    vec4 colour = vec4(1,0,0,1);
    vec3 thisSurfaceNormal = surfaceNormal[0];
    vec3 thisCameraVector = toCameraVector[0];
    vec3 thisToLightVector[lightsCount] = toLightVector[0];

    vec3 unitNormal = normalize(thisSurfaceNormal);
    vec3 unitVectorToCamera = normalize(thisCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    for(int i=0;i<lightsCount;i++) {
            float distance = length(thisToLightVector[i]);
            float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance *distance);
            vec3 unitLightVector = normalize(thisToLightVector[i]);
            float nDot1 = dot(unitNormal, unitLightVector);
            float brightness = max(nDot1, 0.0);

            vec3 lightDirection = -unitLightVector;
            vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
            float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
            specularFactor = max(specularFactor, 0.0);
            float dampedFactor = pow(specularFactor, shineDamper);

            totalDiffuse = totalDiffuse + (brightness * lightColour[i])/attFactor;
            totalSpecular = totalSpecular + (dampedFactor * reflectivty * lightColour[i])/attFactor;
    }
    totalDiffuse = max(totalDiffuse, 0.2);

    finalColour = vec4(totalDiffuse, 1.0) * colour + vec4(totalSpecular, 1.0);


    gl_Position = gl_in[0].gl_Position;
    EmitVertex();

    gl_Position =  gl_in[1].gl_Position;
    EmitVertex();

    gl_Position = gl_in[2].gl_Position;
    EmitVertex();

    EndPrimitive();
}
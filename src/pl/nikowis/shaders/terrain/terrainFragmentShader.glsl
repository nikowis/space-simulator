#version 400 core

const int lightsCount = 10;

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[lightsCount];
in vec3 toCameraVector;

out vec4 out_Color;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;
uniform vec3 attenuation[lightsCount];
uniform vec3 lightColour[lightsCount];
uniform float shineDamper;
uniform float reflectivty;

void main(void){

    vec4 blendMapColour = texture(blendMap, pass_textureCoords);
    float backTextureAmout = 1-(blendMapColour.r + blendMapColour.g + blendMapColour.b);
    vec2 tiledCoords = pass_textureCoords * 40;
    vec4 backgroundTextureColour = texture(backgroundTexture, tiledCoords) * backTextureAmout;
    vec4 rTextureColour = texture(rTexture, tiledCoords) * blendMapColour.r;
    vec4 gTextureColour = texture(gTexture, tiledCoords) * blendMapColour.g;
    vec4 bTextureColour = texture(bTexture, tiledCoords) * blendMapColour.b;
    vec4 totalColour = backgroundTextureColour + rTextureColour + gTextureColour + bTextureColour;

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitVectorToCamera = normalize(toCameraVector);

    for(int i=0;i<lightsCount;i++) {
        float distance = length(toLightVector[i]);
        float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance *distance);

        vec3 unitLightVector = normalize(toLightVector[i]);
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

    out_Color = vec4(totalDiffuse, 1.0) * totalColour + vec4(totalSpecular, 1.0);
}
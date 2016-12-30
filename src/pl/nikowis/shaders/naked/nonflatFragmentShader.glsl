#version 400 core

const int lightsCount = 10;

in vec3 surfaceNormal;
in vec3 toLightVector[lightsCount];
in vec3 toCameraVector;
in vec4 gouraudColor;

out vec4 out_Color;

uniform vec3 lightColour[lightsCount];
uniform vec3 attenuation[lightsCount];
uniform float shineDamper;
uniform float reflectivty;
uniform vec3 baseColour;
uniform float phongShadingEnabled;
uniform float gouraudShadingEnabled;
uniform float phongReflectionEnabled;
uniform float blinnReflectionEnabled;

void main(void){

    if(phongShadingEnabled == 1.0) {
        vec4 colour = vec4(baseColour,1);
        vec3 unitNormal = normalize(surfaceNormal);
        vec3 unitVectorToCamera = normalize(toCameraVector);

        vec3 totalDiffuse = vec3(0.0);
        vec3 totalSpecular = vec3(0.0);
        for(int i=0;i<lightsCount;i++) {
                float distance = length(toLightVector[i]);
                float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance *distance);
                vec3 unitToLightVector = normalize(toLightVector[i]);
                float nDot1 = dot(unitNormal, unitToLightVector);
                float brightness = max(nDot1, 0.0);
                totalDiffuse = totalDiffuse + (brightness * lightColour[i])/attFactor;

                if(phongReflectionEnabled==1.0) {
                    vec3 lightDirection = -unitToLightVector;
                    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
                    float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
                    specularFactor = max(specularFactor, 0.0);
                    float dampedFactor = pow(specularFactor, shineDamper);
                    totalSpecular = totalSpecular + (dampedFactor * reflectivty * lightColour[i])/attFactor;
                } else if(blinnReflectionEnabled == 1.0) {
                    vec3 lightDirection = -unitToLightVector;
                    vec3 halfDir = normalize(-lightDirection + unitVectorToCamera);
                    float specAngle = max(dot(halfDir, unitNormal), 0.0);
                    float dampedFactor = pow(specAngle, shineDamper);
                    totalSpecular = totalSpecular + (dampedFactor * reflectivty * lightColour[i])/attFactor;
                }
        }
        totalDiffuse = max(totalDiffuse, 0.2);
        out_Color = vec4(totalDiffuse, 1.0) * colour ;

        if(phongReflectionEnabled==1.0 || blinnReflectionEnabled == 1.0) {
            out_Color = out_Color + vec4(totalSpecular, 1.0);
        }
    }

    if(gouraudShadingEnabled == 1.0) {
       out_Color = gouraudColor;
    }





}
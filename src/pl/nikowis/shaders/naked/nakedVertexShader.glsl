#version 400 core

const int lightsCount = 10;

in vec3 position;
in vec3 normal;

out vec3 surfaceNormal;
out vec3 toLightVector[lightsCount];
out vec3 toCameraVector;
out vec4 gouraudColor;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[lightsCount];

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
   vec4 worldPosition = transformationMatrix * vec4(position, 1.0);

   gl_Position = projectionMatrix * viewMatrix * worldPosition;

   surfaceNormal = (transformationMatrix * vec4(normal,0.0)).xyz;
   for(int i=0; i<lightsCount; i++) {
     toLightVector[i] = lightPosition[i] - worldPosition.xyz;
   }

   toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;

   if(gouraudShadingEnabled == 1.0) {
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

        gouraudColor = vec4(totalDiffuse, 1.0) * colour;

        if(phongReflectionEnabled==1.0 || blinnReflectionEnabled == 1.0) {
            gouraudColor = gouraudColor + vec4(totalSpecular, 1.0);
        }
   }
}
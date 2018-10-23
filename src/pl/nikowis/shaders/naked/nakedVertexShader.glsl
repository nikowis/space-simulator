#version 400 core

const int lightsCount = 10;

in vec3 position;
in vec3 normal;

out vec3 surfaceNormal;
out vec3 toLightVector[lightsCount];
out vec3 toCameraVector;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[lightsCount];

uniform vec3 lightColour[lightsCount];
uniform vec3 attenuation[lightsCount];
uniform float coneAngle[lightsCount];
uniform vec3 coneDirection[lightsCount];
uniform float shineDamper;
uniform float reflectivty;
uniform vec3 baseColour;

void main(void){
   vec4 worldPosition = transformationMatrix * vec4(position, 1.0);

   gl_Position = projectionMatrix * viewMatrix * worldPosition;

   surfaceNormal = (transformationMatrix * vec4(normal,0.0)).xyz;
   for(int i=0; i<lightsCount; i++) {
     toLightVector[i] = lightPosition[i] - worldPosition.xyz;
   }

   toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
}
#version 400 core

const int lightsCount = 10;

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector[lightsCount];
out vec3 toCameraVector;
out vec3 reflectedVector;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[lightsCount];
uniform vec3 cameraPosition;

uniform float numberOfRows;
uniform vec2 offset;

void main(void){

   vec4 worldPosition = transformationMatrix * vec4(position, 1.0);

   gl_Position = projectionMatrix * viewMatrix * worldPosition;
   pass_textureCoords = (textureCoords/numberOfRows) + offset;

   surfaceNormal = (transformationMatrix * vec4(normal,0.0)).xyz;
   for(int i=0; i<lightsCount; i++) {
     toLightVector[i] = lightPosition[i] - worldPosition.xyz;
   }

   toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;

   vec3 unitNormal = normalize(normal);
   vec3 viewVector = normalize(worldPosition.xyz - cameraPosition);
   reflectedVector = reflect(viewVector, unitNormal);
}
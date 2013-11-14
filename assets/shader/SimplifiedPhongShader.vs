uniform mat4 sg_projectionMatrix;
uniform mat4 sg_modelViewMatrix;

attribute vec3 sg_Position;
attribute vec3 sg_Normal;
attribute vec4 sg_Color;
attribute vec2 sg_TexCoord0;

varying vec3 position;
varying vec3 normal;
varying vec4 color;
varying vec2 texCoord0;

void main() {
	normal = normalize((sg_modelViewMatrix * vec4(sg_Normal, 0.0)).xyz);
	color = sg_Color;
	texCoord0 = sg_TexCoord0;
	
	vec4 eyePosition = sg_modelViewMatrix * vec4(sg_Position, 1.0);
	position = eyePosition.xyz / eyePosition.w;
	gl_Position = sg_projectionMatrix * eyePosition;
} 

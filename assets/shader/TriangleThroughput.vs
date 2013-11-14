uniform mat4 sg_modelViewProjectionMatrix;

attribute vec3 sg_Position;

void main() {
	gl_Position = sg_modelViewProjectionMatrix * vec4(sg_Position, 1.0);
}

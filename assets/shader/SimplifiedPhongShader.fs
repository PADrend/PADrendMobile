#ifdef GL_FRAGMENT_PRECISION_HIGH
precision highp float;
#else
precision mediump float;
#endif

varying vec3 position;
varying vec3 normal;
varying vec4 color;
varying vec2 texCoord0;

struct sg_LightSourceParameters {
	int type; // has to be DIRECTIONAL, POINT or SPOT
	
	vec3 position; // position of the light
	vec3 direction; // direction of the light, has to be normalized
	
	// light colors for all lights
	vec4 ambient;
	vec4 diffuse;
	vec4 specular;
	
	// attenuations for point & spot lights
	float constant;
	float linear;
	float quadratic;
	
	// spot light parameters
	float exponent;
	float cosCutoff;
};
uniform sg_LightSourceParameters sg_LightSource[1];

struct sg_MaterialParameters {
	vec4 ambient;
	vec4 diffuse;
	vec4 specular;
	float shininess;
};
uniform sg_MaterialParameters sg_Material;
uniform bool sg_useMaterials;

uniform bool sg_textureEnabled[8];
uniform sampler2D sg_texture0;

void pointLight(in vec3 pixelNormal, inout vec4 ambient, inout vec4 diffuse, inout vec4 specular) {
	vec3 lightDir = sg_LightSource[0].position - position;
	float distance = length(lightDir);
	lightDir = normalize(lightDir);
	float attenuation = 1.0 / (	sg_LightSource[0].constant +
								sg_LightSource[0].linear * distance +
								sg_LightSource[0].quadratic * distance * distance);
								
	ambient += sg_LightSource[0].ambient * attenuation;
	
	float norDotL = max(0.0, dot(pixelNormal, lightDir));
	if(norDotL != 0.0) {
		diffuse += sg_LightSource[0].diffuse * norDotL * attenuation;
		
		if(sg_useMaterials) {
			vec3 viewDir = normalize(-position);
			vec3 reflected = reflect(-lightDir, pixelNormal);
			float viewDotReflected = dot(viewDir, reflected);
			if(viewDotReflected > 0.0) {
				specular += sg_LightSource[0].specular * pow(viewDotReflected, sg_Material.shininess / 4.0) * attenuation;
			}
		}
	}
}

void main() {
	vec3 pixelNormal = normalize(normal);
		
	vec4 ambient = vec4(0.0);
	vec4 diffuse = vec4(0.0);
	vec4 specular = vec4(0.0);

	pointLight(pixelNormal, ambient, diffuse, specular);
	
	vec4 outputColor;
   	if (!sg_useMaterials) {
		outputColor = vec4((ambient + diffuse + specular).rgb, 1.0) * color;
	} else {
		outputColor = vec4((ambient * sg_Material.ambient + diffuse * sg_Material.diffuse + specular * sg_Material.specular).rgb, 1.0);
	}
	if(sg_textureEnabled[0]) {
		outputColor *= texture2D(sg_texture0, texCoord0);
	}
	gl_FragColor = outputColor;
} 

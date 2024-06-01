#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler3;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
in vec3 normal;
in vec3 position;
in mat4 modelViewMat;

out vec4 fragColor;

void main() {
	vec3 normalMap = texture(Sampler0, texCoord0).rgb;
	normalMap = normalize(normalMap * 2.0 - 1.0);

	vec3 e = normalize(vec3(modelViewMat * vec4(position, 1)));
	vec3 n = normalize(normal + normalize(normalMap));
	vec3 r = reflect(e, n);
	float m = 2 * sqrt(
		pow(r.x, 2) +
		pow(r.y, 2) +
		pow(r.z + 1, 2)
	);
	vec2 texPos = vec2(r.x, -r.y) / m + 0.5;

	vec4 color = texture(Sampler3, texPos);
	color *= vertexColor * ColorModulator;
	color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
	color *= lightMapColor;
	fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}

varying vec4 vColor;
varying vec2 vTexCoord;

uniform sampler2D u_texture;

void main() {

	vec4 texColor = texture2D(u_texture, vTexCoord) * vColor;

	texColor.rgb = mix(texColor.rgb, texColor.rgb, 0.5);
		
	gl_FragColor = vec4(texColor.rgb, texColor.a);
}
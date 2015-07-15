
varying vec4 vColor;
varying vec2 vTexCoord;

uniform sampler2D u_texture;

uniform float blurSize;

void main() {

	vec4 texColor = vec4(0.0); // texture2D(u_texture, vTexCoord)
	texColor += texture2D(u_texture, vTexCoord - 4.0*blurSize) * 0.05;
   	texColor += texture2D(u_texture, vTexCoord - 3.0*blurSize) * 0.09;
  	texColor += texture2D(u_texture, vTexCoord - 2.0*blurSize) * 0.12;
   	texColor += texture2D(u_texture, vTexCoord - blurSize) * 0.15;
   	texColor += texture2D(u_texture, vTexCoord) * 0.16;
   	texColor += texture2D(u_texture, vTexCoord + blurSize) * 0.15;
   	texColor += texture2D(u_texture, vTexCoord + 2.0*blurSize) * 0.12;
   	texColor += texture2D(u_texture, vTexCoord + 3.0*blurSize) * 0.09;
   	texColor += texture2D(u_texture, vTexCoord + 4.0*blurSize) * 0.05;

	texColor.rgb = mix(texColor.rgb, texColor.rgb, 0.5);
		
	gl_FragColor = vec4(texColor.rgb * vColor.rgb, texColor.a);
}
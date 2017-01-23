#ifdef GL_ES
precision highp float;
#endif
varying vec4 v_color;
varying vec2 v_texCoords;

// light origins
uniform float f_points[40];
uniform float f_clearRadii[40];
uniform float f_fadeRadii[40];
uniform int i_lightsPassed;

uniform sampler2D u_texture;

float getBrightness(int lightNum) {
    // if we are in the fade zone, figure out how faded
    float distance = distance( gl_FragCoord.xy, vec2(f_points[lightNum*2], f_points[lightNum*2+1]) );
    float pointPosition = abs(distance - f_clearRadii[lightNum]);

    float percentBrightness = 1.0;

    if ( distance >= f_clearRadii[lightNum] + f_fadeRadii[lightNum] ) {
        percentBrightness = 0.0;
    } else if (distance > f_clearRadii[lightNum]) {
        // if we are in the fade zone, figure out how faded
        percentBrightness = 1.0 - (pointPosition / f_fadeRadii[lightNum] * 5.0);

        if ( percentBrightness < 0.01 ) {
            percentBrightness = 0.0;
        }
    }

    return percentBrightness;
}

void main()
{
    float brightness = 0.0;
    for(int i=0;i<i_lightsPassed;i++)
    {
      brightness = max(brightness, getBrightness(i));
    }

    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
    gl_FragColor.a = brightness;
}
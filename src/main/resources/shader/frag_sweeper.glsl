#ifdef GL_ES
precision mediump float;
#endif
varying vec4 v_color;
varying vec2 v_texCoords;

uniform vec2 v_center;
uniform float f_sweepRadius;

uniform float f_delta;

uniform float f_sweepFadeDistance;
uniform float f_colorFadeDistance;

uniform sampler2D u_texture;
void main()
{
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);

    // track if we were on a non-transparent pixel
    bool nonTransparent = gl_FragColor.a != 0.0;

    // blank out every pixel
    gl_FragColor.a = 0.0;

    // check our neighbors
    vec4 top = texture2D( u_texture, vec2(v_texCoords.x, v_texCoords.y + f_delta) );
    vec4 bottom = texture2D( u_texture, vec2(v_texCoords.x, v_texCoords.y - f_delta) );
    vec4 right = texture2D( u_texture, vec2(v_texCoords.x + f_delta, v_texCoords.y) );
    vec4 left = texture2D( u_texture, vec2(v_texCoords.x - f_delta, v_texCoords.y) );

    // find our distance from the center point
    float distance =  distance(v_texCoords, v_center);

    if ( abs(distance - f_sweepRadius) < f_delta / 2.0 ) {
        gl_FragColor = vec4( 0.0, 0.3, 0.0, 1.0 );
    }

    // if we are inside the sweep donut
    if ( distance <= f_sweepRadius ) {
        float pointPosition = f_sweepRadius - distance;

        float percentBrightness = 1.0 - (pointPosition / f_sweepFadeDistance);

        if ( nonTransparent )
        {
            if (top.a == 0.0 || bottom.a == 0.0 || left.a == 0.0 || right.a == 0.0)
            {
                gl_FragColor = vec4(0.0, 1.0 * percentBrightness, 0.0, 1.0);
            } else {
                percentBrightness = 1.0 - (pointPosition / f_colorFadeDistance);

                gl_FragColor.r *= percentBrightness;
                gl_FragColor.g *= percentBrightness;
                gl_FragColor.b *= percentBrightness;
                gl_FragColor.a = 1.0;
            }
        }
    }
}
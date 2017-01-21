#ifdef GL_ES
precision mediump float;
#endif
varying vec4 v_color;
varying vec2 v_texCoords;

uniform vec2 v_center;
uniform float f_largeRadius;
uniform float f_smallRadius;
uniform float f_fullColorStopRadius;

uniform sampler2D u_texture;
void main()
{
    float delta = 0.002;

    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);

    // track if we were on a non-transparent pixel
    bool nonTransparent = gl_FragColor.a != 0.0;

    // blank out every pixel
    gl_FragColor.a = 0.0;

    // check our neighbors
    vec4 top = texture2D( u_texture, vec2(v_texCoords.x, v_texCoords.y + delta) );
    vec4 bottom = texture2D( u_texture, vec2(v_texCoords.x, v_texCoords.y - delta) );
    vec4 right = texture2D( u_texture, vec2(v_texCoords.x + delta, v_texCoords.y) );
    vec4 left = texture2D( u_texture, vec2(v_texCoords.x - delta, v_texCoords.y) );

    // find our distance from the center point
    float distance =  distance(v_texCoords, v_center);

    if ( abs(distance - f_largeRadius) < delta / 2.0 ) {
        gl_FragColor = vec4( 0.0, 0.3, 0.0, 1.0 );
    }

    // if we are inside the sweep donut
    if ( distance >= f_smallRadius && distance <= f_largeRadius ) {
        float donutWidth = f_largeRadius - f_smallRadius;
        float pointPosition = f_largeRadius - distance;

        float percentBrightness = 1.0 - (pointPosition / donutWidth);

        if ( nonTransparent )
        {
            if (top.a == 0.0 || bottom.a == 0.0 || left.a == 0.0 || right.a == 0.0)
            {
                gl_FragColor = vec4(0.0, 1.0 * percentBrightness, 0.0, 1.0);
            } else {
                float colorDonutWidth = f_largeRadius - f_fullColorStopRadius;
                percentBrightness = 1.0 - (pointPosition / colorDonutWidth);

                gl_FragColor.r *= percentBrightness;
                gl_FragColor.g *= percentBrightness;
                gl_FragColor.b *= percentBrightness;
                gl_FragColor.a = 1.0;
            }
        }
    }
}
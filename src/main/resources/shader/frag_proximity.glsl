#ifdef GL_ES
precision highp float;
#endif
varying vec4 v_color;
varying vec2 v_texCoords;

// sonar ping origin
uniform vec2 v_center;

// how far the player can see clearly
uniform float f_clearRadius;

// how far from the clearRadius before fade to black
uniform float f_fadeRange;

uniform sampler2D u_texture;
void main()
{
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);

    // find our distance from the center point
    vec2 screenPosition = gl_FragCoord.xy; // / v_resolution;

    float distance = distance( screenPosition, v_center );

    // if we are outside the proximity, blackness
    if ( distance >= f_clearRadius + f_fadeRange ) {
        gl_FragColor = vec4( 0.0, 0.0, 0.0, 0.0 );
    } else if (distance > f_clearRadius) {
        // if we are in the fade zone, figure out how faded
        float pointPosition = abs(distance - f_clearRadius);

        float percentBrightness = 1.0 - (pointPosition / f_fadeRange * 5.0);

        gl_FragColor.r *= percentBrightness;
        gl_FragColor.g *= percentBrightness;
        gl_FragColor.b *= percentBrightness;
        gl_FragColor.a = percentBrightness;
        if ( percentBrightness < 0.01 ) {
            gl_FragColor.a = 0.0;
        }
    }

// These are just debugging circles for the light
//    if ( abs(distance - ( f_clearRadius + f_fadeRange) ) < 1.0 ) {
//        // render the outer limit
//        gl_FragColor = vec4( 0.0, 0.3, 0.0, 1.0 );
//    }
//
//    if ( abs(distance - f_clearRadius ) < 1.0 ) {
//            // render the close range
//            gl_FragColor = vec4( 0.0, 0.3, 0.0, 1.0 );
//        }
}
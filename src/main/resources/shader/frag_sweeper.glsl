#ifdef GL_ES
precision highp float;
#endif
varying vec4 v_color;
varying vec2 v_texCoords;

// sonar ping origin
uniform vec2 v_center;

// the forward edge of the ping
uniform float f_sweepRadius;

// distance to look for neighbor pixels. Larger values will result in thicker sweep lines
uniform float f_deltaX;
uniform float f_deltaY;

// distance sweep lines should fade over
uniform float f_sweepFadeDistance;

// distance full color should fade over
uniform float f_colorFadeDistance;

uniform sampler2D u_texture;
void main()
{
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);

    // track if we were on a non-transparent pixel
    bool nonTransparent = gl_FragColor.a != 0.0;

//    if ( gl_FragColor.a == 1.0) {
//        gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
//    } else if (gl_FragColor.a == 0.0 ) {
//        gl_FragColor = vec4(0.0, 1.0, 0.0, 1.0);
//    } else {
//        gl_FragColor = vec4(0.0, 0.0, 1.0, 1.0);
//    }

    // blank out every pixel
//    gl_FragColor.a = 0.0;

    // check our neighbors
    vec4 top = texture2D( u_texture, vec2(v_texCoords.x, v_texCoords.y + f_deltaY) );
    vec4 bottom = texture2D( u_texture, vec2(v_texCoords.x, v_texCoords.y - f_deltaY) );
    vec4 right = texture2D( u_texture, vec2(v_texCoords.x + f_deltaX, v_texCoords.y) );
    vec4 left = texture2D( u_texture, vec2(v_texCoords.x - f_deltaX, v_texCoords.y) );


//    if (gl_FragColor.a == 1.0) {
//        gl_FragColor = vec4( 0.0, 0.0, 0.0, 1.0 );
//        if (top.a != 1.0 || bottom.a != 1.0 || left.a != 1.0 || right.a != 1.0) {
//            gl_FragColor = vec4( 0.8, 0.0, 0.0, 1.0 );
//        }
//    }

//    if (gl_FragColor.a != 1.0) {
//        gl_FragColor = vec4( 0.0, 0.0, 0.0, 1.0 );
//        if (top.a != 0.0 || bottom.a != 0.0 || left.a != 0.0 || right.a != 0.0) {
//            gl_FragColor = vec4( 0.8, 0.0, 0.0, 1.0 );
//        }
//    }

    // find our distance from the center point
    vec2 screenPosition = gl_FragCoord.xy;// / v_resolution;

    float distance = distance( screenPosition, v_center );

    if ( abs(distance - f_sweepRadius) < 2.0 ) {
        // render the forward face of the ping wave
        gl_FragColor = vec4( 0.0, 0.3, 0.0, 1.0 );
    }

    // if we are inside the sweep donut
    if ( distance <= f_sweepRadius ) {
        float pointPosition = f_sweepRadius - distance;

        float percentBrightness;

        if ( nonTransparent )
        {
            percentBrightness = 1.0 - (pointPosition / f_colorFadeDistance);

            gl_FragColor.r *= percentBrightness;
            gl_FragColor.g *= percentBrightness;
            gl_FragColor.b *= percentBrightness;
            gl_FragColor.a = percentBrightness ;
            if ( percentBrightness < 0.01 ) {
                gl_FragColor.a = 0.0;
            }
        } else if (top.a != 0.0 || bottom.a != 0.0 || left.a != 0.0 || right.a != 0.0) {
            percentBrightness = 1.0 - (pointPosition / f_sweepFadeDistance);
            gl_FragColor = vec4(0.0, 1.0 * percentBrightness, 0.0, 1.0);
        }
    } else {
        gl_FragColor = vec4( 0.0, 0.0, 0.0, 1.0 );
    }
}

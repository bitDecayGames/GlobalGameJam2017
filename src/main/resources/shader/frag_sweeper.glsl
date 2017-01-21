#ifdef GL_ES
precision mediump float;
#endif
varying vec4 v_color;
varying vec2 v_texCoords;

uniform vec2 v_center;
uniform float f_largeRadius;
uniform float f_smallRadius;

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

    // if we are inside the sweep donut
    if ( distance >= f_smallRadius && distance <= f_largeRadius ) {
        float donutWidth = f_largeRadius - f_smallRadius;
        float pointPosition = f_largeRadius - distance;

        float percentBrightness = 1.0 - (pointPosition / donutWidth);

        if ( nonTransparent && (top.a == 0.0 || bottom.a == 0.0 || left.a == 0.0 || right.a == 0.0) )
        {
            gl_FragColor.g = 1.0 * percentBrightness;
            gl_FragColor.a = 1.0;
        }
    }
}
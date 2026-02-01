#version 150

uniform float GameTime;
uniform float AnimationTime;
uniform vec2 ScreenSize;
uniform vec4 Mouse;

#define iTime ((GameTime + AnimationTime) * 1000)
#define iResolution vec3(ScreenSize, 0.0)
#define iMouse Mouse

out vec4 fragColor;

void mainImage(out vec4 O, vec2 I) {
    float t = iTime, i, z, d, s;
    vec2 cPos = -1.0 + 2.0 * I.xy / iResolution.xy;

    vec2 uv3 = (I.xy / iResolution.xy) - .5;
    float t3 = iTime * .1 + ((.25 + .05 * sin(iTime * .1)) / (length(uv3.xy) + .07)) * 2.2;
    float si = sin(t3);
    float co = cos(t3);
    mat2 ma = mat2(co, si, -si, co);
    float t4 = 1. / (length(uv3.xy) + .07) * 0.2 + iTime * 0.1;
    float si2 = sin(t4);
    float co2 = cos(t4);
    mat2 ma2 = mat2(co2, si2, -si2, co2);
    float v1, v2, v3;
    v1 = v2 = v3 = 0.0;

    float s3 = 0.0;
    for(int i = 0; i < 90; i++) {
        vec3 p = s3 * normalize(vec3(uv3, 0.04));
        p.xz *= ma2;

        p += vec3(.22, .3, s3 - 1.5 - sin(iTime * .13) * .1);
        for(int i = 0; i < 8; i++) p = abs(p) / dot(p, p) - 0.659;
        v1 += dot(p, p) * .0015 * (1.8 + sin(length(uv3.xy * 13.0) + .5 - iTime * .2));
        v2 += dot(p, p) * .0013 * (1.5 + sin(length(uv3.xy * 14.5) + 1.2 - iTime * .3));
        v3 += length(p.xy * 10.) * .0003;
        s3 += .035;
    }

    float len = length(uv3);
    v1 *= smoothstep(.7, .0, len);
    v2 *= smoothstep(.7, .0, len);
    v3 *= smoothstep(.7, .0, len);

    vec3 col = vec3(v3 * (1.5 + sin(iTime * .2) * .4), (v1 + v3) * .3, v2) + smoothstep(0.2, .0, len) * .85 + smoothstep(.0, .6, v3) * .3;

    float cLength = length(cPos);

    vec2 uv = (cPos / cLength) * (cLength * 1.50 - iTime * 1.0) * 0.01;

    for(O *= i; i++ < 1e2;) {
        vec3 p = z * normalize(vec3(I + I, 0) - iResolution.xyy);

        p.z -= iTime * 1.;

        for(d = 5.; d < 2e2; d += d) p += 0.7 * sin(p.yzx * d - .2 * t) / d;

        z += d = .0075 + max(s = .3 - abs(p.y * p.x), -s * .2) / 4.;
        O += (cos(s / .07 + p.x + .5 * t - vec4(0, 1, 2, 3) - 3.) + 1.5) * exp(s / .1) / d;
    }
    O = tanh(O * O / 4e8);
    O.rgb += col * 0.5;
}

void main() {
    mainImage(fragColor, gl_FragCoord.xy);
}
#version 300 es

precision mediump float;

vec4 SRC;
vec4 DST;
vec4 COLOR;
vec2 UV;
vec2 SIZE;

//<indigo-fragment>
layout (std140) uniform IndigoBlendEffectsData {
  highp vec4 ALPHA_SATURATION_OVERLAYTYPE_BG;
  vec4 TINT;
  vec4 GRADIENT_FROM_TO;
  vec4 GRADIENT_FROM_COLOR;
  vec4 GRADIENT_TO_COLOR;
};

vec4 applyBasicEffects(vec4 textureColor) {
  float alpha = ALPHA_SATURATION_OVERLAYTYPE_BG.x;
  vec4 withAlpha = vec4(textureColor.rgb * alpha, textureColor.a * alpha);
  vec4 tintedVersion = vec4(withAlpha.rgb * TINT.rgb, withAlpha.a);

  return tintedVersion;
}

vec4 calculateColorOverlay(vec4 color) {
  return mix(color, vec4(GRADIENT_FROM_COLOR.rgb * color.a, color.a), GRADIENT_FROM_COLOR.a);
}

vec4 calculateLinearGradientOverlay(vec4 color) {
  vec2 pointA = vec2(GRADIENT_FROM_TO.x, SIZE.y - GRADIENT_FROM_TO.y);
  vec2 pointB = vec2(GRADIENT_FROM_TO.z, SIZE.y - GRADIENT_FROM_TO.w);
  vec2 pointP = UV * SIZE;

  // `h` is the distance along the gradient 0 at A, 1 at B
  float h = min(1.0, max(0.0, dot(pointP - pointA, pointB - pointA) / dot(pointB - pointA, pointB - pointA)));

  vec4 gradient = mix(GRADIENT_FROM_COLOR, GRADIENT_TO_COLOR, h);

  return mix(color, vec4(gradient.rgb * color.a, color.a), gradient.a);
}

vec4 calculateRadialGradientOverlay(vec4 color) {
  vec2 pointA = vec2(GRADIENT_FROM_TO.x, SIZE.y - GRADIENT_FROM_TO.y);
  vec2 pointB = vec2(GRADIENT_FROM_TO.z, SIZE.y - GRADIENT_FROM_TO.w);
  vec2 pointP = UV * SIZE;

  float radius = length(pointB - pointA);
  float distanceToP = length(pointP - pointA);

  float sdf = clamp(-((distanceToP - radius) / radius), 0.0, 1.0);
  // float sdf = (distanceToP - radius) / radius;

  vec4 gradient = mix(GRADIENT_TO_COLOR, GRADIENT_FROM_COLOR, sdf);

  return mix(color, vec4(gradient.rgb * color.a, color.a), gradient.a);
}

vec4 calculateSaturation(vec4 color) {
  float saturation = ALPHA_SATURATION_OVERLAYTYPE_BG.y;
  float average = (color.r + color.g + color.b) / float(3.0);
  vec4 grayscale = vec4(average, average, average, color.a);

  return mix(grayscale, color, max(0.0, min(1.0, saturation)));
}

void fragment(){

  int affectsBg = int(round(ALPHA_SATURATION_OVERLAYTYPE_BG.w));
  vec4 affects;

  switch(affectsBg) {
    case 0:
      affects = SRC;
      break;

    case 1:
      affects = mix(DST, SRC, SRC.a);
      break;

    default:
      affects = SRC;
      break;
  }

  vec4 baseColor = applyBasicEffects(affects);

  // 0 = color; 1 = linear gradient; 2 = radial gradient
  int overlayType = int(round(ALPHA_SATURATION_OVERLAYTYPE_BG.z));
  vec4 overlay;

  switch(overlayType) {
    case 0:
      overlay = calculateColorOverlay(baseColor);
      break;

    case 1:
      overlay = calculateLinearGradientOverlay(baseColor);
      break;

    case 2:
      overlay = calculateRadialGradientOverlay(baseColor);
      break;

    default:
      overlay = calculateColorOverlay(baseColor);
      break;
  }

  vec4 saturation = calculateSaturation(overlay);

  COLOR = saturation;
}
//</indigo-fragment>

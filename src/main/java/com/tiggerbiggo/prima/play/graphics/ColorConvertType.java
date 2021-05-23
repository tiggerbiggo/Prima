package com.tiggerbiggo.prima.play.graphics;

import java.awt.Color;
import java.util.function.Function;

public enum ColorConvertType{
  H(ColorTools::getHue),
  S(ColorTools::getSaturation),
  V(ColorTools::getBrightness),
  R(c -> c.getRed()/255f),
  G(c -> c.getGreen()/255f),
  B(c -> c.getBlue()/255f);

  Function<Color, Float> convertFunction;

  ColorConvertType(Function<Color, Float> _convertFunction){
    convertFunction = _convertFunction;
  }

  public double convertColor(Color c){
    if(c == null) return 0;

    return convertFunction.apply(c);
  }
}
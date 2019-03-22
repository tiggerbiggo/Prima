package com.tiggerbiggo.prima.play.graphics;

import java.awt.Color;
import java.util.function.Function;

public enum ColorConvertType{
  H(ColorTools::getHue),
  S(ColorTools::getSaturation),
  V(ColorTools::getBrightness),
  R(c -> c.getRed()/255d),
  G(c -> c.getGreen()/255d),
  B(c -> c.getBlue()/255d);

  Function<Color, Double> convertFunction;

  ColorConvertType(Function<Color, Double> _convertFunction){
    convertFunction = _convertFunction;
  }

  public double convertColor(Color c){
    if(c == null) return 0;

    return convertFunction.apply(c);
  }
}
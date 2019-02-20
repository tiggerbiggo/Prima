package com.tiggerbiggo.prima.play.graphics;

import java.awt.Color;
import java.util.function.Function;

public enum ColorConvertType{
  H(ColorTools::getHue),
  S(ColorTools::getSaturation),
  V(ColorTools::getBrightness),
  R(c -> (double)c.getRed()/255),
  G(c -> (double)c.getGreen()/255),
  B(c -> (double)c.getBlue()/255);

  Function<Color, Double> convertFunction;

  ColorConvertType(Function<Color, Double> _convertFunction){
    convertFunction = _convertFunction;
  }

  public double convertColor(Color c){
    if(c == null) return 0;

    return convertFunction.apply(c);
  }
}
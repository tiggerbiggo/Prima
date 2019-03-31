package com.tiggerbiggo.prima.play.graphics;

import java.awt.Color;
import java.util.function.BiFunction;

public enum ColorAction{
  ADD("Add", ColorTools::add),
  SUB("Subtract", ColorTools::subtract),
  MUL("Multiply", ColorTools::multiply),
  DIV("Divide", ColorTools::divide),
  INV("Invert", (c1, c2) -> {return ColorTools.invert(c1);});

  String name;
  BiFunction<Color, Color, Color> func;

  ColorAction(String _name, BiFunction<Color, Color, Color> _func){
    func = _func;
    name = _name;
  }

  public Color transform(Color A, Color B){
    return func.apply(A, B);
  }


  @Override
  public String toString() {
    return name;
  }
}

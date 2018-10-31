//Unfinished class, do not use.

package com.tiggerbiggo.prima.primaplay.node.implemented.io;

import com.tiggerbiggo.prima.primaplay.graphics.ColorTools;
import com.tiggerbiggo.prima.primaplay.node.core.NodeInOut;
import java.awt.Color;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ColorEditNode{//} extends NodeInOut {
  
  
  //@Override
  public String getName() {
    return null;
  }

  //@Override
  public String getDescription() {
    return null;
  }
}

/*enum ColorAction{
  ADD("Add", new BiFunction<Color, Color, Color>() {
    @Override
    public Color apply(Color color, Color color2) {

      return ColorTools.;
    }
  }),
  SUB,
  MUL,
  DIV;
  
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
*/
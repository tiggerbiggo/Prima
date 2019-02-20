//Unfinished class, do not use.

package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.ColorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.ColorOutputLink;
import java.awt.Color;
import java.util.function.BiFunction;

public class ColorEditNode extends NodeInOut {

  @TransferGrid
  ColorAction action = ColorAction.ADD;

  ColorInputLink inA, inB;
  ColorOutputLink out;

  public ColorEditNode(){
    inA = new ColorInputLink();
    inB = new ColorInputLink();
    out = new ColorOutputLink() {
      @Override
      public Color get(RenderParams p) {
        return action.transform(inA.get(p), inB.get(p));
      }
    };

    addInput(inA, inB);
    addOutput(out);
  }

  //@Override
  public String getName() {
    return "Color Edit Node";
  }

  //@Override
  public String getDescription() {
    return "Allows transformation of colors.";
  }
}

enum ColorAction{
  ADD("Add", ColorTools::add),
  SUB("Subtract", ColorTools::subtract),
  MUL("Multiply", ColorTools::multiply),
  DIV("Divide", ColorTools::divide);
  
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

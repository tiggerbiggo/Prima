package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.Color;

public abstract class NumberArrayOutputLink extends OutputLink<Double[]> {
  public NumberArrayOutputLink(String desc){
    this.desc = desc;
  }

  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof NumberArrayInputLink;
  }

  @Override
  public String getStyleClass() {
    return "NumberArrayLink";
  }

  @Override
  public String describeValue(RenderParams p, int currentFrame) {
    Double[] got = get(p);

    if(got == null) return "null";
    if(currentFrame <0 || currentFrame >= got.length) return "Frame num outside bounds. Tell tiggerbiggo he's a bad coder.";

    return "Double: " + got[currentFrame];
  }

  @Override
  public Color[] getColors(RenderParams p) {
    Color[] toReturn = new Color[p.frameNum()];
    Double[] got = get(p);
    for(int i=0; i<p.frameNum(); i++){
      toReturn[i] = ColorTools.colorLerp(Color.BLACK, Color.WHITE, Calculation.modLoop(got[i], true));
    }

    return toReturn;
  }

  @Override
  public String getReturnType() {
    return "float";
  }

  @Override
  public boolean isSingular() {
    return false;
  }
}

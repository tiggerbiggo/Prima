package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import java.awt.Color;

public abstract class ColorOutputLink extends OutputLink<Color> {
  public ColorOutputLink(String desc){
    this.desc = desc;
  }

  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof ColorInputLink;
  }

  @Override
  public String getStyleClass() {
    return "ColorLink";
  }

  @Override
  public String describeValue(RenderParams p, int currentFrame) {
    Color got = get(p);

    if(got == null) return "null";

    return "Color\nR: " + got.getRed() + "\nG: " + got.getGreen() + "\nB: " + got.getBlue();
  }

  @Override
  public Color[] getColors(RenderParams p) {
    return ColorTools.colorArray(p.frameNum(), get(p));
  }

  @Override
  public String getReturnType() {
    return "vec4";
  }

  @Override
  public boolean isSingular() {
    return true;
  }
}

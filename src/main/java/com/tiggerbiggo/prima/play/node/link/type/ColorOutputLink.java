package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import java.awt.Color;

public abstract class ColorOutputLink extends OutputLink<Color> {
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
}

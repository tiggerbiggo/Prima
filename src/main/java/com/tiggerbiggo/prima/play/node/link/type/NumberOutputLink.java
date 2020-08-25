package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import java.awt.Color;

public abstract class NumberOutputLink extends OutputLink<Double> {
  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof NumberInputLink;
  }

  @Override
  public String getStyleClass() {
    return "NumberLink";
  }

  @Override
  public String describeValue(RenderParams p, int currentFrame) {
    Double got = get(p);

    if(got == null) return "null";
    return "Double: " + got;
  }

  @Override
  public Color getColor(RenderParams p) {
    return ColorTools.colorLerp(Color.BLACK, Color.WHITE, Calculation.modLoop(get(p.asSingleFrame()), true));
  }
}

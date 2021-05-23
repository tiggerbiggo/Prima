package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import java.awt.Color;

public abstract class NumberOutputLink extends OutputLink<Float> {
  public NumberOutputLink(String desc){this.desc = desc;}

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
    Float got = get(p);

    if(got == null) return "null";
    return "Float: " + got;
  }

  @Override
  public boolean isSingular() {
    return true;
  }

  @Override
  public String getReturnType() {
    return "float";
  }

  @Override
  public Color[] getColors(RenderParams p) {
    return ColorTools.colorArray(
            p.frameNum(),
            ColorTools.colorLerp(
                    Color.BLACK,
                    Color.WHITE,
                    Calculation.modLoop(
                            get(
                                    p.asSingleFrame()),
                            true
                    )
            )
    );
  }
}

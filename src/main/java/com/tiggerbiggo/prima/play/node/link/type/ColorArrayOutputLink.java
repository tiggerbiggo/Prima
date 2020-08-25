package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import java.awt.Color;

public abstract class ColorArrayOutputLink extends OutputLink<Color[]> {
  public ColorArrayOutputLink(String desc){
    this.desc = desc;
  }

  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof ColorArrayInputLink;
  }
  @Override
  public String getStyleClass() {
    return "ColorArrayLink";
  }

  @Override
  public String describeValue(RenderParams p, int currentFrame) {
    Color[] got = get(p);

    if(got == null) return "null";
    if(currentFrame < 0 || currentFrame > got.length) return "Frame number was out of bounds. Tell tiggerbiggo to quit coding forever.";

    return
        "Color\nR: " + got[currentFrame].getRed()
        + "\nG: " + got[currentFrame].getGreen()
        + "\nB: " + got[currentFrame].getBlue();
  }

  @Override
  public Color[] getColors(RenderParams p) {
    return get(p);
  }

  @Override
  public String getReturnType() {
    return "vec4";
  }

  @Override
  public boolean isSingular() {
    return false;
  }
}

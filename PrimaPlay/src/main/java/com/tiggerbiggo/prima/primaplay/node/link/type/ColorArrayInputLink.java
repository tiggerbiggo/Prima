package com.tiggerbiggo.prima.primaplay.node.link.type;

import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.graphics.ColorTools;
import com.tiggerbiggo.prima.primaplay.node.link.InputLink;
import com.tiggerbiggo.prima.primaplay.node.link.Link;
import com.tiggerbiggo.prima.primaplay.node.link.OutputLink;
import java.awt.Color;

public class ColorArrayInputLink extends InputLink<Color[]> {

  @Override
  public boolean link(OutputLink<?> toLink) {
    if (canLink(toLink)) {
      currentLink = (ColorArrayOutputLink) toLink;
      return true;
    }
    return false;
  }

  @Override
  public Color[] defaultValue(RenderParams p) {
    return ColorTools.blankArray(p.frameNum());
  }

  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof ColorArrayOutputLink;
  }

  @Override
  public String getStyleClass() {
    return "ColorArrayLink";
  }
}

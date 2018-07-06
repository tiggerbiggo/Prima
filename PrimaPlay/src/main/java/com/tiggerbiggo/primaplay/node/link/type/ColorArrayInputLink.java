package com.tiggerbiggo.primaplay.node.link.type;

import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.Link;
import com.tiggerbiggo.primaplay.node.link.OutputLink;
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
  public boolean canLink(Link other) {
    if (other == null) {
      return false;
    }
    return other instanceof ColorArrayOutputLink;
  }
}

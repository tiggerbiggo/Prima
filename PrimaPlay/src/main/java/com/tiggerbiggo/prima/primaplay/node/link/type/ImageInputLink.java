package com.tiggerbiggo.prima.primaplay.node.link.type;

import com.tiggerbiggo.prima.primaplay.graphics.SafeImage;
import com.tiggerbiggo.prima.primaplay.node.link.InputLink;
import com.tiggerbiggo.prima.primaplay.node.link.Link;
import com.tiggerbiggo.prima.primaplay.node.link.OutputLink;
import java.awt.Color;

public class ImageInputLink extends InputLink<SafeImage> {

  @Override
  public boolean link(OutputLink<?> toLink) {
    if (canLink(toLink)) {
      currentLink = (ImageOutputLink) toLink;
      return true;
    }
    return false;
  }

  @Override
  public boolean canLink(Link other) {
    return other != null && other instanceof ImageOutputLink;
  }
}

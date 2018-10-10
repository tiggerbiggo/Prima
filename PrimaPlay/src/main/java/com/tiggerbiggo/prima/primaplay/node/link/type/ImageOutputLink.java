package com.tiggerbiggo.prima.primaplay.node.link.type;

import com.tiggerbiggo.prima.primaplay.graphics.SafeImage;
import com.tiggerbiggo.prima.primaplay.node.link.Link;
import com.tiggerbiggo.prima.primaplay.node.link.OutputLink;

public abstract class ImageOutputLink extends OutputLink<SafeImage>{
  @Override
  public boolean canLink(Link other) {
    return other != null && other instanceof ImageInputLink;
  }

  @Override
  public String getStyleClass() {
    return "ImageLink";
  }
}

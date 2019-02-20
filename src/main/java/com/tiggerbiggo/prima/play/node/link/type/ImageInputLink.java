package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ImageTools;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.link.InputLink;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;

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
  public SafeImage defaultValue(RenderParams p) {
    return ImageTools.blankImage();
  }

  @Override
  public boolean canLink(Link other) {
    return other != null && other instanceof ImageOutputLink;
  }

  @Override
  public String getStyleClass() {
    return "ImageLink";
  }
}

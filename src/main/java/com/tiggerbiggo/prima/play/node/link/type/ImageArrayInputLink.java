package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ImageTools;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.link.InputLink;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;

public class ImageArrayInputLink extends InputLink<SafeImage[]> {

  @Override
  public boolean link(OutputLink<?> toLink) {
    if (canLink(toLink)) {
      currentLink = (ImageArrayOutputLink) toLink;
      return true;
    }
    return false;
  }

  @Override
  public SafeImage[] defaultValue(RenderParams p) {
    return ImageTools.blankArray();
  }

  @Override
  public boolean canLink(Link other) {
    return other != null && other instanceof ImageArrayOutputLink;
  }
  @Override
  public String getStyleClass() {
    return "ImageArrayLink";
  }
}

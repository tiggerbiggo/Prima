package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import java.awt.Color;

public abstract class ImageOutputLink extends OutputLink<SafeImage>{
  @Override
  public boolean canLink(Link other) {
    return other != null && other instanceof ImageInputLink;
  }

  @Override
  public String getStyleClass() {
    return "ImageLink";
  }

  @Override
  public String describeValue(RenderParams p, int currentFrame) {
    SafeImage img = get(p);

    return "Image\nWidth: " + img.getWidth() + "\nHeight: " + img.getHeight();
  }

  @Override
  public Color getColor(RenderParams p) {
    return Color.BLACK;//TODO:Change this
  }
}

package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import java.awt.Color;

public abstract class ImageArrayOutputLink extends OutputLink<SafeImage[]>{

  @Override
  public boolean canLink(Link other) {
    return other != null && other instanceof ImageArrayInputLink;
  }

  @Override
  public String getStyleClass() {
    return "ImageArrayLink";
  }

  @Override
  public String describeValue(RenderParams p, int currentFrame) {
    SafeImage[] got = get(p);

    if(got == null) return "null";
    if(currentFrame < 0 || currentFrame > got.length) return "Frame number was out of bounds, tiggerbiggo is a really bad programmer...";

    return "Image\nWidth: " + got[currentFrame].getWidth() + "\nHeight: " + got[currentFrame].getHeight();
  }

  @Override
  public Color getColor(RenderParams p) {
    return Color.BLACK;//TODO: Change this
  }
}

package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.Color;

public abstract class ImageOutputLink extends OutputLink<SafeImage>{
  public ImageOutputLink(String desc){
    this.desc = desc;
  }

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
  public Color[] getColors(RenderParams p) {
    return ColorTools.colorArray(p.frameNum());
  }

  @Override
  public String getReturnType() {
    throw new NotImplementedException();
  }

  @Override
  public boolean isSingular() {
    return true;
  }
}

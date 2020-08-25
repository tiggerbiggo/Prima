package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.Color;

public abstract class ImageArrayOutputLink extends OutputLink<SafeImage[]>{
  public ImageArrayOutputLink(String desc){
    this.desc = desc;
  }

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
  public Color[] getColors(RenderParams p) {
    return ColorTools.colorArray(p.frameNum());//TODO: Change this
  }

  @Override
  public String getReturnType() {
    throw new NotImplementedException();
  }

  @Override
  public boolean isSingular() {
    return false;
  }
}

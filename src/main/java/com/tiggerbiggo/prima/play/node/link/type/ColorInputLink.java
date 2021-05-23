package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.link.InputLink;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import java.awt.Color;

public class ColorInputLink extends InputLink<Color> {

  private ImageOutputLink image;

  public ColorInputLink(String desc){
    this.desc = desc;
  }

  @Override
  public boolean link(OutputLink<?> toLink) {
    if (!canLink(toLink)) return false;
    if(toLink instanceof ColorOutputLink)
    {
      image = null;
      currentLink = (ColorOutputLink) toLink;
      return true;
    }
    if(toLink instanceof ImageOutputLink){
      currentLink = null;
      image = (ImageOutputLink) toLink;
    }
    return false;
  }

  @Override
  public void unlink() {
    image = null;
    currentLink = null;
  }

  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof ColorOutputLink || other instanceof ImageOutputLink;
  }

  @Override
  public Color get(RenderParams p) {
    if(currentLink != null){
      return currentLink.get(p);
    }
    if(image != null){
      SafeImage img = image.get(p);
      if(img == null) return Color.BLACK;
      return img.getColor(img.denormVector((float)p.x()/p.width(), (float)p.y()/p.height()));
    }
    return Color.BLACK;
  }

  @Override
  public Color defaultValue(RenderParams p) {return Color.BLACK;}

  @Override
  public String getStyleClass() {
    return "ColorLink";
  }
}

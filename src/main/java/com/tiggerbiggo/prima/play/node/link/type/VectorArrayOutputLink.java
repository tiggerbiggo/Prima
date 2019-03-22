package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.SimpleGradient;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import java.awt.Color;

public abstract class VectorArrayOutputLink extends OutputLink<Vector2[]> {
  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof VectorArrayInputLink;
  }

  @Override
  public String getStyleClass() {
    return "VectorArrayLink";
  }

  @Override
  public String describeValue(RenderParams p, int currentFrame) {
    Vector2[] got = get(p);

    if(got == null) return "null";
    if(currentFrame < 0 || currentFrame > got.length) return "Frame number was out of bounds, tiggerbiggo is a really bad programmer...";

    return "Vector2\nX: " + got[currentFrame].X() + "\nY: " + got[currentFrame].Y();
  }

  @Override
  public Color getColor(RenderParams p) {
    return SimpleGradient.evaluate(get(p.asSingleFrame())[0], Color.black, Color.white, true);
  }
}
package com.tiggerbiggo.prima.play.node.link;

import com.tiggerbiggo.prima.play.core.render.RenderParams;

public abstract class OutputLink<T> extends Link{
  public abstract T get(RenderParams p);

  public abstract String describeValue(RenderParams p, int currentFrame);
}

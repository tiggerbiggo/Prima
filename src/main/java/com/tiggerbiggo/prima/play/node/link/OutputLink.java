package com.tiggerbiggo.prima.play.node.link;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import java.awt.Color;

public abstract class OutputLink<T> extends Link{
  public abstract T get(RenderParams p);

  public abstract String describeValue(RenderParams p, int currentFrame);
  public abstract Color[] getColors(RenderParams p);
  public abstract boolean isSingular();

  public abstract void generateGLSLMethod(StringBuilder s);
  public abstract String getMethodName();
  public abstract String getReturnType();
}

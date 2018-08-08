package com.tiggerbiggo.prima.primaplay.node.link;

import com.tiggerbiggo.prima.primaplay.core.RenderParams;

public abstract class OutputLink<T> extends Link{
  public abstract T get(RenderParams p);
}

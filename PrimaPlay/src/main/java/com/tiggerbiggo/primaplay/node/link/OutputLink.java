package com.tiggerbiggo.primaplay.node.link;

import com.tiggerbiggo.primaplay.core.RenderParams;

public abstract class OutputLink<T> extends Link{
  public abstract T get(RenderParams p);
}

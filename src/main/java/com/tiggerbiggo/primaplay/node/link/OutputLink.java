package com.tiggerbiggo.primaplay.node.link;

import com.tiggerbiggo.primaplay.core.RenderParams;

public abstract class OutputLink<T> {

  public abstract T get(RenderParams p);
}

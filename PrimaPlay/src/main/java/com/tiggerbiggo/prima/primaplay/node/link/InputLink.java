package com.tiggerbiggo.prima.primaplay.node.link;

import com.tiggerbiggo.prima.primaplay.core.RenderParams;

public abstract class InputLink<T> extends Link{
  protected OutputLink<T> currentLink;

  public abstract boolean link(OutputLink<?> toLink);

  public void unlink() {
    currentLink = null;
  }

  public T get(RenderParams p){
    if (currentLink == null) {
      return defaultValue(p);
    }
    return currentLink.get(p);
  }

  public abstract T defaultValue(RenderParams p);


}

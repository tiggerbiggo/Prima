package com.tiggerbiggo.prima.primaplay.node.link;

import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.exception.NotLinkedException;

public abstract class InputLink<T> extends Link {

  protected OutputLink<T> currentLink;

  public abstract boolean link(OutputLink<?> toLink);

  public void unlink() {
    currentLink = null;
  }

  public T get(RenderParams p) throws NotLinkedException {
    if (currentLink == null) {
      throw new NotLinkedException("InputLink: " + this.getClass().getName());
    }
    return currentLink.get(p);
  }


}

package com.tiggerbiggo.primaplay.node.link;

import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.exception.NotLinkedException;

public abstract class InputLink<T>{
  protected OutputLink<T> currentLink;

  public abstract boolean link(OutputLink<?> toLink);

  public void unlink() {
    currentLink = null;
  }

  public T get(RenderParams p)throws NotLinkedException{
    if(currentLink == null) throw new NotLinkedException("InputLink: " + this.getClass().getName());
    return currentLink.get(p);
  }
}

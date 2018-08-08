package com.tiggerbiggo.prima.primaplay.node.link;

import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.exception.NotLinkedException;
import com.tiggerbiggo.prima.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.prima.primaplay.node.core.NodeHasInput;
import com.tiggerbiggo.prima.primaplay.node.core.NodeHasOutput;

public abstract class InputLink<T> extends Link{
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

package com.tiggerbiggo.primaplay.node.link;

import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.exception.NotLinkedException;
import com.tiggerbiggo.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.primaplay.node.core.NodeHasInput;
import com.tiggerbiggo.primaplay.node.core.NodeHasOutput;

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

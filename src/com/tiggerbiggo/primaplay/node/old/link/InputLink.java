package com.tiggerbiggo.primaplay.node.old.link;

import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.exception.NotLinkedException;
import java.util.Objects;

public class InputLink<T>{
  private OutputLink<T> currentLink;
  private Class<T> myClass;

  public void link(OutputLink<T> toLink) throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(toLink);
    //add new link
    currentLink = toLink;
  }

  public void unlink() {
    currentLink = null;
  }

  public T get(RenderParams p)throws NotLinkedException{
    if(currentLink == null) throw new NotLinkedException("InputLink");
    return currentLink.get(p);
  }
}

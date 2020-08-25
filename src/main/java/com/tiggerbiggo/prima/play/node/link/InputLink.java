package com.tiggerbiggo.prima.play.node.link;

import com.tiggerbiggo.prima.play.core.render.RenderParams;

public abstract class InputLink<T> extends Link{
  protected OutputLink<T> currentLink;

  public abstract boolean link(OutputLink<?> toLink);


  public void unlink() {
    currentLink = null;
  }

  public boolean isLinked() {
    return currentLink != null;
  }

  public T get(RenderParams p){
    if (currentLink == null) {
      return defaultValue(p);
    }
    return currentLink.get(p);
  }

  public abstract T defaultValue(RenderParams p);

  public String getMethodName(){
    return currentLink.getMethodName();
  }

  public OutputLink getCurrentLink(){return currentLink;}
}

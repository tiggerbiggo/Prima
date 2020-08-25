package com.tiggerbiggo.prima.play.node.link;

public abstract class Link {
  public abstract boolean canLink(Link other);
  public abstract String getStyleClass();
  protected String desc = "";
  public String getDesc(){
    return desc;
  }
}

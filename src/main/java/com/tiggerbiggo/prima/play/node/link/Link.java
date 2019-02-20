package com.tiggerbiggo.prima.play.node.link;

public abstract class Link {
  public abstract boolean canLink(Link other);
  public abstract String getStyleClass();
}

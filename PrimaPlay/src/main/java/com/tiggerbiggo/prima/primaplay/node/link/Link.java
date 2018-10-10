package com.tiggerbiggo.prima.primaplay.node.link;

public abstract class Link {
  public abstract boolean canLink(Link other);
  public abstract String getStyleClass();
}

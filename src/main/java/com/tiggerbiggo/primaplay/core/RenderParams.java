package com.tiggerbiggo.primaplay.core;

public class RenderParams {

  private int width, height, x, y, n;

  public RenderParams(int width, int height, int x, int y, int n) {
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    this.n = n;
  }

  public int width() {
    return width;
  }

  public int height() {
    return height;
  }

  public int x() {
    return x;
  }

  public int y() {
    return y;
  }

  public int n() {
    return n;
  }
}

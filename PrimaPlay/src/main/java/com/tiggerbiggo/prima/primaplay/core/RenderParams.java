package com.tiggerbiggo.prima.primaplay.core;

public class RenderParams {

  private int width, height, x, y, frameNum;

  public RenderParams(int width, int height, int x, int y, int frameNum) {
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    this.frameNum = frameNum;
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

  public int frameNum() {
    return frameNum;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setFrameNum(int frameNum) {
    this.frameNum = frameNum;
  }

  @Override
  public RenderParams clone() {
    return new RenderParams(width, height, x, y, frameNum);
  }
}

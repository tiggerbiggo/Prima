package com.tiggerbiggo.prima.view.sample.control.flow;

import com.tiggerbiggo.prima.play.graphics.ColorConvertType;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FlowPoint {
  private int x, y;
  private int tolerance;
  private ColorConvertType type;
  private float gradient;
  private FlowMode mode;

  public FlowPoint(int x, int y, int tolerance, ColorConvertType type, float gradient, FlowMode mode) {
    this.x = x;
    this.y = y;
    this.tolerance = tolerance;
    this.type = type;
    this.gradient = gradient;
    this.mode = mode;
  }

  public FlowPoint() {
    this(0, 0, 10, ColorConvertType.V, 1, FlowMode.EUCLIDEAN);
  }

  public static final float RADIUS = 3;
  public static final float DIAMETER = 2*RADIUS;


  public void draw(GraphicsContext g){
    g.setFill(Color.YELLOW);
    g.fillOval(x-RADIUS, y-RADIUS, DIAMETER, DIAMETER);
  }

  public boolean isClicked(int clickX, int clickY){
    return
        (Math.abs(clickX - x) <= RADIUS)
            &&
        (Math.abs(clickY - y) <= RADIUS);
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getTolerance() {
    return tolerance;
  }

  public void setTolerance(int tolerance) {
    this.tolerance = Math.max(0, tolerance);
  }

  public ColorConvertType getType() {
    return type;
  }

  public void setType(ColorConvertType type) {
    if(type == null) return;
    this.type = type;
  }

  public float getGradient() {
    return gradient;
  }

  public void setGradient(float gradient) {
    this.gradient = gradient;
  }

  public FlowMode getMode() {
    return mode;
  }

  public void setMode(FlowMode mode) {
    if(type == null) return;
    this.mode = mode;
  }

  public Node getFXNode(){
    return null;
  }
}


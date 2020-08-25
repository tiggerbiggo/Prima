package com.tiggerbiggo.prima.view.sample.components.timeline;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import java.util.Objects;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TimePoint {
  public static final double RADIUS = 5;
  public static final double DIAMETER = RADIUS * 2;

  private Color defCol = Color.RED;
  private Color overCol = Color.YELLOW;

  @TransferGrid
  protected double time; //between 0 and 1
  @TransferGrid
  protected double value; // between 0 and 1

  protected boolean mouseOver = false;

  public TimePoint(double x, double y, double width, double height){
    setX(x, width);
    setY(y, height);
  }

  public TimePoint(double time, double value) {
    setTime(time);
    setValue(value);
  }

  public TimePoint() {
    this(0.5, 0.5);
  }

  public Color getDefaultColor() {
    return defCol;
  }

  public void setDefaultColor(Color defCol) {
    this.defCol = Objects.requireNonNull(defCol);
  }

  public Color getOverColor() {
    return overCol;
  }

  public void setOverColor(Color overCol) {
    this.overCol = Objects.requireNonNull(overCol);
  }

  public void setX(double x, double width){
    setTime(x / width);
  }

  public double getX(double width){
    return width * time;
  }

  public void setY(double y, double height){
    setValue(1-(y/height));
  }

  public double getY(double height){
    return height - (height*value);
  }

  public double getYInverse(double height){
    return height * value;
  }

  public void setMouseOver(boolean value){
    mouseOver = value;
  }

  public void draw(double width, double height, GraphicsContext g){
    double x = getX(width);
    double y = getY(height);

    g.setFill(mouseOver ? overCol : defCol);
    g.fillOval(x-RADIUS, y-RADIUS, DIAMETER, DIAMETER);
  }

  public boolean isMouseOver(double clickX, double clickY, double width, double height){
    return (Math.abs(clickX - getX(width)) <= RADIUS) && (Math.abs(clickY - getY(height)) <= RADIUS);
  }

  public double getTime() {
    return time;
  }

  public void setTime(double time, int min, int max) {
    this.time = Calculation.clamp(min, max, time);
  }

  public void setTime(double time){
    setTime(time, 0, 1);
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = Calculation.clamp(0, 1, value);
  }
}

package com.tiggerbiggo.prima.view.sample.components.draggable;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.sun.istack.internal.NotNull;
import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import java.util.Objects;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javax.annotation.Nullable;

public class DraggablePoint {
  private static final double RADIUS = 5;
  private static final double DIAMETER = RADIUS * 2;

  private Color defCol = Color.RED;
  private Color overCol = Color.YELLOW;

  @TransferGrid
  private double time; //between 0 and 1
  @TransferGrid
  private double value; // between 0 and 1

  private boolean mouseOver = false;

  public DraggablePoint(double x, double y, double width, double height){
    setX(x, width);
    setY(y, height);
  }

  public DraggablePoint(double time, double value) {
    setTime(time);
    setValue(value);
  }

  public DraggablePoint() {
    this(0.5, 0.5);
  }

  @Nullable
  public Color getDefaultColor() {
    return defCol;
  }

  @NotNull
  public void setDefaultColor(Color defCol) {
    this.defCol = Objects.requireNonNull(defCol);
  }

  @Nullable
  public Color getOverColor() {
    return overCol;
  }

  @NotNull
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
    setValue(y/height);
  }

  public double getY(double height){
    return height * value;
  }

  public double getYInverse(double height){
    return height - (height*value);
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

  public boolean isClicked(double clickX, double clickY, double width, double height){
    return (Math.abs(clickX - getX(width)) <= RADIUS) && (Math.abs(clickY - getY(height)) <= RADIUS);
  }

  public double getTime() {
    return time;
  }

  public void setTime(double time, int min, int max) {
    this.time = Calculation.clamp(min, max, time);
  }

  public void setTime(double time){
    this.time = time;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = Calculation.clamp(0, 1, value);
  }
}

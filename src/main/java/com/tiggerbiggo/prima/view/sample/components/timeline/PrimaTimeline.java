package com.tiggerbiggo.prima.view.sample.components.timeline;

import com.sun.istack.internal.NotNull;
import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import com.tiggerbiggo.prima.view.sample.components.draggable.DraggableCanvas;
import com.tiggerbiggo.prima.view.sample.components.draggable.DraggablePoint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PrimaTimeline extends DraggableCanvas {

  private int dividerNum = 16;

  public PrimaTimeline(double width, double height, int dividerNum) {
    super(width, height);
    this.dividerNum = dividerNum;


    setOnMouseDragged(e -> {doDragPoint(e);doSortByTime();});
  }

  @Override
  public void draw(@NotNull GraphicsContext g){
    g.setFill(Color.color(0.3, 0.3, 0.3));
    g.fillRect(0, 0, getWidth(), getHeight());

    g.setLineWidth(1);
    g.setStroke(Color.color(0.2, 0.2, 0.2));

    for (int i = 0; i < dividerNum; i++) {
      double percent;
      if(dividerNum <= 1) percent = 0;
      else percent = (float)(i+1)/(dividerNum+1);
      g.strokeLine(percent * getWidth(), 0, percent * getWidth(), getHeight());
    }

    g.setStroke(Color.RED);

    if(points.size() >=1) {
      double prevX = 0;
      double prevY = points.get(0).getY(getHeight());
      for (DraggablePoint t : points) {
        g.strokeLine(prevX, prevY, prevX = t.getX(getWidth()), prevY = t.getY(getHeight()));
      }
      g.strokeLine(prevX, prevY, getWidth(), prevY);
    }
  }
  public double evaluate(double in){
    if(points.size() <= 0) return 0;
    DraggablePoint before = null;
    DraggablePoint after = null;

    for(DraggablePoint p : points){
      if(p.getTime() <= in) {
        before = p;
      }
      if(p.getTime() > in){
        after = p;
        if(before == null) before = after;
        break;
      }
    }

    if(before == null || after == null){
      return 0;
    }

    if(before == after) return before.getValue();

    double percent = after.getTime() - before.getTime();
    if(percent == 0) return before.getValue();

    percent = 1.0/percent;

    percent *= (in - before.getTime());

    return Calculation.lerp(before.getValue(), after.getValue(), percent);
  }


}

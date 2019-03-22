package com.tiggerbiggo.prima.view.sample.components.draggable;

import com.sun.istack.internal.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javax.annotation.Nullable;

public abstract class DraggableCanvas extends Canvas {
  public List<DraggablePoint> points;
  private DraggablePoint dragging = null;

  public DraggableCanvas(double width, double height, @Nullable List<DraggablePoint> _points){
    super(width, height);

    if(_points != null){
      points = _points;
    }
    else
    {
      points = new ArrayList<>();
    }

    setOnMousePressed(e -> {
      dragging = getMouseOver(e.getX(), e.getY());

      if(e.getClickCount() == 2){
        if(dragging == null){
          points.add(new DraggablePoint(e.getX(), e.getY(), getWidth(), getHeight()));
        }
        else
        {
          points.remove(dragging);
        }
      }
      draw();
      e.consume();
    });

    setOnMouseDragged(e -> {
      doDragPoint(e);
    });

    setOnMouseMoved(e -> {
      for(DraggablePoint p : points){
        p.setMouseOver(false);
      }
      DraggablePoint tmp = getMouseOver(e.getX(), e.getY());
      if(tmp != null) tmp.setMouseOver(true);

      draw();
    });

    doSortByTime();
  }

  public DraggableCanvas(double width, double height){
    this(width, height, null);
  }

  public void doDragPoint(MouseEvent e){
    e.consume();
    if(dragging != null) {
      dragging.setX(e.getX(), getWidth());
      dragging.setY(e.getY(), getHeight());
    }
    draw();
  }

  private DraggablePoint getMouseOver(double x, double y){
    for(DraggablePoint p : points){
      if(p.isClicked(x, y, getWidth(), getHeight())) return p;
    }
    return null;
  }

  protected void doSortByTime(){
    points.sort(Comparator.comparingDouble(DraggablePoint::getTime));
  }

  private void setSizes(double width, double height){
    setWidth(width);
    setHeight(height);

    draw();
  }

  public List<DraggablePoint> getPoints() {
    return points;
  }

  void drawPoints(){
    for(DraggablePoint t : points) {
      t.draw(getWidth(), getHeight(), getGraphicsContext2D());
    }
  }

  public abstract void draw(@NotNull GraphicsContext g);

  public void draw(){
    draw(getGraphicsContext2D());
  }

}

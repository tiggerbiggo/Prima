package com.tiggerbiggo.prima.view.sample.components.timeline;

import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class PrimaTimeline extends Canvas {

  private List<TimePoint> points;
  private TimePoint dragging = null;

  private int dividerNum = 16;

  public PrimaTimeline(double width, double height, int dividerNum, List<TimePoint> _points) {
    super(width, height);
    this.dividerNum = dividerNum;

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
          points.add(new TimePoint(e.getX(), e.getY(), getWidth(), getHeight()));
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
      System.out.println("Points: " + points);
      for(TimePoint p : points){
        p.setMouseOver(false);
      }
      TimePoint tmp = getMouseOver(e.getX(), e.getY());
      if(tmp != null) tmp.setMouseOver(true);

      draw();
    });

    doSortByTime();


    setOnMouseDragged(e -> {doDragPoint(e);doSortByTime();});
  }

  public void draw(){
    GraphicsContext g = getGraphicsContext2D();

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
      for (TimePoint t : points) {
        g.strokeLine(prevX, prevY, prevX = t.getX(getWidth()), prevY = t.getY(getHeight()));
      }
      g.strokeLine(prevX, prevY, getWidth(), prevY);
    }

    drawPoints();
  }
  public double evaluate(double in){
    if(points.size() <= 0) return 0;
    TimePoint before = null;
    TimePoint after = null;

    for(TimePoint p : points){
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

  public void doDragPoint(MouseEvent e){
    e.consume();
    if(dragging != null) {
      dragging.setX(e.getX(), getWidth());
      dragging.setY(e.getY(), getHeight());
    }
    draw();
  }

  private TimePoint getMouseOver(double x, double y){
    for(TimePoint p : points){
      if(p.isClicked(x, y, getWidth(), getHeight())) return p;
    }
    return null;
  }

  protected void doSortByTime(){
    points.sort(Comparator.comparingDouble(TimePoint::getTime));
  }

  private void setSizes(double width, double height){
    setWidth(width);
    setHeight(height);

    draw();
  }

  public List<TimePoint> getPoints() {
    return points;
  }

  void drawPoints(){
    for(TimePoint t : points) {
      t.draw(getWidth(), getHeight(), getGraphicsContext2D());
    }
  }
}

/*


  public DraggableCanvas(double width, double height, @Nullable List<DraggablePoint> _points){
    super(width, height);


  }

  public DraggableCanvas(double width, double height){
    this(width, height, null);
  }


 */
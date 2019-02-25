package com.tiggerbiggo.prima.view.sample.components;

import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.Callback;
import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Knob extends Canvas {

  private double value;
  private double min, max;
  private double lastY;

  private boolean isFirst = true;

  private ChangeListener listener;

  private Timeline timer;

  private Callback callback;

  public Knob(){
    this(-1,1);
  }

  /**
   * 64x64 pixels
   * Knob controller (hehe) acts like a knob
   */
  public Knob(double _min, double _max) {
    super(64,64);

    min = _min;
    max = _max;

    value = max;

    timer = new Timeline(new KeyFrame(Duration.seconds(1.0/24),
        e -> {
          reRender();
        }
    ));
    timer.setCycleCount(Animation.INDEFINITE);

    setOnDragDetected(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        timer.play();
        event.consume();
      }
    });

    setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if(isFirst) {
          lastY = event.getY();
          isFirst = false;
        }
        event.consume();

        value += -(event.getY() - lastY) * (max-min) * 0.007;
        value = Calculation.clamp(min, max, value);

        lastY=event.getY();

        draw();

      }
    });

    setOnMouseReleased(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        timer.stop();
        isFirst = true;
        reRender();
      }
    });

    setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
          value = max;
          draw();
          reRender();
        }
      }
    });

    draw();
  }

  private void reRender(){
    listener.onObjectValueChanged(null,null,null,null);
    if(callback != null) callback.call(value);
  }

  public double getValue() {
    return value;
  }

  public void setValue(double newVal){
    value = Calculation.clamp(min, max, newVal);
  }

  public void setChangeListener(ChangeListener _listener){
    listener = _listener;
  }

  public void setCallback(Callback c){
    callback = c;
  }

  public void draw(){
    GraphicsContext g = getGraphicsContext2D();

    if(g == null) return;

    g.clearRect(0,0,64,64);

    g.setLineWidth(4);

    //Circle
    g.setFill(Color.BLACK);
    g.setStroke(Color.WHITE);
    g.fillOval(4,4,56,56);
    g.strokeOval(4,4,56,56);

    //Mark
    g.setStroke(Color.RED);


    double p = (value-min)/(max-min);

    double x2 = -Math.sin(p * 2 * Math.PI);//outer
    double y2 = Math.cos(p * 2 * Math.PI);

    g.strokeLine(32,32,(x2*32)+32, (y2*32)+32);
  }

}

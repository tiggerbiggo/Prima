package com.tiggerbiggo.prima.view.sample;

import com.tiggerbiggo.prima.play.core.render.Renderer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class RenderBar extends ProgressBar {

  Timeline timer;
  public RenderBar(Label l) {
    super();

    updateProgress();

    timer = new Timeline(new KeyFrame(Duration.seconds(1d / 60), new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //Renderer inst = Renderer.getInstance();
        //l.setText("Queue length: " + inst.getQueueLength() + ", Current task name: " + inst.getCurrentName());
      }
    }));
    timer.setCycleCount(Animation.INDEFINITE);
    timer.play();


  }

  private void updateProgress(){
    //this.setProgress(1-Renderer.getInstance().getCurrentProgress());
  }
}

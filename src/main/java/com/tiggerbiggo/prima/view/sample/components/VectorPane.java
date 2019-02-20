package com.tiggerbiggo.prima.view.sample.components;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class VectorPane extends AnchorPane{
  Canvas gridCanvas;
  Button resetButton;
  Pane xScale, yScale;

  GridPane container;

  List<VectorHandle> handles;

  Label xLabel, yLabel;

  Timeline renderTick;

  public VectorPane(Vector2... vectors){
    handles = new ArrayList<>();

  }

}

class VectorHandle extends Circle{

}
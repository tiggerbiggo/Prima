package com.tiggerbiggo.prima.view.sample.control;

import com.tiggerbiggo.prima.play.core.render.RenderCallback;
import com.tiggerbiggo.prima.play.core.render.RenderTask;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.view.sample.components.AnimatedImageView;
import com.tiggerbiggo.prima.view.sample.components.DraggableValueField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class PreviewController implements Initializable {

  @FXML
  Button mulButton, divButton, refreshButton;

  DraggableValueField widthField, heightField;

  @FXML
  Pane imagePane;

  @FXML
  HBox widthHeightBox;

  AnimatedImageView view;

  int width = 200;
  int height = 200;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    view = new AnimatedImageView();
    imagePane.getChildren().add(view);

    widthField = new DraggableValueField(100, 1, 10000, 1);
    heightField = new DraggableValueField(100, 1, 10000, 1);

    HBox.setHgrow(widthField, Priority.ALWAYS);
    HBox.setHgrow(heightField, Priority.ALWAYS);

    widthHeightBox.getChildren().addAll(widthField, heightField);

    mulButton.setOnAction(e -> {
      width *= 2;
      height *= 2;
      updateFields();
    });

    divButton.setOnAction(e -> {
      width /= 2;
      height /= 2;
      updateFields();
    });

    widthField.addCallback(value -> width = (int)value);

    heightField.addCallback(value -> height = (int)value);

    refreshButton.setOnAction(e -> {
      view.stop();
      RenderTask t = MainController.thisController.nodePane.renderAsync(width, height, 60, "Preview Window Render",
          new RenderCallback() {
            @Override
            public void callback(SafeImage[] i) {
              if(i != null) {
                view.setImgs(i);
                sizePane();
                view.start();
              }
            }
          });


    });

    updateFields();
  }

  public void updateFields(){
    widthField.setValue(width);
    heightField.setValue(height);
  }

  public void sizePane(){
    imagePane.setPrefWidth(width);
    imagePane.setPrefHeight(height);
  }
}

package com.tiggerbiggo.prima.view.sample.control;

import com.tiggerbiggo.prima.play.core.render.RenderCallback;
import com.tiggerbiggo.prima.play.core.render.RenderTask;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.view.sample.components.AnimatedImageView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class PreviewController implements Initializable {

  @FXML
  Button mulButton, divButton, refreshButton;

  @FXML
  TextField widthField, heightField;

  @FXML
  Pane imagePane;

  AnimatedImageView view;

  int width = 200;
  int height = 200;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    view = new AnimatedImageView();
    imagePane.getChildren().add(view);

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

    widthField.setOnAction(e -> {
      try{
        width = Integer.parseInt(widthField.getText());
      }
      catch (NumberFormatException ex){
        updateFields();
      }
    });

    heightField.setOnAction(e -> {
      try{
        height = Integer.parseInt(heightField.getText());
      }
      catch (NumberFormatException ex){
        updateFields();
      }
    });

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
    widthField.setText(width + "");
    heightField.setText(height + "");
  }

  public void sizePane(){
    imagePane.setPrefWidth(width);
    imagePane.setPrefHeight(height);
  }
}

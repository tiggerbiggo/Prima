package com.tiggerbiggo.prima.play.node.implemented.output;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.node.core.NodeHasOutput;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

public class CommentNode extends NodeHasOutput{

  @TransferGrid
  String comment;

  @TransferGrid
  double x = 100;
  @TransferGrid
  double y = 100;

  @Override
  public String getName() {
    return "Comment Node";
  }

  @Override
  public String getDescription() {
    return "Write comments";
  }

  public static final double MIN_SIZE = 100;//minimum dimensions of the content
  public static final double DRAG_SIZE = 20;//

  private double offsetX, offsetY;

  @Override
  public Node getFXNode(ChangeListener listener) {
    TextArea content = new TextArea(comment);
    Pane dragPane = new Pane();
    Pane toReturn = new Pane(content, dragPane);

    //20x20 pane
    //bind bottom right of anchor pane to the drag pane
    //my logic when coming up with it ^

    // Custom Event Code //
    content.setOnKeyReleased(event -> comment = content.getText());
    content.setWrapText(true);
    // ///////////////// //

    // ========================== Common ========================== //
    // Content Layout //
    content.setPrefWidth(Double.MAX_VALUE);
    content.setPrefHeight(Double.MAX_VALUE);
    content.maxWidthProperty().bind(toReturn.widthProperty());
    content.maxHeightProperty().bind(toReturn.heightProperty().subtract(DRAG_SIZE));
    // ////////////// //


    dragPane.setMinWidth(DRAG_SIZE);
    dragPane.setMinHeight(DRAG_SIZE);
    dragPane.setTranslateX(x);
    dragPane.setTranslateY(y);
    dragPane.getStyleClass().add("button");

    offsetX = offsetY = 0;

    dragPane.setOnMousePressed(event -> {
      if(event.getButton() != MouseButton.PRIMARY)
        return;

      offsetX = dragPane.getTranslateX() - event.getSceneX();
      offsetY = dragPane.getTranslateY() - event.getSceneY();

      event.consume();
    });

    dragPane.setOnMouseDragged(e -> {
      if(e.getButton() != MouseButton.PRIMARY)
        return;
      if(e.getSceneX() + offsetX >= MIN_SIZE)
        dragPane.setTranslateX(e.getSceneX() + offsetX);
      else
        dragPane.setTranslateX(MIN_SIZE);

      if(e.getSceneY() + offsetY  >= MIN_SIZE)
        dragPane.setTranslateY(e.getSceneY() + offsetY);
      else
        dragPane.setTranslateY(MIN_SIZE);

      x = dragPane.getTranslateX();
      y = dragPane.getTranslateY();

      e.consume();
    });

    dragPane.toFront();


    toReturn.setPrefWidth(Double.MAX_VALUE);
    toReturn.setPrefHeight(Double.MAX_VALUE);
    toReturn.maxWidthProperty().bind(dragPane.translateXProperty().add(DRAG_SIZE));
    toReturn.maxHeightProperty().bind(dragPane.translateYProperty().add(DRAG_SIZE));

    toReturn.getStyleClass().add("CommentPane");

    return toReturn;


  }
}
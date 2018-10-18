package com.tiggerbiggo.prima.primaplay.node.implemented.output;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.utils.calculation.GUITools;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
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

  public static final double MIN_SIZE = 100;
  public static final double DRAG_SIZE = 20;

  private double offsetX, offsetY;

  @Override
  public Node getFXNode(ChangeListener listener) {
    TextArea ta = new TextArea(comment);
    Pane dragPane = new Pane();
    Pane p = new Pane(ta, dragPane);

    //20x20 pane
    //bind bottom right of anchor pane to the drag pane

    ta.setPrefWidth(Double.MAX_VALUE);
    ta.setPrefHeight(Double.MAX_VALUE);

    ta.maxWidthProperty().bind(p.widthProperty());
    ta.maxHeightProperty().bind(p.heightProperty().subtract(DRAG_SIZE));

    ta.setOnKeyReleased(event -> comment = ta.getText());
    ta.setWrapText(true);

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


    p.setPrefWidth(Double.MAX_VALUE);
    p.setPrefHeight(Double.MAX_VALUE);
    p.maxWidthProperty().bind(dragPane.translateXProperty().add(DRAG_SIZE));
    p.maxHeightProperty().bind(dragPane.translateYProperty().add(DRAG_SIZE));

    p.getStyleClass().add("CommentPane");

    return p;
  }
}

package com.tiggerbiggo.prima.view.guinode;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Bloom;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.lang.reflect.Field;

public abstract class GUILink extends Circle {

  private Vector2 offset, last;

  public GUILink() { //extends Circle
    super(10);

    getStyleClass().add("GUILink");
    last = Vector2.ZERO;

    setOnDragDetected(event -> {
      Dragboard db = startDragAndDrop(TransferMode.MOVE);

      ClipboardContent clipboardContent = new ClipboardContent();
      clipboardContent.putString("Move me");

      db.setContent(clipboardContent);

      event.consume();
    });

    setOnDragEntered(event -> setEffect(new Bloom(0.1)));
    setOnDragExited(event -> setEffect(null));

    setOnDragOver(event -> event.acceptTransferModes(TransferMode.MOVE));

    setOnMouseDragged(Event::consume);

    setOnMousePressed(event -> {
      if (event.getButton().equals(MouseButton.MIDDLE)) {
        triggerUnlink();
        event.consume();
      }
      else if(event.getButton().equals(MouseButton.SECONDARY)){
        doNodeMenu(event);
        event.consume();
      }
    });
    setManaged(false);
    toFront();
  }

  public abstract void unlink();
  public abstract void triggerUnlink();
  public abstract void doNodeMenu(MouseEvent event);

  public Vector2 getOffset() {
    return offset;
  }

  public void setRelativeOffset(Vector2 offset) {
    this.offset = offset;
  }

  public Bounds getWorldPosition() {
    return localToScene(getBoundsInLocal());
  }
}

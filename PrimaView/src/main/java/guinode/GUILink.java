package guinode;

import com.tiggerbiggo.utils.calculation.Vector2;
import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.scene.effect.Bloom;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Circle;

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
      if (event.getButton().equals(MouseButton.SECONDARY)) {
        triggerUnlink();
      }
      System.out.println("Clicked");
      event.consume();
    });
    setManaged(false);
    toFront();
  }

  public abstract void unlink();
  public abstract void triggerUnlink();

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

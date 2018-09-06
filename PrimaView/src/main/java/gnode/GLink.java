package gnode;

import com.tiggerbiggo.utils.calculation.Vector2;
import javafx.scene.effect.Bloom;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Circle;

public abstract class GLink extends Circle {

  private Vector2 offset, last;

  public GLink() { //extends Circle
    super(10);

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

    setOnDragOver(event -> {
      event.acceptTransferModes(TransferMode.MOVE);
    });

    setOnMouseClicked(event -> {
      if (event.getButton().equals(MouseButton.SECONDARY)) {
        unlink();
      }
    });
  }

  public abstract void unlink();

  public Vector2 getOffset() {
    return offset;
  }

  public void setRelativeOffset(Vector2 offset) {
    this.offset = offset;
  }

  public void updatePosition(Vector2 parentPosition) {
    Vector2 added = parentPosition.add(this.offset);
    setCenterX(added.X());
    setCenterY(added.Y());
    last = parentPosition.clone();
  }

  public void updatePosition() {
    if (last == null) {
      last = Vector2.ZERO;
    }
    updatePosition(last);
  }

  public Vector2 getWorldPosition() {
    return new Vector2(getCenterX(), getCenterY());
  }
}

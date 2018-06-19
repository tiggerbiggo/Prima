package gnode;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import javafx.scene.effect.Bloom;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Circle;

public abstract class GLink extends Circle{
  private Vector2 position, last;

  public GLink() { //extends Circle
    super(10);

    last = Vector2.ZERO;

    setOnDragDetected(event -> {
      System.out.println("STARTED");
      Dragboard db = startDragAndDrop(TransferMode.MOVE);

      ClipboardContent clipboardContent = new ClipboardContent();
      clipboardContent.putString("Move me");

      db.setContent(clipboardContent);

      event.consume();
    });

    setOnDragEntered(event -> setEffect(new Bloom(0.1)));
    setOnDragExited(event -> setEffect(null));

    setOnDragOver(event -> {
      System.out.println("before: " + event.getAcceptedTransferMode());
      event.acceptTransferModes(TransferMode.MOVE);
      System.out.println("after" + event.getAcceptedTransferMode());
    });

    setOnDragDropped(event -> {
      System.out.println("TRIGGERED: " + event.getGestureSource());
      GLink source;
      if(event.getGestureSource() instanceof GLink){
        System.out.println("yes");
        source = (GLink)event.getGestureSource();
        source.updatePosition();
      }
      updatePosition();
    });
  }

  public Vector2 getPosition() {
    return position;
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }

  public void updatePosition(Vector2 offset){
    Vector2 added = offset.add(position);
    setCenterX(added.X());
    setCenterY(added.Y());
    last = offset.clone();
  }

  public void updatePosition(){
    if(last == null) last = Vector2.ZERO;
    updatePosition(last);
  }
}

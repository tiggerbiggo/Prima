package guinode;

import com.tiggerbiggo.prima.primaplay.node.link.type.ImageArrayInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.ImageInputLink;
import com.tiggerbiggo.utils.calculation.Vector2;
import com.tiggerbiggo.prima.primaplay.node.link.InputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.NumberArrayInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorArrayInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorInputLink;
import java.util.Objects;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GUIInputLink extends GUILink {

  InputLink<?> link;
  private GUILinkLine line;
  private GUIOutputLink currentGLink;
  private GUINode owner;
  private int index;
  private final Pane parent;
  Vector2 position;

  public GUIInputLink(InputLink<?> in, GUINode owner, int index, Vector2 position, Pane parent) {
    link = in;
    this.parent = Objects.requireNonNull(parent);
    this.owner = Objects.requireNonNull(owner);
    this.index = index;

    if (link instanceof ColorArrayInputLink) {
      setFill(Color.YELLOW);
    } else if (link instanceof NumberArrayInputLink) {
      setFill(Color.GREY);
    } else if (link instanceof VectorArrayInputLink) {
      setFill(Color.BLUE);
    } else if (link instanceof VectorInputLink) {
      setFill(Color.AQUA);
    } else if (link instanceof ImageInputLink) {
      setFill(Color.GREEN);
    } else if (link instanceof ImageArrayInputLink) {
      setFill(Color.DARKGREEN);
    } else {
      System.out.println("ERR: " + link);
    }

    setRelativeOffset(position);

    setOnDragDropped(event -> {
      GUIOutputLink source;
      if (event.getGestureSource() instanceof GUIOutputLink) {
        source = (GUIOutputLink) event.getGestureSource();
        if (link.canLink(source.link)) {
          deleteLine();
          link(source);
        }
      }
      updatePosition();
      updateLinePos();
    });
  }

  public void deleteLine() {
    if (line != null) {
      line.delete();
    }
  }

  @Override
  public void unlink() {
    line = null;
    currentGLink = null;
    link.unlink();
  }

  @Override
  public void updatePosition(Vector2 offset) {
    super.updatePosition(offset);
  }

  public void updateLinePos() {
    if (line != null) {
      line.updatePositions();
    }
  }

  public boolean link(GUIOutputLink out) {
    if (link.link(out.getLink())) {

      currentGLink = out;

      if(line != null){
        line.delete();
      }
      parent.getChildren().add(new GUILinkLine(this, out, parent));
      // ^Here be bugs


      return true;
    }
    return false;
  }

  public InputLink<?> getLink() {
    return link;
  }

  public GUILinkLine getLine() {
    return line;
  }

  public GUIOutputLink getCurrentGLink() {
    return currentGLink;
  }

  public GUINode getOwner() {
    return owner;
  }

  public void setLine(GUILinkLine line) {
    this.line = Objects.requireNonNull(line);
  }

  public int getIndex() {
    return index;
  }
}

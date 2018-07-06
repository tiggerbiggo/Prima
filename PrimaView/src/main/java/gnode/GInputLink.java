package gnode;

import com.tiggerbiggo.utils.calculation.Vector2;
import com.tiggerbiggo.prima.primaplay.node.link.InputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.NumberArrayInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorArrayInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorInputLink;
import java.util.Objects;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GInputLink extends GLink {

  InputLink<?> link;
  private GLinkLine line;
  Vector2 position;
  private final Pane parent;

  public GInputLink(InputLink<?> in, Vector2 position, Pane parent) {
    link = in;
    this.parent = Objects.requireNonNull(parent);

    if (link instanceof ColorArrayInputLink) {
      setFill(Color.YELLOW);
    } else if (link instanceof NumberArrayInputLink) {
      setFill(Color.GREY);
    } else if (link instanceof VectorArrayInputLink) {
      setFill(Color.BLUE);
    } else if (link instanceof VectorInputLink) {
      setFill(Color.AQUA);
    } else {
      System.out.println("ERR: " + link);
    }

    setRelativeOffset(position);

    setOnDragDropped(event -> {
      GOutputLink source;
      if (event.getGestureSource() instanceof GOutputLink) {
        source = (GOutputLink) event.getGestureSource();
        if (link.canLink(source.link)) {
          deleteLine();
          link(source);
          this.parent.getChildren().add(new GLinkLine(this, source, this.parent));
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

  public boolean link(GOutputLink out) {
    if (link.link(out.getLink())) {
      /*if(line != null){
        line.delete();
      }
      parent.getChildren().add(new GLinkLine(this, out, parent));*/
      // ^Here be bugs
      return true;
    }
    return false;
  }

  public InputLink<?> getLink() {
    return link;
  }

  public GLinkLine getLine() {
    return line;
  }

  public void setLine(GLinkLine line) {
    this.line = Objects.requireNonNull(line);
  }
}

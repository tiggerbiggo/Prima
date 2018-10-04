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

  private InputLink<?> link;
  private GUILinkLine line;
  private GUIOutputLink currentGLink;
  private GUINode owner;
  private int index;

  public GUIInputLink(InputLink<?> in, GUINode owner, int index, double yOffset) {
    link = in;
    this.owner = Objects.requireNonNull(owner);
    this.index = index;

    setCenterY(yOffset);

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

    setOnDragDropped(event -> {
      GUIOutputLink source;
      if (event.getGestureSource() instanceof GUIOutputLink) {
        source = (GUIOutputLink) event.getGestureSource();
        if (link.canLink(source.link)) {
          if(line != null)
            line.delete();
          link(source);
        }
      }
    });
  }

  @Override
  public void unlink() {
    currentGLink = null;
    link.unlink();
  }

  @Override
  public void triggerUnlink() {
    if(line != null){
      line.delete();
    }
  }

  public void setLine(GUILinkLine line){
    this.line = line;
  }

  public boolean link(GUIOutputLink out) {
    if (link.link(out.getLink())) {
      currentGLink = out;
      new GUILinkLine(this, out);
      // ^Here be bugs
      return true;
    }
    return false;
  }

  public InputLink<?> getLink() {
    return link;
  }

  public GUIOutputLink getCurrentGLink() {
    return currentGLink;
  }

  public GUINode getOwner() {
    return owner;
  }

  public int getIndex() {
    return index;
  }
}
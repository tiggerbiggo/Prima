package guinode;

import com.tiggerbiggo.prima.primaplay.node.core.INode;
import com.tiggerbiggo.prima.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.prima.primaplay.node.link.InputLink;
import java.util.Objects;
import java.util.function.Predicate;
import sample.components.NodePopup;

public class GUIInputLink extends GUILink {

  private InputLink<?> link;
  private GUILinkLine line;
  private GUIOutputLink currentGLink;
  private GUINode owner;
  private int index;

  public GUIInputLink(InputLink<?> in, GUINode owner, int index, double yOffset) {
    super();

    link = in;
    this.owner = Objects.requireNonNull(owner);
    this.index = index;

    setCenterY(yOffset);

    getStyleClass().addAll("GUILink", link.getStyleClass());

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

  @Override
  public void doNodeMenu() {

  }

  public void doNodeMenu(int x, int y) {
    NodePopup pop = new NodePopup(
        owner.getParentPane(),
        new Predicate<INode>() {
      @Override
      public boolean test(INode node) {
        if(node instanceof NodeHasOutput){
          //((NodeHasOutput)node).
        }
        return false;
      }
    },
        x,
        y
    );
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
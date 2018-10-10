package guinode;

import com.tiggerbiggo.prima.primaplay.node.link.OutputLink;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GUIOutputLink extends GUILink {

  OutputLink<?> link;

  List<GUILinkLine> lineList;

  private GUINode owner;
  private int index;

  public GUIOutputLink(OutputLink<?> in, GUINode owner, int index, double yOffset) {
    super();

    link = in;
    lineList = new ArrayList<>();

    this.owner = owner;
    this.index = index;

    setCenterY(yOffset);

    getStyleClass().addAll("GUILink", link.getStyleClass());

    GUIOutputLink thisLink = this;

    setOnDragDropped(event -> {
      GUIInputLink inputLink;
      if (event.getGestureSource() instanceof GUIInputLink) {
        inputLink = (GUIInputLink) event.getGestureSource();
        if (inputLink.link(thisLink)) {
          //success, create line
          new GUILinkLine(inputLink, thisLink);
        }
      }
    });
  }

  @Override
  public void unlink() {
    while (lineList.size() >= 1) {
      lineList.get(0).delete();
    }
  }

  @Override
  public void triggerUnlink() {
    unlink();
  }

  public OutputLink<?> getLink() {
    return link;
  }

  public void addLine(GUIInputLink link){
    addLine(new GUILinkLine(link, this));
  }

  public void addLine(GUILinkLine line) {
    lineList.add(line);
    owner.getParentPane().getChildren().add(line);
  }

  public void forgetLine(GUILinkLine toForget) {
    toForget = Objects.requireNonNull(toForget);
    lineList.remove(toForget);
    owner.getParentPane().getChildren().remove(toForget);
  }

  public void deleteAllLines() {
    while (lineList.size() > 0) {
      GUILinkLine line = lineList.get(0);
      line.delete();
    }
  }

  public GUINode getOwner() {
    return owner;
  }

  public int getIndex() {
    return index;
  }
}

package com.tiggerbiggo.prima.view.guinode;

import com.tiggerbiggo.prima.play.node.core.INodeHasInput;
import com.tiggerbiggo.prima.play.node.link.InputLink;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import com.tiggerbiggo.prima.view.sample.ViewMain;
import com.tiggerbiggo.prima.view.sample.components.NodePopup;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

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
    Tooltip.install(this, new Tooltip(link.getDesc()));
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

  @Override
  public void doNodeMenu(MouseEvent event) {
    NodePopup pop = new NodePopup(
        owner.getParentPane(),
        node -> {
          if (node instanceof INodeHasInput){
            INodeHasInput out = ((INodeHasInput) node);
            for (InputLink<?> otherLink : out.getInputs()) {
              if (otherLink.canLink(link))
                return true;
            }
          }
          return false;
        },
        (int)event.getScreenX(),
        (int)event.getScreenY()
    );

    pop.setLink(this);
    pop.show(ViewMain.getMainStage(), event.getScreenX(), event.getScreenY());
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

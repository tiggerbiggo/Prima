package com.tiggerbiggo.prima.view.guinode;

import com.tiggerbiggo.prima.play.node.core.INode;
import com.tiggerbiggo.prima.play.node.core.INodeHasOutput;
import com.tiggerbiggo.prima.play.node.link.InputLink;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import com.tiggerbiggo.prima.view.sample.ViewMain;
import com.tiggerbiggo.prima.view.sample.components.NodePopup;
import java.util.Objects;
import java.util.function.Predicate;

import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

import javax.tools.Tool;

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

    Tooltip.install(this, new Tooltip(link.getDesc()));
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
  public void doNodeMenu(MouseEvent event) {
    NodePopup pop = new NodePopup(
        owner.getParentPane(),
        new Predicate<INode>() {
          @Override
          public boolean test(INode node) {
            if (node instanceof INodeHasOutput){
              INodeHasOutput out = ((INodeHasOutput) node);
              for (OutputLink<?> otherLink : out.getOutputs()) {
                if (otherLink.canLink(link))
                  return true;
              }
            }
            return false;
          }
        },
        (int)event.getScreenX(),
        (int)event.getScreenY()
    );

    pop.setLink(this);
    pop.show(ViewMain.getMainStage(), event.getScreenX(), event.getScreenY());
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
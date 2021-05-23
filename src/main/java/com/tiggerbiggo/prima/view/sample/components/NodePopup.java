package com.tiggerbiggo.prima.view.sample.components;

import com.tiggerbiggo.prima.play.node.core.INode;
import com.tiggerbiggo.prima.view.guinode.GUIInputLink;
import com.tiggerbiggo.prima.view.guinode.GUILink;
import com.tiggerbiggo.prima.view.guinode.GUINode;
import com.tiggerbiggo.prima.view.guinode.GUIOutputLink;
import com.tiggerbiggo.prima.view.sample.NodePane;
import com.tiggerbiggo.prima.view.sample.NodeReflection;
import java.util.List;
import java.util.function.Predicate;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class NodePopup extends Popup {

  private final NodePane parent;
  private Predicate<INode> filter;
  private GUILink link;

  /**
   * Creates a new context menu with all nodes included.
   */
  public NodePopup(NodePane _parent, int x, int y){
    this(_parent , a -> true, x, y);
  }

  /**
   *
   * @param _filter The filter used to determine whether or not to include a given node in the list.
   */
  public NodePopup(NodePane _parent, Predicate<INode> _filter, int x, int y){
    filter = _filter;
    parent = _parent;

    List<Class<? extends INode>> nodeList = NodeReflection.getAllImplementedNodes();

    VBox itemBox = new VBox();
    itemBox.setMinWidth(Region.USE_COMPUTED_SIZE);
    itemBox.setMaxWidth(Double.MAX_VALUE);


    ScrollPane scroll = new ScrollPane(itemBox);
    scroll.setFitToWidth(false);
    scroll.setPrefWidth(Region.USE_COMPUTED_SIZE);
    scroll.setMinWidth(Region.USE_PREF_SIZE);
    scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
    scroll.setMaxHeight(400);

    for(Class<? extends INode> clazz : nodeList){
      try{
        INode node = clazz.newInstance();
        if(filter.test(node)){
          NodeMenuItem item = new NodeMenuItem(node.getName(), clazz);
          Tooltip desc = new Tooltip(node.getDescription());
          Tooltip.install(item, desc);
          item.setOnAction(event -> {
            try {
              Point2D p = parent.screenToLocal(x, y);
              GUINode toAdd = new GUINode(Math.max(0, p.getX()-150), p.getY(), clazz.newInstance(), parent, parent.getListener());
              parent.addNode(toAdd);
              if(link != null){
                linkFirst(toAdd);
              }
              System.out.println("DONE: " + x + ", " + y);
            } catch (InstantiationException | IllegalAccessException e) {
              e.printStackTrace();
            }
            NodePopup.this.hide();
          });
          HBox.setHgrow(item, Priority.ALWAYS);
          itemBox.getChildren().add(item);
        }
      }
      catch (InstantiationException | IllegalAccessException e) {
      }
    }

    /*for(Node m : itemBox.getChildren()){
      if(m instanceof NodeMenuItem){

      }
    }*/

    this.getContent().add(scroll);
    this.setAutoHide(true);
  }

  private void linkFirst(GUINode gNode){
    INode node = gNode.getNode();
    if(link instanceof GUIInputLink){
      //store currently linked output
      GUIOutputLink tmpLink = ((GUIInputLink) link).getCurrentGLink();
      link.triggerUnlink();
      //node must have output
      for(GUIOutputLink opl : gNode.getOutputs()){
        if(((GUIInputLink) link).link(opl)) {
          return;
        }
      }
      ((GUIInputLink) link).link(tmpLink);
    }
    else if(link instanceof GUIOutputLink){
      //node must have input
      for(GUIInputLink ipl : gNode.getInputs()){
        if(ipl.link(((GUIOutputLink)link))) return;
      }
    }
  }

  public void setLink(GUILink link) {
    this.link = link;
  }
}

class NodeMenuItem extends Button{
  private Class<? extends INode> clazz;

  public NodeMenuItem(String text, Class<? extends INode> _clazz) {
    super(text);
    clazz = _clazz;
  }

  public Class<? extends INode> getClazz() {
    return clazz;
  }
}

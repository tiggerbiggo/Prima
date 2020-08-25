package com.tiggerbiggo.prima.view.sample;

import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.render.RenderCallback;
import com.tiggerbiggo.prima.play.core.render.RenderTask;
import com.tiggerbiggo.prima.play.node.core.INode;
import com.tiggerbiggo.prima.play.node.implemented.BasicRenderNode;
import com.tiggerbiggo.prima.view.guinode.GUINode;
import com.tiggerbiggo.prima.view.sample.components.NodePopup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class NodePane extends Pane {
  private BasicRenderNode renderNode = null;
  List<GUINode> nodeList;
  ChangeListener listener;

  public NodePane(ChangeListener _listener){
    listener = _listener;
    renderNode = null;//new BasicRenderNode();

    getStyleClass().add("NodePane");

    nodeList = new ArrayList<>();
    //addNode(new GUINode(renderNode, this));

    setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
          deselectAll();
        }
        else if(event.getButton().equals(MouseButton.SECONDARY)){
          event.consume();
          NodePopup nodeMenu = new NodePopup(
              NodePane.this,
              (int)event.getScreenX(),
              (int)event.getScreenY()
          );
          nodeMenu.show(NodePane.this, event.getScreenX(), event.getScreenY());
        }
      }
    });
  }

  public void addNode(INode node){
    Objects.requireNonNull(node);

    addNode(new GUINode(node, this, listener));
  }

  public void addNode(GUINode ... nodes){
    for(GUINode n : nodes){
      addNode(n);
    }
  }

  public void addNode(List<GUINode> nodes){
    addNode(nodes.toArray(new GUINode[]{}));
  }

  public void addNode(GUINode node){
    if(node.getNode()instanceof BasicRenderNode){
      renderNode = ((BasicRenderNode)node.getNode());
    }
    getChildren().add(node);
    nodeList.add(node);
  }

  public void doDragOffsets(MouseEvent e){
    for(GUINode n : nodeList){
      if(n.isSelected()){
        n.setDragOffset(e);
      }
    }
  }

  public void doDrag(MouseEvent e){
    for(GUINode n : nodeList){
      if(n.isSelected()){
        n.doDrag(e);
      }
    }
  }

  public void doSelection(MouseEvent e){
    if(!e.isControlDown()){
      deselectAll();
    }
  }

  public void deselectAll(){
    for(GUINode n : nodeList){
      n.setSelected(false);
    }
  }

  public void selectAll(){
    for(GUINode n : nodeList){
      n.setSelected(true);
    }
  }

  public void deleteSelectedNodes(){
    List<GUINode> toDelete = new ArrayList<>();
    for(GUINode n : nodeList){
      if(n.isSelected()) toDelete.add(n);
    }

    for(GUINode n : toDelete){
      n.delete();
    }
  }

  public void forgetNode(GUINode node){
    getChildren().remove(node);
    nodeList.remove(node);
  }

  public RenderTask renderAsync(int w, int h, int n, String desc, RenderCallback callback) throws NullPointerException{
    return getRenderNode().renderAsync(w, h, n, desc, callback);
  }

  public void clearNodes(){
    nodeList.clear();
    getChildren().clear();
  }

  public ChangeListener getListener() {
    return listener;
  }

  public BasicRenderNode getRenderNode() {
    return renderNode;
  }

  public List<GUINode> getReadOnlyNodeList(){
    return Collections.unmodifiableList(nodeList);
  }
}

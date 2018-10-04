package sample;

import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.primaplay.node.core.INode;
import com.tiggerbiggo.prima.primaplay.node.implemented.BasicRenderNode;
import guinode.GUINode;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

public class NodePane extends Pane {
  BasicRenderNode renderNode = null;
  List<GUINode> nodeList;
  ChangeListener listener;

  public NodePane(ChangeListener _listener){
    listener = _listener;
    renderNode = null;//new BasicRenderNode();

    nodeList = new ArrayList<>();
    //addNode(new GUINode(renderNode, this));
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



  public Future<BufferedImage[]> renderAsync(int w, int h, int n) throws NullPointerException{
    return renderNode.renderAsync(w, h, n);
  }

  public BufferedImage[] render(int w, int h, int n) throws NullPointerException{
    try {
      return renderAsync(w, h, n).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void clearNodes(){
    nodeList.clear();
    getChildren().clear();
  }

  public void purgeNodes(){
    List<GUINode> toDelete = new ArrayList<>();
    for(GUINode n : nodeList){
      if(!getChildren().contains(n)){
        toDelete.add(n);
      }
    }
    getChildren().removeAll(toDelete);
  }
}

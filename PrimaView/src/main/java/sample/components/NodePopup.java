package sample.components;

import com.tiggerbiggo.prima.primaplay.node.core.INode;
import guinode.GUINode;
import java.util.List;
import java.util.function.Predicate;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import sample.NodePane;
import sample.NodeReflection;

public class NodePopup extends Popup {

  private final NodePane parent;
  private Predicate<INode> filter;

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
          item.setOnAction(event -> {
            try {
              parent.getChildren().add(
                  new GUINode(x, y, clazz.newInstance(), parent, parent.getListener())
              );
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

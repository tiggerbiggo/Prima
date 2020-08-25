package com.tiggerbiggo.prima.view.guinode;

import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.node.core.INode;
import com.tiggerbiggo.prima.play.node.core.INodeHasInput;
import com.tiggerbiggo.prima.play.node.core.INodeHasOutput;
import com.tiggerbiggo.prima.play.node.link.InputLink;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import com.tiggerbiggo.prima.view.sample.NodePane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class GUINode extends AnchorPane {

  public static final double LINK_Y = 30;

  private INode node;

  private List<GUIInputLink> inputs;
  private List<GUIOutputLink> outputs;

  private double offsetX, offsetY;

  private NodePane parent;

  private boolean selected = false;

  public GUINode(double width, double height, double x, double y, INode node, NodePane _parent, ChangeListener listener) {
    //super();
    //setHeight(50);
    //setWidth(50);

    //setFill(Color.WHITE);

    parent = _parent;

    this.node = node;

    setMinWidth(width);
    setWidth(width);

    setHeight(height);

    this.getStyleClass().add("GUINode");

    setLayoutX(x);
    setLayoutY(y);
    addEventHandler(MouseEvent.ANY, event -> {
      if(event.getButton() != MouseButton.MIDDLE) event.consume();
    });

    setOnMousePressed(e -> {
      if(e.getButton().equals(MouseButton.PRIMARY)) {
        if(!isSelected()){
          parent.doSelection(e);
          setSelected(true);
        }

        parent.doDragOffsets(e);

        e.consume();
      }
      else if(e.getButton().equals(MouseButton.SECONDARY)){
        if(!isSelected()){
          parent.doSelection(e);
          setSelected(true);
        }

        MenuItem delete;

        delete = new MenuItem("Delete");
        delete.setOnAction(ev -> parent.deleteSelectedNodes());
        ContextMenu m = new ContextMenu(delete);
        m.show(this, e.getScreenX(), e.getScreenY());
      }
    });

    setOnMouseReleased(e -> {
      if(e.getButton().equals(MouseButton.PRIMARY)) {
        //parent.doSelection(e);
        //setSelected(true);
        //toFront();
      }
    });

    setOnMouseDragged(e -> {
      if(e.getButton() != MouseButton.PRIMARY)
        return;

      parent.doDrag(e);

      e.consume();
    });

    if (node != null) {
//      if(node instanceof ChangeListener){
//        reflectorGrid.addChangeListener((ChangeListener)node);
//      }
//      if(listener != null){
//        reflectorGrid.addChangeListener(listener);
//      } //TODO: Implement Loki changes after it's updated

      GridPane layoutGrid = new GridPane();

      setTopAnchor(layoutGrid, 0.0);
      setLeftAnchor(layoutGrid, 0.0);
      setBottomAnchor(layoutGrid, 0.0);
      setRightAnchor(layoutGrid, 0.0);

      getChildren().add(layoutGrid);

      updateLinks(node);

      int maxIoIndex = Math.max(inputs.size(), outputs.size());

      setMinHeight((maxIoIndex * LINK_Y) + LINK_Y);


      GUINode thisNode = this;

      layoutGrid.addColumn(0, new Label(node.getName()), node.getFXNode(listener));

      toBack();
    } else {
      throw new IllegalArgumentException(
          "Given node does not implement IO! Node is either corrupt or tiggerbiggo is a bad coder. Probably both.");
    }
  }

  public GUINode(double x, double y, INode node, NodePane parent, ChangeListener listener){
    this(50, 50, x, y, node, parent, listener);
  }

  public GUINode(INode node, NodePane parent, ChangeListener listener){
    this(0, 0, node, parent, listener);
  }

  public GUINode(INode node, NodePane parent) {
    this(node, parent, null);
  }

  public void updateLinks(INode node){
    //for each old:
    //if new link can still connect to old external link:
    //


    inputs = new ArrayList<>();
    if (node instanceof INodeHasInput) {
      InputLink<?>[] inputs = ((INodeHasInput) node).getInputs();
      for (int i = 0; i < inputs.length; i++) {
        InputLink<?> link = inputs[i];
        GUIInputLink newLink = new GUIInputLink(link, this, i, LINK_Y + (i * LINK_Y));
        this.inputs.add(newLink);
        //parent.getChildren().add(tmp);
        getChildren().add(newLink);
      }
    }

    outputs = new ArrayList<>();
    if (node instanceof INodeHasOutput) {
      OutputLink<?>[] outputs = ((INodeHasOutput) node).getOutputs();
      for (int i = 0; i < outputs.length; i++) {
        OutputLink<?> link = outputs[i];
        GUIOutputLink newLink = new GUIOutputLink(link, this, i, LINK_Y + (i * LINK_Y));
        newLink.centerXProperty().bind(widthProperty());
        this.outputs.add(newLink);
        getChildren().add(newLink);
      }
    }
  }

  public void doDrag(MouseEvent e){
    if(e.getSceneX() + offsetX >= 0)
      setLayoutX(e.getSceneX() + offsetX);
    else
      setLayoutX(0);

    if(e.getSceneY() + offsetY >= 0)
      setLayoutY(e.getSceneY() + offsetY);
    else
      setLayoutY(0);
  }

  public void setDragOffset(MouseEvent e){
    offsetX = getLayoutX() - e.getSceneX();
    offsetY = getLayoutY() - e.getSceneY();
  }

  public boolean isSelected(){
    return selected;
  }

  public void setSelected(boolean value){
    selected = value;
    if(value){
      getStyleClass().add("NodeSelected");
    }
    else {
      getStyleClass().remove("NodeSelected");
    }
  }

  public void delete(){
    for (GUIInputLink i : inputs) {
      i.triggerUnlink();
    }
    for(GUIOutputLink o : outputs){
      o.triggerUnlink();
    }
    getChildren().removeAll(inputs);
    getChildren().removeAll(outputs);
    parent.forgetNode(this);
  }

  public Vector2 posAsVector() {
    return new Vector2(getLayoutX(), getLayoutY());
  }

  public List<GUIInputLink> getInputs() {
    return inputs;
  }

  public List<GUIOutputLink> getOutputs() {
    return outputs;
  }

  public INode getNode() {
    return node;
  }

  public NodePane getParentPane() {
    return parent;
  }
}

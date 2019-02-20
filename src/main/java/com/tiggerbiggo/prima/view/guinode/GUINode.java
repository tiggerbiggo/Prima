package com.tiggerbiggo.prima.view.guinode;

import ch.hephaistos.utilities.loki.ReflectorGrid;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class GUINode extends AnchorPane {

  public static final double LINK_Y = 30;

  private ReflectorGrid reflectorGrid;
  private INode node;

  private List<GUIInputLink> inputs;
  private List<GUIOutputLink> outputs;

  private double offsetX, offsetY;

  private NodePane parent;

  public GUINode(double width, double height, double x, double y, INode node, NodePane _parent, ChangeListener listener) {
    //super();
    //setHeight(50);
    //setWidth(50);

    //setFill(Color.WHITE);

    parent = _parent;

    this.node = node;

    setMinWidth(width);
    setWidth(width);
    setMinHeight(height);
    setHeight(height);

    this.getStyleClass().add("GUINode");

    setLayoutX(x);
    setLayoutY(y);
    addEventHandler(MouseEvent.ANY, event -> {
      if(event.getButton() != MouseButton.MIDDLE) event.consume();
    });

    setOnMousePressed(event -> {
      if(event.getButton() != MouseButton.PRIMARY)
        return;

      offsetX = getLayoutX() - event.getSceneX();
      offsetY = getLayoutY() - event.getSceneY();
      event.consume();
    });

    setOnMouseDragged(e -> {
      if(e.getButton() != MouseButton.PRIMARY)
        return;
      if(e.getSceneX() + offsetX >= 0)
        setLayoutX(e.getSceneX() + offsetX);
      else
        setLayoutX(0);

      if(e.getSceneY() + offsetY >= 0)
        setLayoutY(e.getSceneY() + offsetY);
      else
        setLayoutY(0);
      e.consume();
    });

    if (node != null) {
      reflectorGrid = new ReflectorGrid();
      reflectorGrid.transfromIntoGrid(node);

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


      GUINode thisNode = this;
      Button delete = new Button("Delete");
      delete.setOnAction(event -> {
        for (GUIInputLink i : inputs) {
          i.triggerUnlink();
        }
        for(GUIOutputLink o : outputs){
          o.triggerUnlink();
        }
        getChildren().removeAll(inputs);
        getChildren().removeAll(outputs);
        parent.deleteNode(thisNode);
      });


      layoutGrid.addColumn(0, new Label(node.getName()), node.getFXNode(listener), delete);

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

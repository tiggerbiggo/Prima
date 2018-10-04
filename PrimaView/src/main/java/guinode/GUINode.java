package guinode;

import ch.hephaistos.utilities.loki.ReflectorGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.primaplay.node.implemented.BasicRenderNode;
import com.tiggerbiggo.utils.calculation.Vector2;
import com.tiggerbiggo.prima.primaplay.node.core.INode;
import com.tiggerbiggo.prima.primaplay.node.core.INodeHasInput;
import com.tiggerbiggo.prima.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.prima.primaplay.node.link.InputLink;
import com.tiggerbiggo.prima.primaplay.node.link.OutputLink;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class GUINode extends AnchorPane {

  public static final double LINK_Y = 30;

  private ReflectorGrid reflectorGrid;
  private INode node;

  private List<GUIInputLink> inputs;
  private List<GUIOutputLink> outputs;

  private double offsetX, offsetY;

  public GUINode(int width, int height, int x, int y, INode node, Pane parent, ChangeListener listener) {
    //super();
    //setHeight(50);
    //setWidth(50);

    //setFill(Color.WHITE);

    this.node = node;

    setMinWidth(width);
    setWidth(width);
    setMinHeight(height);
    setHeight(height);

    setStyle("-fx-background-color: #888888");

    setLayoutX(x);
    setLayoutY(y);

    setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        offsetX = getLayoutX() - event.getSceneX();
        offsetY = getLayoutY() - event.getSceneY();
      }
    });

    setOnMouseDragged(e -> {
      setLayoutX(e.getSceneX() + offsetX);
      setLayoutY(e.getSceneY() + offsetY);
    });

    if (node != null) {
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

      reflectorGrid = new ReflectorGrid();
      reflectorGrid.transfromIntoGrid(node);

      if(node instanceof ChangeListener){
        reflectorGrid.addChangeListener((ChangeListener)node);
      }

      if(listener != null){
        reflectorGrid.addChangeListener(listener);
      }

      GridPane layoutGrid = new GridPane();

      setTopAnchor(layoutGrid, 0.0);
      setLeftAnchor(layoutGrid, 0.0);
      setBottomAnchor(layoutGrid, 0.0);
      setRightAnchor(layoutGrid, 0.0);

      getChildren().add(layoutGrid);

      GUINode thisNode = this;
      Button delete = new Button("Delete");
      delete.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          for (GUIInputLink i : inputs) {
            i.unlink();
          }
          getChildren().removeAll(inputs);
          getChildren().removeAll(outputs);
          parent.getChildren().remove(thisNode);
        }
      });

      if (!(node instanceof BasicRenderNode)) {
        layoutGrid.addColumn(0, new Text(node.getName()), reflectorGrid, delete);
      } else {
        layoutGrid.addColumn(0, new Text(node.getName()), reflectorGrid);
      }

      toBack();
    } else {
      throw new IllegalArgumentException(
          "Given node does not implement IO! Node is either corrupt or tiggerbiggo is a bad coder. Probably both.");
    }
  }

  public void refreshGrid(){
    reflectorGrid.redoGrid();
  }

  public GUINode(int x, int y, INode node, Pane parent, ChangeListener listener){
    this(50, 50, x, y, node, parent, listener);
  }

  public GUINode(INode node, Pane parent, ChangeListener listener){
    this(0, 0, node, parent, listener);
  }

  public GUINode(INode node, Pane parent) {
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
}

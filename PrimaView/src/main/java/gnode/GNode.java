package gnode;

import ch.rs.reflectorgrid.ReflectorGrid;
import com.tiggerbiggo.utils.calculation.Vector2;
import com.tiggerbiggo.prima.primaplay.node.core.INode;
import com.tiggerbiggo.prima.primaplay.node.core.INodeHasInput;
import com.tiggerbiggo.prima.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.prima.primaplay.node.core.RenderNode;
import com.tiggerbiggo.prima.primaplay.node.link.InputLink;
import com.tiggerbiggo.prima.primaplay.node.link.OutputLink;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class GNode extends AnchorPane {

  public static final double LINK_Y = 30;

  private List<GInputLink> inputs;
  private List<GOutputLink> outputs;

  public GNode(int width, int height, int x, int y, INode node, Pane parent) {
    //super();
    //setHeight(50);
    //setWidth(50);

    //setFill(Color.WHITE);

    setMinWidth(width);
    setWidth(width);
    setMinHeight(height);
    setHeight(height);

    setStyle("-fx-background-color: #888888");

    setTranslateX(x);
    setTranslateY(y);

    setOnMouseDragged(e -> {
      setTranslateX(e.getSceneX());
      setTranslateY(e.getSceneY());

      for (GInputLink i : inputs) {
        i.updatePosition(posAsVector());
        i.updateLinePos();
        i.toFront();
      }

      for (GOutputLink o : outputs) {
        o.setRelativeOffset(new Vector2(getWidth(), o.getOffset().Y()));
        o.updatePosition(posAsVector());
        o.updateLinePos();
        o.toFront();
      }
    });

    if (node != null) {
      inputs = new ArrayList<>();
      if (node instanceof INodeHasInput) {
        InputLink<?>[] inputs = ((INodeHasInput) node).getInputs();
        for (int i = 0; i < inputs.length; i++) {
          InputLink<?> link = inputs[i];
          GInputLink tmp = new GInputLink(link, new Vector2(0, LINK_Y + (i * LINK_Y)), parent);
          tmp.updatePosition(posAsVector());
          this.inputs.add(tmp);
          parent.getChildren().add(tmp);
        }
      }

      outputs = new ArrayList<>();
      if (node instanceof INodeHasOutput) {
        OutputLink<?>[] outputs = ((INodeHasOutput) node).getOutputs();
        for (int i = 0; i < outputs.length; i++) {
          OutputLink<?> link = outputs[i];
          GOutputLink tmp = new GOutputLink(link, new Vector2(getWidth(), LINK_Y + (i * LINK_Y)),
              parent);
          tmp.updatePosition(posAsVector());
          this.outputs.add(tmp);
          parent.getChildren().add(tmp);
        }
      }

      ReflectorGrid reflectorGrid = new ReflectorGrid();
      //getChildren().add(reflectorGrid.transfromIntoGrid(node));

      GridPane layoutGrid = new GridPane();

      setTopAnchor(layoutGrid, 0.0);
      setLeftAnchor(layoutGrid, 0.0);
      setBottomAnchor(layoutGrid, 0.0);
      setRightAnchor(layoutGrid, 0.0);

      getChildren().add(layoutGrid);

      GNode thisNode = this;

      Button delete = new Button("Delete");
      delete.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          for (GInputLink i : inputs) {
            i.deleteLine();
            parent.getChildren().remove(i);
          }
          for (GOutputLink o : outputs) {
            o.deleteAllLines();
            parent.getChildren().remove(o);
          }
          parent.getChildren().remove(thisNode);
        }
      });

      if (!(node instanceof RenderNode)) {
        layoutGrid
            .addColumn(0, new Text(node.getName()), reflectorGrid.transfromIntoGrid(node), delete);
      } else {
        layoutGrid.addColumn(0, new Text(node.getName()), reflectorGrid.transfromIntoGrid(node));
      }

      toBack();
    } else {
      throw new IllegalArgumentException(
          "Given node does not implement IO! Node is either corrupt or tiggerbiggo is a bad coder. Probably both.");
    }
  }

  public GNode(INode node, Pane parent) {
    this(50, 50, 0, 0, node, parent);
  }

  public Vector2 posAsVector() {
    return new Vector2(getTranslateX(), getTranslateY());
  }
}

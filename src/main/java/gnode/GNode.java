package gnode;

import ch.rs.reflectorgrid.ReflectorGrid;
import ch.rs.reflectorgrid.TransferGrid;
import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.node.core.INode;
import com.tiggerbiggo.primaplay.node.core.INodeHasInput;
import com.tiggerbiggo.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.OutputLink;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GNode extends AnchorPane {
  public static final double LINK_Y = 30;

  private List<GInputLink> inputs;
  private List<GOutputLink> outputs;

  public GNode(int width, int height, int x, int y, INode node, Pane toAdd){
    super();
    //setHeight(50);
    //setWidth(50);

    //setFill(Color.WHITE);

    setMinWidth(width);
    setWidth(width);
    setMinHeight(height);
    setHeight(height);

    setStyle("-fx-background-color: #888888");

    Text t = new Text(node.getName());
    setTopAnchor(t, 0.0);
    setLeftAnchor(t, 0.0);
    setBottomAnchor(t, 0.0);
    setRightAnchor(t, 0.0);
    getChildren().add(t);

    //setX(x);
    //setY(y);

    setTranslateX(x);
    setTranslateY(y);

    Vector2 scene = new Vector2();
    Vector2 translate = new Vector2();

    setOnMouseDragged(e -> {
      setTranslateX(e.getSceneX());
      setTranslateY(e.getSceneY());

      for(GInputLink i : inputs){
        i.updatePosition(posAsVector());
        i.updateLinePos();
        i.toFront();
      }

      for(GOutputLink o : outputs){
        o.setRelativeOffset(new Vector2(getWidth() ,o.getOffset().Y()));
        o.updatePosition(posAsVector());
        o.updateLinePos();
        o.toFront();
      }
    });

    if(node != null) {
      inputs = new ArrayList<>();
      if (node instanceof INodeHasInput) {
        InputLink<?>[] inputs = ((INodeHasInput) node).getInputs();
        for (int i = 0; i < inputs.length; i++) {
          InputLink<?> link = inputs[i];
          GInputLink tmp = new GInputLink(link, new Vector2(0, LINK_Y+(i*LINK_Y)), toAdd);
          tmp.updatePosition(posAsVector());
          this.inputs.add(tmp);
          toAdd.getChildren().add(tmp);
        }
      }

      outputs = new ArrayList<>();
      if (node instanceof INodeHasOutput) {
        OutputLink<?>[] outputs = ((INodeHasOutput) node).getOutputs();
        for (int i = 0; i < outputs.length; i++) {
          OutputLink<?> link = outputs[i];
          GOutputLink tmp = new GOutputLink(link, new Vector2(getWidth(), LINK_Y + (i * LINK_Y)), toAdd);
          tmp.updatePosition(posAsVector());
          this.outputs.add(tmp);
          toAdd.getChildren().add(tmp);
        }
      }

      ReflectorGrid grid = new ReflectorGrid();
      getChildren().add(grid.transfromIntoGrid(node));

      toBack();
    }
    else{
      throw new IllegalArgumentException("Given node does not implement IO! Node is either corrupt or tiggerbiggo is a bad coder. Probably both.");
    }
  }

  public GNode(INode node, Pane parent){
    this(50, 50, 0, 0, node, parent);
  }

  public Vector2 posAsVector(){
    return new Vector2(getTranslateX(), getTranslateY());
  }
}

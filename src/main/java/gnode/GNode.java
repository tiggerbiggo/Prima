package gnode;

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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GNode extends Rectangle {
  public static final double LINK_Y = 30;

  private List<GInputLink> inputs;
  private List<GOutputLink> outputs;

  public GNode(int width, int height, INode node, Pane toAdd){
    super();

    //setHeight(50);
    //setWidth(50);

    setFill(Color.WHITE);

    setWidth(width);
    setHeight(height);

    Vector2 scene = new Vector2();
    Vector2 translate = new Vector2();

    setOnMousePressed(e -> {

    });

    setOnMouseDragged(e -> {
      setX(e.getX());
      setY(e.getY());

      for(GInputLink i : inputs){
        i.updatePosition(posAsVector());
        i.toFront();
      }

      for(GOutputLink o : outputs){
        o.updatePosition(posAsVector());
        o.toFront();
      }
    });

    if(node != null) {
      inputs = new ArrayList<>();
      if (node instanceof INodeHasInput) {
        InputLink<?>[] inputs = ((INodeHasInput) node).getInputs();
        for (int i = 0; i < inputs.length; i++) {
          InputLink<?> link = inputs[i];
          GInputLink tmp = new GInputLink(link, new Vector2(0, LINK_Y+(i*LINK_Y)));
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
          GOutputLink tmp = new GOutputLink(link, new Vector2(getWidth(), LINK_Y + (i * LINK_Y)));
          tmp.updatePosition(posAsVector());
          this.outputs.add(tmp);
          toAdd.getChildren().add(tmp);
        }
      }

      toBack();
    }
    else{
      throw new IllegalArgumentException("Given node does not implement IO! Node is either corrupt or tiggerbiggo is a bad coder. Probably both.");
    }
  }

  public Vector2 posAsVector(){
    return new Vector2(getX(), getY());
  }
}

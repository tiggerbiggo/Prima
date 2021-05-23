package com.tiggerbiggo.prima.play.node.implemented.output;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeHasOutput;
import com.tiggerbiggo.prima.play.node.link.type.VectorOutputLink;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PolarNode extends NodeHasOutput {
  @TransferGrid
  private boolean useRadians = true;
  @TransferGrid
  private boolean loopAngle = false;
  @TransferGrid
  private boolean spiral = false;
  @TransferGrid
  private boolean cylinder = false;

  @TransferGrid
  private double zoom = 1;
  @TransferGrid
  private double stretch = 1;

  private VectorOutputLink out;

  public PolarNode() {
    out = new VectorOutputLink("Out") {
      @Override
      public Vector2 get(RenderParams p) {
        Vector2 xy = new Vector2(p.x(), p.y());

        //Normalize
        xy = xy.divide(new Vector2(p.width()/2.0, p.height()/2.0));
        xy = xy.subtract(Vector2.ONE);

        double angle = xy.angleBetween(Vector2.UP);
        double percentRound = angle / Math.PI;
        if(loopAngle){
          //angle *= 2;
          angle = Calculation.modLoop(angle, Math.PI*2, true);
        }
        if(!useRadians) angle /= (Math.PI);


        //x = theta, y=radius
        if(!cylinder) {
          angle *= stretch;
          xy = new Vector2(angle, xy.magnitude() * zoom);
          if (spiral) xy = xy.add(0, percentRound);
        }
        else{
          //cylinder mode
          xy = new Vector2(xy.Y(), xy.X());
          xy = xy.multiply(stretch);
        }



        return xy;
      }
      @Override
      public void generateGLSLMethod(StringBuilder s) {
        throw new NotImplementedException();
      }

      @Override
      public String getMethodName() {
        throw new NotImplementedException();
      }
    };
    addOutput(out);
  }

  @Override
  public String getName() {
    return "Polar Node";
  }

  @Override
  public String getDescription() {
    return "Creates a polar coordinate map, where x=theta, y=radius";
  }

  @Override
  public Node getFXNode(ChangeListener listener) {
    final CheckBox use_radians = new CheckBox("Use Radians");
    use_radians.setSelected(useRadians);
    use_radians.setOnAction(event -> useRadians = use_radians.isSelected());

    final CheckBox loop_angle = new CheckBox("Loop Angle");
    loop_angle.setSelected(loopAngle);
    loop_angle.setOnAction(e -> loopAngle = loop_angle.isSelected());

    final CheckBox spiral_check = new CheckBox("Spiral");
    spiral_check.setSelected(spiral);
    spiral_check.setOnAction(e -> spiral = spiral_check.isSelected());

    final CheckBox cylinder_check = new CheckBox("Cylinder");
    cylinder_check.setSelected(cylinder);
    cylinder_check.setOnAction(e -> cylinder = cylinder_check.isSelected());

    final Spinner<Double> zoom_amnt = new Spinner<>(0, 10, zoom);
    zoom_amnt.valueProperty().addListener(e -> zoom = zoom_amnt.getValue());
    zoom_amnt.setEditable(true);

    final Spinner<Double> stretch_amnt = new Spinner<>(0, 10, stretch);
    stretch_amnt.valueProperty().addListener(e -> stretch = stretch_amnt.getValue());
    stretch_amnt.setEditable(true);

    return new VBox(use_radians, loop_angle, spiral_check, cylinder_check, new HBox(zoom_amnt, new Label("Zoom")), new HBox(stretch_amnt, new Label("Stretch")));
  }
}

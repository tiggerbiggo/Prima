package com.tiggerbiggo.prima.play.node.implemented.output;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeHasOutput;
import com.tiggerbiggo.prima.play.node.link.type.VectorOutputLink;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PolarNode extends NodeHasOutput {
  @TransferGrid
  private boolean useRadians = true;
  @TransferGrid
  private boolean loopAngle = false;

  @TransferGrid
  private double zoom = 1;

  private VectorOutputLink out;

  public PolarNode() {
    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        Vector2 xy = new Vector2(p.x(), p.y());

        //Normalize
        xy = xy.divide(new Vector2(p.width()/2.0, p.height()/2.0));
        xy = xy.subtract(Vector2.ONE);

        double angle = xy.angleBetween(Vector2.UP);
        if(loopAngle){
          //angle *= 2;
          angle = Calculation.modLoop(angle, Math.PI*2, true);
        }
        if(!useRadians) angle /= (Math.PI);


        //x = theta, y=radius
        xy = new Vector2(angle, xy.magnitude() * zoom);

        return xy;
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

    final Slider zoom_amnt = new Slider(0, 10, zoom);
    zoom_amnt.valueProperty().addListener(e -> zoom = zoom_amnt.getValue());

    return new VBox(use_radians, loop_angle, new HBox(new Label("Zoom: "), zoom_amnt));
  }
}

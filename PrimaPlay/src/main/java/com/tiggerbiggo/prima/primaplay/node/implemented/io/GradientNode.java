package com.tiggerbiggo.prima.primaplay.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.graphics.HueCycleGradient;
import com.tiggerbiggo.prima.primaplay.graphics.SimpleGradient;
import com.tiggerbiggo.prima.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorArrayInputLink;
import com.tiggerbiggo.utils.calculation.ReflectionHelper;
import com.tiggerbiggo.utils.calculation.Vector2;
import java.awt.Color;
import java.lang.reflect.Field;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;

public class GradientNode extends NodeInOut {

  @TransferGrid
  boolean hueCycle;

  Field f_hueCycle = ReflectionHelper.getFieldFromClass(GradientNode.class, "hueCycle");

  VectorArrayInputLink inputLink;
  ColorInputLink A, B;

  ColorArrayOutputLink out;

  public GradientNode() {
    inputLink = new VectorArrayInputLink();

    A = new ColorInputLink();
    B = new ColorInputLink();

    addInput(inputLink, A, B);

    out = new ColorArrayOutputLink() {
      @Override
      public Color[] get(RenderParams p) {
        Vector2[] in = inputLink.get(p);
        Color[] toReturn = new Color[in.length];
        for (int i = 0; i < in.length; i++) {
          if(hueCycle)
            toReturn[i] = HueCycleGradient.evaluate(in[i]);
          else
            toReturn[i] = SimpleGradient.evaluate(in[i], A.get(p), B.get(p), true);
        }
        return toReturn;
      }
    };
    addOutput(out);
  }

  @Override
  public String getName() {
    return "Gradient Node";
  }

  @Override
  public String getDescription() {
    return "Takes an array of Vectors and turns them into an array of Colors.";
  }

  @Override
  public Node getFXNode(ChangeListener listener) {
    CheckBox cb = new CheckBox("Hue Cycle");
    cb.setSelected(hueCycle);

    Object thisObj = this;

    cb.setOnAction(event -> {
      hueCycle = cb.isSelected();
      listener.onObjectValueChanged(f_hueCycle, !hueCycle, hueCycle, thisObj);
    });
    return cb;
  }
}

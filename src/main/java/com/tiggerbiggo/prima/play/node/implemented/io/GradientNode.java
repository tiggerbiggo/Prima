package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.calculation.ReflectionHelper;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.HueCycleGradient;
import com.tiggerbiggo.prima.play.graphics.SimpleGradient;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.ColorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorArrayInputLink;
import java.awt.Color;
import java.lang.reflect.Field;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class GradientNode extends NodeInOut {

  @TransferGrid
  boolean hueCycle;

  Field f_hueCycle = ReflectionHelper.getFieldFromClass(GradientNode.class, "hueCycle");

  VectorArrayInputLink inputLink;
  ColorInputLink A, B;

  ColorArrayOutputLink out;

  public GradientNode() {
    inputLink = new VectorArrayInputLink("Input");

    A = new ColorInputLink("Color A");
    B = new ColorInputLink("Color B");

    addInput(inputLink, A, B);

    out = new ColorArrayOutputLink("Out") {
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

      @Override
      public void generateGLSLMethod(StringBuilder s) {
        s.append("vec4 " + getMethodName() + "{\n");
        s.append("  return mix(").append(A.getMethodName()+","+B.getMethodName());
        s.append(",fract(length(").append(inputLink.getMethodName()).append(")));\n}\n");
      }

      @Override
      public String getMethodName() {
        return "Gradient_"+hashCode()+"()";
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

    cb.setOnAction(event -> {
      hueCycle = cb.isSelected();
      listener.onObjectValueChanged(f_hueCycle, !hueCycle, hueCycle, GradientNode.this);
    });
    return cb;
  }
}

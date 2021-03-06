package com.tiggerbiggo.prima.play.node.implemented.output;


import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.calculation.GUITools;
import com.tiggerbiggo.prima.play.core.calculation.ReflectionHelper;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.node.core.NodeHasOutput;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.ColorOutputLink;
import java.awt.Color;
import java.lang.reflect.Field;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ColorNode extends NodeHasOutput {

  private ColorOutputLink out;
  private ColorArrayOutputLink animOut;

  private Color c;

  @TransferGrid
  private int r = 0;

  @TransferGrid
  private int g = 0;

  @TransferGrid
  private int b = 0;

  private Field cField = ReflectionHelper.getFieldFromClass(ColorNode.class, "c");

  private transient ColorPicker picker = null;

  public ColorNode(){
    c = Color.BLACK;

    out = new ColorOutputLink("Static Color") {
      @Override
      public Color get(RenderParams p) {
        return c;
      }

      @Override
      public void generateGLSLMethod(StringBuilder s) {
        /*vec4
        *
        * */

        s.append("vec4 ColorSingleOut_")
         .append(hashCode())
         .append("(){\n return ")
         .append(ColorTools.colorAsVec4(c))
         .append(";\n}");
      }

      @Override
      public String getMethodName() {
        return "ColorSingleOut_"+hashCode()+"()";
      }
    };
    animOut = new ColorArrayOutputLink("Animated Color") {
      @Override
      public Color[] get(RenderParams p) {
        Color[] toReturn = new Color[p.frameNum()];
        for(int i=0; i<p.frameNum(); i++){
          toReturn[i] = c;
        }
        return toReturn;
      }

      @Override
      public void generateGLSLMethod(StringBuilder s) {
        /*vec4
         *
         * */

        s.append("vec4 ColorMultiOut_")
                .append(hashCode())
                .append("(){\n  return ")
                .append(ColorTools.colorAsVec4(c))
                .append(";\n}");
      }

      @Override
      public String getMethodName() {
        return "ColorMultiOut_"+hashCode()+"()";
      }
    };
    addOutput(out, animOut);
  }

  @Override
  public String getName() {
    return "Color Node";
  }

  @Override
  public String getDescription() {
    return "Outputs a color";
  }

  @Override
  public Node getFXNode(ChangeListener listener) {
    if(picker == null) {
      c = new Color(r, g, b);
      picker = new ColorPicker(ColorTools.toFXColor(c));
      picker.getStyleClass().add("no-border");
    }

    picker.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Color old = c;
        c = ColorTools.fromFXColor(picker.getValue());
        r = c.getRed();
        g = c.getGreen();
        b = c.getBlue();
        listener.onObjectValueChanged(cField, old, c, this);
      }
    });

    GUITools.setAllAnchors(picker, 0);

    AnchorPane p = new AnchorPane(picker);

    p.setMinWidth(50);
    p.setMinHeight(50);

    return p;
  }
}

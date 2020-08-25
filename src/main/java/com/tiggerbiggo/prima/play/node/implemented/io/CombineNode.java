package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.CombineFunction;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorOutputLink;
import java.util.function.BiFunction;

public class CombineNode extends NodeInOut {

  @TransferGrid
  private CombineFunction func;

  private VectorInputLink A, B;
  private VectorOutputLink out;


  public CombineNode(CombineFunction _func) {
    func = _func;

    A = new VectorInputLink("A");
    B = new VectorInputLink("B");
    addInput(A, B);

    out = new VectorOutputLink("Out") {
      @Override
      public Vector2 get(RenderParams p) {
        return func.apply(A.get(p), B.get(p));
      }

      @Override
      public void generateGLSLMethod(StringBuilder s) {
        /*
         vec2 Combine_x(){
              vec2 A = <A method here>;
              vec2 B = <B method here>;
              return A <operator> B;
          }
         */

        s.append("vec2 Combine_" + hashCode() + "(){\n");
        s.append("  vec2 A = " + A.getMethodName() + ";\n");
        s.append("  vec2 B = " + B.getMethodName() + ";\n");
        s.append("  return A " + func.getOperatorChar() + "B;\n}");
      }

      @Override
      public String getMethodName() {
        return "Combine_" + hashCode() + "()";
      }
    };
    addOutput(out);
  }

  public CombineNode() {
    this(CombineFunction.ADD);
  }

  @Override
  public String getName() {
    return "Combine Node";
  }

  @Override
  public String getDescription() {
    return "Combines 2 vectors using some function to produce another vector.";
  }
}


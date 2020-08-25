package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.CombineFunction;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.VectorArrayInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorArrayOutputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.function.BiFunction;

public class MultiCombineNode extends NodeInOut {

  @TransferGrid
  private CombineFunction func;

  private VectorArrayInputLink A, B;
  private VectorArrayOutputLink out;


  public MultiCombineNode(CombineFunction _func) {
    func = _func;

    A = new VectorArrayInputLink("A");
    B = new VectorArrayInputLink("B");
    addInput(A, B);

    out = new VectorArrayOutputLink("Out") {
      @Override
      public Vector2[] get(RenderParams p) {
        Vector2[] gotA = A.get(p);
        Vector2[] gotB = B.get(p);

        Vector2[] toReturn = new Vector2[p.frameNum()];

        for (int i = 0; i < p.frameNum(); i++) {
          toReturn[i] = func.apply(gotA[i], gotB[i]);
        }

        return toReturn;
      }

      @Override
      public void generateGLSLMethod(StringBuilder s) {
        //TODO
        throw new NotImplementedException();
      }

      @Override
      public String getMethodName() {
        //TODO
        throw new NotImplementedException();
      }
    };
    addOutput(out);
  }

  public MultiCombineNode() {
    this(CombineFunction.ADD);
  }


  public static BiFunction<Vector2, Vector2, Vector2> ADD = Vector2::add;
  public static BiFunction<Vector2, Vector2, Vector2> SUB = Vector2::subtract;
  public static BiFunction<Vector2, Vector2, Vector2> MUL = Vector2::multiply;
  public static BiFunction<Vector2, Vector2, Vector2> DIV = Vector2::divide;

  @Override
  public String getName() {
    return "Multi Combine Node";
  }

  @Override
  public String getDescription() {
    return "Combines 2 vector arrays using some function to produce another vector.";
  }
}


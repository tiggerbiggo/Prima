package com.tiggerbiggo.primaplay.node.implemented.io;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.INodeHasInput;
import com.tiggerbiggo.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.OutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;
import java.util.function.BiFunction;

public class CombineNode extends NodeInOut {

  private BiFunction<Vector2, Vector2, Vector2> func;
  private VectorInputLink A, B;
  private VectorOutputLink out;


  public CombineNode(BiFunction<Vector2, Vector2, Vector2> func) {
    this.func = func;

    A = new VectorInputLink();
    B = new VectorInputLink();
    addInput(A, B);

    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        return func.apply(A.get(p), B.get(p));
      }
    };
    addOutput(out);
  }

  public CombineNode(BiFunction<Vector2, Vector2, Vector2> func, INodeHasOutput A, INodeHasOutput B){
    this(func);
    link(A);
    link(B, 1, 0);
  }

  public CombineNode(){
    this(ADD);
  }


  public static BiFunction<Vector2, Vector2, Vector2> ADD = Vector2::add;
  public static BiFunction<Vector2, Vector2, Vector2> SUB = Vector2::subtract;
  public static BiFunction<Vector2, Vector2, Vector2> MUL = Vector2::multiply;
  public static BiFunction<Vector2, Vector2, Vector2> DIV = Vector2::divide;
}

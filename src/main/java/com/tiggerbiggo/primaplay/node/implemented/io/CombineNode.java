package com.tiggerbiggo.primaplay.node.implemented.io;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;
import java.util.function.BiFunction;

public class CombineNode extends NodeInOut {

  private CombineFunction func;

  private VectorInputLink A, B;
  private VectorOutputLink out;


  public CombineNode(CombineFunction _func) {
    func = _func;

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

  public CombineNode(CombineFunction func, INodeHasOutput A, INodeHasOutput B) {
    this(func);
    link(A);
    link(B, 1, 0);
  }

  public CombineNode() {
    this(CombineFunction.ADD);
  }


  public static BiFunction<Vector2, Vector2, Vector2> ADD = Vector2::add;
  public static BiFunction<Vector2, Vector2, Vector2> SUB = Vector2::subtract;
  public static BiFunction<Vector2, Vector2, Vector2> MUL = Vector2::multiply;
  public static BiFunction<Vector2, Vector2, Vector2> DIV = Vector2::divide;

  @Override
  public String getName() {
    return "Combine Node";
  }

  @Override
  public String getDescription() {
    return "Combines 2 vectors using some function to produce another vector.";
  }
}

enum CombineFunction {
  ADD(Vector2::add),
  SUB(Vector2::subtract),
  MUL(Vector2::multiply),
  DIV(Vector2::divide);

  private BiFunction<Vector2, Vector2, Vector2> func;

  CombineFunction(BiFunction<Vector2, Vector2, Vector2> func) {
    this.func = func;
  }

  public Vector2 apply(Vector2 A, Vector2 B) {
    return func.apply(A, B);
  }
}

package com.tiggerbiggo.primaplay.node.implemented;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeHasInput;
import com.tiggerbiggo.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.OutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;
import java.util.function.BiFunction;

public class CombineNode implements NodeHasOutput, NodeHasInput{

  private BiFunction<Vector2, Vector2, Vector2> func;
  private VectorInputLink A, B;


  public CombineNode(BiFunction<Vector2, Vector2, Vector2> func) {
    this.func = func;
    A = new VectorInputLink();
    B = new VectorInputLink();
  }

  public CombineNode(BiFunction<Vector2, Vector2, Vector2> func, NodeHasOutput A, NodeHasOutput B){
    this(func);
    link(A);
    link(B, 1, 0);
  }

  public CombineNode(){
    this(ADD);
  }

  private VectorOutputLink out = new VectorOutputLink() {
    @Override
    public Vector2 get(RenderParams p) {
      return func.apply(A.get(p), B.get(p));
    }
  };

  @Override
  public InputLink<?>[] getInputs() {
    return new InputLink[]{A, B};
  }

  @Override
  public InputLink<?> getInput(int n) {
    if(n<0 || n>=2) return null;
    return getInputs()[n];
  }

  @Override
  public OutputLink<?>[] getOutputs() {
    return new OutputLink[]{out};
  }

  @Override
  public OutputLink<?> getOutput(int n) {
    return out;
  }

  public static BiFunction<Vector2, Vector2, Vector2> ADD = Vector2::add;
  public static BiFunction<Vector2, Vector2, Vector2> SUB = Vector2::subtract;
  public static BiFunction<Vector2, Vector2, Vector2> MUL = Vector2::multiply;
  public static BiFunction<Vector2, Vector2, Vector2> DIV = Vector2::divide;
}

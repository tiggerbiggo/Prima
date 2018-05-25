package com.tiggerbiggo.primaplay.node.implemented;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.primaplay.node.link.OutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;

public class ConstNode implements INodeHasOutput {
  private Vector2 value;

  public ConstNode(Vector2 value) {
    this.value = value;
  }

  public ConstNode(double x, double y){
    this(new Vector2(x, y));
  }

  public ConstNode(double value){
    this(value, value);
  }



  public void set(Vector2 toSet){
    if(toSet != null) value = toSet;
  }

  private VectorOutputLink out = new VectorOutputLink() {
    @Override
    public Vector2 get(RenderParams p) {
      return value;
    }
  };

  @Override
  public OutputLink<?>[] getOutputs() {
    return new OutputLink[]{out};
  }

  @Override
  public OutputLink<?> getOutput(int n) {
    return out;
  }
}

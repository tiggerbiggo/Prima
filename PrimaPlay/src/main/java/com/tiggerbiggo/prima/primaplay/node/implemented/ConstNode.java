package com.tiggerbiggo.prima.primaplay.node.implemented;

import ch.rs.reflectorgrid.TransferGrid;
import com.tiggerbiggo.utils.calculation.Vector2;
import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorOutputLink;

public class ConstNode implements INodeHasOutput {

  @TransferGrid
  private Vector2 value;

  private VectorOutputLink out;

  public ConstNode(Vector2 _value) {
    value = _value;
    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        return value;
      }
    };
    addOutput(out);
  }

  public ConstNode(double x, double y) {
    this(new Vector2(x, y));
  }

  public ConstNode(double value) {
    this(value, value);
  }

  public ConstNode() {
    this(1);
  }

  public void set(Vector2 toSet) {
    if (toSet != null) {
      value = toSet;
    }
  }


  @Override
  public String getName() {
    return "Constant Node";
  }

  @Override
  public String getDescription() {
    return "Will always return a constant Vector value.";
  }
}

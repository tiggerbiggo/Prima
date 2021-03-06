package com.tiggerbiggo.prima.play.node.implemented.output;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.INodeHasOutput;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorOutputLink;

public class ConstNode implements INodeHasOutput {

  @TransferGrid
  private Vector2 value;

  public ConstNode(Vector2 value) {
    this.value = value;
  }

  public ConstNode(double x, double y) {
    this(new Vector2(x, y));
  }

  public ConstNode(double value) {
    this(value, value);
  }

  public ConstNode(){ this(1); }

  public void set(Vector2 toSet) {
    if (toSet != null) {
      value = toSet;
    }
  }

  private VectorOutputLink out = new VectorOutputLink("Out") {
    @Override
    public Vector2 get(RenderParams p) {
      return value;
    }

    @Override
    public void generateGLSLMethod(StringBuilder s) {
      s.append("vec2 ConstSingle_"+hashCode()+"(){\nreturn "+value+";\n}");
    }

    @Override
    public String getMethodName() {
      return "ConstSingle_"+hashCode()+"()";
    }
  };

  private VectorArrayOutputLink outA = new VectorArrayOutputLink("Animated Out") {
    @Override
    public Vector2[] get(RenderParams p) {
      Vector2[] toReturn = new Vector2[p.frameNum()];

      for(int i=0;i<p.frameNum();i++){
        toReturn[i] = value;
      }
      return toReturn;
    }

    @Override
    public void generateGLSLMethod(StringBuilder s) {
      s.append("vec2 ConstMulti_"+hashCode()+"(){\nreturn "+value+";\n}");
    }

    @Override
    public String getMethodName() {
      return "ConstMulti_"+hashCode()+"()";
    }
  };


  @Override
  public OutputLink<?>[] getOutputs() {
    return new OutputLink[]{out, outA};
  }

  @Override
  public OutputLink<?> getOutput(int n) {
    return out;
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

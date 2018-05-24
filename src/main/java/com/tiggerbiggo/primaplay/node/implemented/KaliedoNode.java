package com.tiggerbiggo.primaplay.node.implemented;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeHasInput;
import com.tiggerbiggo.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.OutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;

public class KaliedoNode implements NodeHasOutput, NodeHasInput {

  Vector2 rotationPoint;
  int rotationNum;

  public KaliedoNode(Vector2 rotationPoint, int rotationNum) {
    this.rotationPoint = rotationPoint;
    this.rotationNum = rotationNum;
  }

  public KaliedoNode(int rotationNum) {
    this(Vector2.ZERO, rotationNum);
  }

  VectorInputLink map = new VectorInputLink();
  VectorOutputLink out = new VectorOutputLink() {
    @Override
    public Vector2 get(RenderParams p) {
      Vector2 point;
      double baseAngle, angle;
      int multiplier;

      point = map.get(p);
      point = Vector2.add(point, rotationPoint);

      baseAngle = Math.PI * 2;
      baseAngle /= rotationNum;

      angle = Vector2.angleBetween(new Vector2(5, 0), point);
      multiplier = (int) (angle / baseAngle);

      angle = baseAngle * -multiplier;
      if (multiplier % 2 == 0) {
        //angle = baseAngle - angle;
      }

      point = Vector2.rotateAround(point, rotationPoint, angle);
      point = Vector2.subtract(point, rotationPoint);

      return point;
    }
  };

  @Override
  public InputLink<?>[] getInputs() {
    return new InputLink[]{map};
  }

  @Override
  public InputLink<?> getInput(int n) {
    return map;
  }

  @Override
  public OutputLink<?>[] getOutputs() {
    return new OutputLink[]{out};
  }

  @Override
  public OutputLink<?> getOutput(int n) {
    return out;
  }
}

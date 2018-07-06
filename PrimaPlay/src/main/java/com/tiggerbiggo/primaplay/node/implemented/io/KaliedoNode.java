package com.tiggerbiggo.primaplay.node.implemented.io;

import ch.rs.reflectorgrid.TransferGrid;
import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.INodeHasInput;
import com.tiggerbiggo.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;

public class KaliedoNode implements INodeHasInput, INodeHasOutput {

  @TransferGrid
  Vector2 rotationPoint;

  @TransferGrid
  int rotationNum;

  VectorInputLink map;
  VectorOutputLink out;

  public KaliedoNode(Vector2 _rotationPoint, int _rotationNum) {
    rotationPoint = _rotationPoint;
    rotationNum = _rotationNum;

    map = new VectorInputLink();
    addInput(map);

    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        Vector2 point;
        double baseAngle, angle;
        int multiplier;

        point = map.get(p).add(rotationPoint);

        baseAngle = Math.PI * 2;
        baseAngle /= rotationNum;

        angle = new Vector2(5, 0).angleBetween(point);
        multiplier = (int) (angle / baseAngle);

        angle = baseAngle * -multiplier;
        if (multiplier % 2 == 0) {
          //angle = baseAngle - angle;
        }

        point = point.rotateAround(rotationPoint, angle);
        point = point.subtract(rotationPoint);

        return point;
      }
    };
    addOutput(out);
  }

  public KaliedoNode(int rotationNum) {
    this(Vector2.ZERO, rotationNum);
  }

  public KaliedoNode() {
    this(6);
  }

  @Override
  public String getName() {
    return "Kaliedo Node";
  }

  @Override
  public String getDescription() {
    return "Effectively performs a modulo operation on the rotation value of a given coordinate, creating a kaliedoscope effect.";
  }
}

package com.tiggerbiggo.primaplay.node.implemented.io;

import ch.rs.reflectorgrid.TransferGrid;
import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;

public class KaliedoNode extends NodeInOut {

  @TransferGrid
  Vector2 rotationPoint;

  @TransferGrid
  int rotationNum;

  VectorInputLink map;
  VectorOutputLink out;

  public KaliedoNode(Vector2 rotationPoint, int rotationNum) {
    this.rotationPoint = rotationPoint;
    this.rotationNum = rotationNum;

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

  @Override
  public String getName() {
    return "Kaliedo Node";
  }

  @Override
  public String getDescription() {
    return "Effectively performs a modulo operation on the rotation value of a given coordinate, creating a kaliedoscope effect.";
  }
}

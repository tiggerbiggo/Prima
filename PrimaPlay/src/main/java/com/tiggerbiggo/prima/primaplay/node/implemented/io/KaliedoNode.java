package com.tiggerbiggo.prima.primaplay.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.primaplay.core.render.RenderParams;
import com.tiggerbiggo.prima.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorOutputLink;
import com.tiggerbiggo.utils.calculation.Vector2;

public class KaliedoNode extends NodeInOut {

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

  public KaliedoNode(){this(6); }

  @Override
  public String getName() {
    return "Kaliedo Node";
  }

  @Override
  public String getDescription() {
    return "Effectively performs a modulo operation on the rotation value of a given coordinate, creating a kaliedoscope effect.";
  }
}

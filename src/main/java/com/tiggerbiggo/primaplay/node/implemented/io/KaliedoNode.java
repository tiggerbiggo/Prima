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

public class KaliedoNode extends NodeInOut {

  Vector2 rotationPoint;
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
    addOutput(out);
  }

  public KaliedoNode(int rotationNum) {
    this(Vector2.ZERO, rotationNum);
  }
}

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

public class MandelNode extends NodeInOut {

  int iterations;
  double multiplier;

  public MandelNode(int iterations, double multiplier) {
    this.iterations = iterations;
    this.multiplier = multiplier;

    map = new VectorInputLink();
    addInput(map);

    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        //get the point we are rendering
        Vector2 c = map.get(p);

        if (c == null) {
          return null;
        }
        //Start z at (0,0i), treating the y component as coefficient of i
        Vector2 z = Vector2.ZERO;

        for (int i = 0; i < iterations; i++) {
          //temporary hold values for this iteration
          double a, b;

          //set a and b from the current z value
          a = z.X();
          b = z.Y();

          //perform calculation for this iteration
          z = new Vector2(
              (a * a) - (b * b) + c.Y(),
              (2 * a * b) + c.X()
          );

          //check for out of bounds
          if (z.sqMagnitude() > (1 << 16)) {
            double smooth = (i + 1.0) - Math.log(Math.log(z.magnitude())) / Math.log(2);

            return new Vector2(smooth * multiplier);
          }
        }
        //edge case to return zero if never escapes
        return Vector2.ZERO;
      }
    };

    addOutput(out);
  }

  public MandelNode() {
    this(300, 0.1);
  }

  VectorInputLink map;
  VectorOutputLink out;
}

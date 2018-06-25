package com.tiggerbiggo.primaplay.node.implemented.io;

import ch.rs.reflectorgrid.TransferGrid;
import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;

public class MovementNode extends NodeInOut {

  VectorInputLink in;
  VectorOutputLink out;

  @TransferGrid
  private int iter;

  public MovementNode(int iter) {
    this.iter = iter;

    in = new VectorInputLink();
    addInput(in);

    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        Vector2 initial = in.get(p);
        RenderParams dP = p.clone();

        Vector2 current = initial.clone();
        for (int i = 0; i < iter; i++) {
          dP.setX(dP.x() + current.iX());
          dP.setY(dP.y() + current.iY());

          current = current.add(in.get(dP));
        }

        return initial.subtract(current.divide(new Vector2(iter)));
      }
    };
    addOutput(out);
  }

  @Override
  public String getName() {
    return "Movement Node";
  }

  @Override
  public String getDescription() {
    return "Iteratively moves the input vector across the vector field.";
  }
}

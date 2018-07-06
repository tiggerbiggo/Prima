package com.tiggerbiggo.prima.primaplay.node.implemented.io;

import ch.rs.reflectorgrid.TransferGrid;
import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.node.core.INodeHasInput;
import com.tiggerbiggo.prima.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayOutputLink;
import java.awt.Color;

public class SuperSampleNode implements INodeHasInput, INodeHasOutput {

  ColorArrayInputLink input;
  ColorArrayOutputLink output;

  @TransferGrid
  private int factor;

  public SuperSampleNode(int _factor) {
    this.factor = _factor;

    input = new ColorArrayInputLink();
    addInput(input);

    output = new ColorArrayOutputLink() {
      @Override
      public Color[] get(RenderParams p) {
        RenderParams p2 = new RenderParams(p.width() * factor, p.height() * factor, 0, 0,
            p.frameNum());

        double[] r, g, b;
        r = new double[p.frameNum()];
        g = new double[p.frameNum()];
        b = new double[p.frameNum()];
        for (int i = 0; i < factor; i++) {
          for (int j = 0; j < factor; j++) {
            p2.setX((p.x() * factor) + i);
            p2.setY((p.y() * factor) + j);

            //System.out.println("x, y: " + p2.x() + ", "+ p2.y());

            Color[] cA = input.get(p2);
            for (int k = 0; k < cA.length; k++) {
              r[k] += cA[k].getRed();

              g[k] += cA[k].getGreen();
              b[k] += cA[k].getBlue();
            }
            //System.out.println("i, j: " + i + ", " + j + ", r: " + r[0] + ", cA[0]: " + cA[0].getRed());
          }
          //System.out.println();
        }
        //System.out.println("_______________");

        int fac2 = factor * factor;

        Color[] toReturn = new Color[p.frameNum()];
        for (int i = 0; i < toReturn.length; i++) {
          toReturn[i] = new Color(
              (int) (r[i] / fac2),
              (int) (g[i] / fac2),
              (int) (b[i] / fac2)
          );
        }

        return toReturn;
      }
    };
    addOutput(output);
  }

  public SuperSampleNode() {
    this(2);
  }


  @Override
  public String getName() {
    return "Super Sample Node";
  }

  @Override
  public String getDescription() {
    return "applies supersampling to the image, internally rendering at a larger resolution then downscaling the image for better quality.";
  }
}
package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayOutputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.Color;

public class SuperSampleNode extends NodeInOut {

  private ColorArrayInputLink input;
  private ColorArrayOutputLink output;

  @TransferGrid
  private int factor;

  public SuperSampleNode(int _factor) {
    this.factor = _factor;

    input = new ColorArrayInputLink("In");
    addInput(input);

    output = new ColorArrayOutputLink("Out") {
      @Override
      public Color[] get(RenderParams p) {
        int width = p.width() * factor;
        int height = p.height() * factor;
        int framenum = p.frameNum();

        double[] r, g, b;
        r = new double[framenum];
        g = new double[framenum];
        b = new double[framenum];
        for (int i = 0; i < factor; i++) {
          for (int j = 0; j < factor; j++) {
            //p2.setX((p.x() * factor) + i);
            //p2.setY((p.y() * factor) + j);

            Color[] cA = input.get(new RenderParams(
                width, height,
                ((p.x() * factor) + i),
                ((p.y() * factor) + j),
                framenum,
                p.getId()
            ));
            for (int k = 0; k < cA.length; k++) {
              r[k] += cA[k].getRed();
              g[k] += cA[k].getGreen();
              b[k] += cA[k].getBlue();
            }
          }
        }

        double fac2 = factor * factor;

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

      @Override
      public void generateGLSLMethod(StringBuilder s) {
        //TODO
        throw new NotImplementedException();
      }

      @Override
      public String getMethodName() {
        //TODO
        throw new NotImplementedException();
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
package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderCache;
import com.tiggerbiggo.prima.play.core.render.RenderID;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorOutputLink;
import java.util.HashMap;
import java.util.Map;

public class ConvolutionNode extends NodeInOut {

  @TransferGrid
  Convolution c = Convolution.SOBEL1;

  transient final Map<RenderID, RenderCache<Vector2>> cacheMap;

  VectorInputLink in;

  VectorOutputLink out;

  public ConvolutionNode() {
    cacheMap = new HashMap<>();

    in = new VectorInputLink();
    addInput(in);

    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        synchronized (cacheMap) {
          if (!cacheMap.containsKey(p.getId())) {
            cacheMap.put(p.getId(), new RenderCache<>(new Vector2[p.width()][p.height()], in));
          }
        }

        RenderCache<Vector2> cache = cacheMap.get(p.getId());

        Vector2 toReturn = Vector2.ZERO;

        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            if (p.x() + i < 0 || p.x() + i >= p.width()) {
              continue;
            }
            if (p.y() + j < 0 || p.y() + j >= p.height()) {
              continue;
            }
            Vector2 toConv = cache.get(new RenderParams(p.width(), p.height(), p.x() + i, p.y() + j, p.frameNum(), p.getId()));
            toReturn = toReturn.add(c.convolutePosition(i, j, toConv));
          }
        }

        System.out.println(toReturn);

        return toReturn;
      }
    };
    addOutput(out);

  }


  @Override
  public String getName() {
    return "Convolution Node";
  }

  @Override
  public String getDescription() {
    return "Performs Kernel Convolutions on input";
  }
}

enum Convolution {
  SOBEL1(new double[][]{
      {1, 0, -1},
      {0, 0, 0},
      {-1, 0, 1}
  }),
  SOBEL2(new double[][]{
      {0, 1, 0},
      {1, -4, 1},
      {0, 1, 0}
  }),
  BOXBLUR(new double[][]{
      {1, 1, 1},
      {1, 1, 1},
      {1, 1, 1}
  })
  ;
  double[][] convolution;

  Convolution(double[][] _convolution) {
    convolution = _convolution;
  }

  public Vector2 convolutePosition(int x, int y, Vector2 in) {
    return in.multiply(convolution[x + 1][y + 1]);
  }
}

package com.tiggerbiggo.primaplay.node.implemented;

import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.primaplay.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorArrayInputLink;
import java.awt.Color;

public class TileNode extends NodeInOut {

  VectorArrayInputLink in;
  ColorArrayOutputLink out;

  int width, height;

  public TileNode(int width, int height) {
    this.width = width;
    this.height = height;

    in = new VectorArrayInputLink();
    addInput(in);

    out = new ColorArrayOutputLink() {
      @Override
      public Color[] get(RenderParams p) {

        return new Color[0];
      }
    };
    addOutput(out);
  }
}

package com.tiggerbiggo.primaplay.node.core;

import com.tiggerbiggo.primaplay.core.RenderParams;
import java.awt.image.BufferedImage;

public abstract class RenderNode implements NodeHasInput {
  public abstract BufferedImage[] Render(RenderParams p);
}

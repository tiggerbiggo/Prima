package com.tiggerbiggo.primaplay.node.old.core;

import com.tiggerbiggo.primaplay.core.RenderParams;
import java.awt.image.BufferedImage;

public abstract class RenderNode implements NodeHasInput {

  public abstract BufferedImage[] render(RenderParams p);
}

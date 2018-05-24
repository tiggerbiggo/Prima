package com.tiggerbiggo.primaplay.node.core;

import java.awt.image.BufferedImage;

public abstract class RenderNode implements NodeHasInput {

  public abstract BufferedImage[] render(int width, int height, int n);

  public abstract BufferedImage renderSingle(int width, int height);
}

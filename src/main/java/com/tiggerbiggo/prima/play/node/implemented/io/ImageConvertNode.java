package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorConvertType;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.ColorOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.ImageInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.defaults.MapGenDefaultLink;
import java.awt.Color;

public class ImageConvertNode extends NodeInOut{

  @TransferGrid
  private ColorConvertType convertX = ColorConvertType.V;
  @TransferGrid
  private ColorConvertType convertY = ColorConvertType.V;

  private VectorInputLink pos;
  private ImageInputLink img;

  private VectorOutputLink vecOut;
  private ColorOutputLink colOut;

  public ImageConvertNode(){
    pos = new MapGenDefaultLink();
    img = new ImageInputLink();
    addInput(pos, img);

    vecOut = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        Color sample = colOut.get(p);

        return new Vector2(
            convertX.convertColor(sample),
            convertY.convertColor(sample));
      }
    };

    colOut = new ColorOutputLink() {
      @Override
      public Color get(RenderParams p) {
        SafeImage currentImage = img.get(p);

        Vector2 position = pos.get(p);

        return currentImage.getColor(currentImage.denormVector(position));
      }
    };
    addOutput(vecOut, colOut);
  }

  @Override
  public String getName() {
    return "Image Convert Node";
  }

  @Override
  public String getDescription() {
    return "Converts a single still image to numbers.";
  }
}

package com.tiggerbiggo.prima.primaplay.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.primaplay.core.FileManager;
import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.graphics.ColorConvertType;
import com.tiggerbiggo.prima.primaplay.graphics.SafeImage;
import com.tiggerbiggo.prima.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.prima.primaplay.node.link.type.ImageInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorOutputLink;
import com.tiggerbiggo.utils.calculation.Vector2;
import java.awt.Color;
import java.lang.reflect.Field;

public class ImageConvertNode extends NodeInOut{

  @TransferGrid
  private ColorConvertType convertX = ColorConvertType.V;
  @TransferGrid
  private ColorConvertType convertY = ColorConvertType.V;

  private VectorInputLink pos;
  private ImageInputLink img;

  private VectorOutputLink out;

  public ImageConvertNode(){
    pos = new VectorInputLink();
    img = new ImageInputLink();
    addInput(pos, img);

    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        SafeImage currentImage = img.get(p);

        Vector2 position = pos.get(p);

        Color sample = currentImage.getColor(currentImage.denormVector(position));

        return new Vector2(
            convertX.convertColor(sample),
            convertY.convertColor(sample));
      }
    };
    addOutput(out);
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

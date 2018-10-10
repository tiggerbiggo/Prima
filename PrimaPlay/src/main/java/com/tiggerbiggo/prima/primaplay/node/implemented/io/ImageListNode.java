package com.tiggerbiggo.prima.primaplay.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.graphics.ColorConvertType;
import com.tiggerbiggo.prima.primaplay.graphics.ColorTools;
import com.tiggerbiggo.prima.primaplay.graphics.SafeImage;
import com.tiggerbiggo.prima.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.ImageArrayInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorArrayInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorArrayOutputLink;
import com.tiggerbiggo.utils.calculation.Vector2;
import java.awt.Color;

public class ImageListNode extends NodeInOut{
  @TransferGrid
  ColorConvertType convertX = ColorConvertType.V;

  @TransferGrid
  ColorConvertType convertY = ColorConvertType.V;

  VectorArrayInputLink uvLink;
  VectorArrayInputLink timeIn;
  ImageArrayInputLink imgLink;

  VectorArrayOutputLink vecOut;
  ColorArrayOutputLink colOut;

  public ImageListNode(){
    uvLink = new VectorArrayInputLink();
    timeIn = new VectorArrayInputLink();
    imgLink = new ImageArrayInputLink();

    addInput(uvLink, timeIn, imgLink);

    vecOut = new VectorArrayOutputLink() {
      @Override
      public Vector2[] get(RenderParams params) {
        Vector2[] position = uvLink.get(params);
        Vector2[] time = timeIn.get(params);
        Vector2[] toReturn = new Vector2[params.frameNum()];

        SafeImage[] imgs = imgLink.get(params);

        for(int i=0; i<toReturn.length; i++) {
          double percent = Math.abs(time[i].xy()) % 1;

          //multiply by number of images
          percent *= imgs.length;

          SafeImage img = imgs[(int)percent];

          Color sample = img.getColor(img.denormVector(position[i]));

          toReturn[i] = new Vector2(
              convertX.convertColor(sample),
              convertY.convertColor(sample));
        }

        return toReturn;
      }
    };
    addOutput(vecOut);

    colOut = new ColorArrayOutputLink() {
      @Override
      public Color[] get(RenderParams params) {
        //We use a list of vectors as the position in the image,
        //and a list of numbers as the time to return an array of colors.

        Vector2[] position = uvLink.get(params);
        Vector2[] time = timeIn.get(params);

        SafeImage[] imgs = imgLink.get(params);

        Color[] pixel = new Color[params.frameNum()];

        for (int i = 0; i < params.frameNum(); i++) {
          //raw percent value looped between 0 and 1
          double percent = Math.abs(time[i].xy()) % 1;

          //multiply by number of images
          percent *= imgs.length;

          //get index number
          int index = (int)percent;

          //re-normalise percentage
          percent %=1;

          SafeImage imgA = imgs[index];
          SafeImage imgB = imgs[(index + 1) % imgs.length];

          Color colorA = imgA.getColor(imgA.denormVector(position[i]));
          Color colorB = imgB.getColor(imgB.denormVector(position[(i+1) % params.frameNum()]));

          pixel[i] = ColorTools.colorLerp(colorA, colorB, percent);
        }
        return pixel;
      }
    };
    addOutput(colOut);
  }

  @Override
  public String getName() {
    return "Image List Node";
  }

  @Override
  public String getDescription() {
    return "Using a list of images, generates Color output based on position and time vectors.";
  }
}

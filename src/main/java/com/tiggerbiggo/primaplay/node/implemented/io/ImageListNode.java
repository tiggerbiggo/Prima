package com.tiggerbiggo.primaplay.node.implemented.io;

import com.tiggerbiggo.primaplay.calculation.Calculation;
import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.graphics.ColorTools;
import com.tiggerbiggo.primaplay.graphics.SafeImage;
import com.tiggerbiggo.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.primaplay.node.implemented.MapGenNode;
import com.tiggerbiggo.primaplay.node.implemented.NodeFactory;
import com.tiggerbiggo.primaplay.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.NumberArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorArrayInputLink;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class ImageListNode extends NodeInOut{
  List<SafeImage> imgs;

  VectorArrayInputLink uvLink;
  VectorArrayInputLink timeIn;

  ColorArrayOutputLink colOut;

  public ImageListNode(List<SafeImage> imgs){
    this.imgs = imgs;

    uvLink = new VectorArrayInputLink();
    uvLink.link(NodeFactory.stillAnim());

    timeIn = new VectorArrayInputLink();
    addInput(uvLink, timeIn);

    colOut = new ColorArrayOutputLink() {
      @Override
      public Color[] get(RenderParams params) {
        //We use a list of vectors as the position in the image,
        //and a list of numbers as the time to return an array of colors.

        Vector2[] position = uvLink.get(params);
        Vector2[] time = timeIn.get(params);

        Color[] pixel = new Color[params.frameNum()];

        for (int i = 0; i < params.frameNum(); i++) {
          //raw percent value looped between 0 and 1
          double percent = Math.abs(time[i].xy()) % 1;

          //multiply by number of images
          percent *= imgs.size();

          //get index number
          int index = (int)percent;

          //re-normalise percentage
          percent %=1;

          SafeImage imgA = imgs.get(index);
          SafeImage imgB = imgs.get((index + 1) % imgs.size());

          Color colorA = imgA.getColor(imgA.denormVector(position[i]));
          Color colorB = imgB.getColor(imgB.denormVector(position[(i+1) % params.frameNum()]));

          pixel[i] = ColorTools.colorLerp(colorA, colorB, percent);
        }
        return pixel;
      }
    };
    addOutput(colOut);
  }

  public ImageListNode(SafeImage ... imgs){
    this(Arrays.asList(imgs));
  }
}

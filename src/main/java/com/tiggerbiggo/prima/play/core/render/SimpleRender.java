package com.tiggerbiggo.prima.play.core.render;

import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayInputLink;
import java.awt.Color;

public class SimpleRender {
  private int width, height, frameNum;
  private RenderID id;

  public SimpleRender(int width, int height, int frameNum) {
    this.width = width;
    this.height = height;
    this.frameNum = frameNum;
    id = new RenderID();
  }

  public SafeImage[] render(ColorArrayInputLink input){
    //array of images, one for each frame
    SafeImage[] toReturn = new SafeImage[frameNum];
    for(int i=0; i<frameNum; i++){
      toReturn[i] = new SafeImage(width, height);
      //populate array with blank images
    }

    //loop over every pixel, get Color[] and apply those to each image
    for(int i=0; i<width; i++){
      for(int j=0; j<height; j++){
        Color[] cols = input.get(new RenderParams(width, height, i, j, frameNum, id));
        if(cols != null && cols.length == frameNum){
          for(int k=0; k<frameNum; k++){
            toReturn[k].setColor(i, j, cols[k]);
          }
        }
      }
    }

    return toReturn;
  }
}

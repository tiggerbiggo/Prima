package com.tiggerbiggo.prima.primaplay.core.render;

import com.tiggerbiggo.prima.primaplay.graphics.SafeImage;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayInputLink;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class RenderTask {
  int width, height, frameNum;

  ConcurrentLinkedDeque<RenderCoordinate> coords;
  ColorArrayInputLink in;

  AtomicInteger count;
  int startCount;
  String description;

  List<RenderCallback> callbacks;

  SafeImage[] imgs;

  public RenderTask(int _width, int _height, int _frameNum, ColorArrayInputLink _in, String _description, RenderCallback ... _callbacks){
    width = _width;
    height = _height;
    frameNum = _frameNum;
    in = _in;
    description = _description;

    callbacks = new ArrayList<>();
    if(_callbacks != null)
      callbacks.addAll(Arrays.asList(_callbacks));

    coords = new ConcurrentLinkedDeque<>();
    for(int i=0; i<width; i++){
      for(int j=0; j<height; j++){
        coords.add(new RenderCoordinate(i, j));
      }
    }

    imgs = new SafeImage[frameNum];
    for(int i=0; i<frameNum; i++){
      imgs[i] = new SafeImage(width, height);
    }

    startCount = width * height;
    count = new AtomicInteger(startCount);
  }

  public void addCallback(RenderCallback c){
    callbacks.add(c);
  }

  public boolean step(){
    RenderCoordinate coord = coords.poll();
    if(coord == null) return false;

    Color[] colors = in.get(new RenderParams(width, height, coord.x, coord.y, frameNum));
    if(colors.length != frameNum) return false;
    for(int i=0; i<frameNum; i++){
      imgs[i].setColor(coord.x, coord.y, colors[i]);
    }
    if(count.decrementAndGet() == 0)
    {
      callbacks.forEach(c -> c.callback(imgs));
      return false;
    }
    return true;
  }

  public SafeImage[] getImgs() {
    return imgs;
  }

  public double getPercentage(){
    return (double)count.get()/startCount;
  }

  public String getDescription(){
    return description;
  }
}

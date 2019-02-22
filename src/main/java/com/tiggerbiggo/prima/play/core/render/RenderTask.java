package com.tiggerbiggo.prima.play.core.render;

import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayInputLink;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.util.Pair;

public class RenderTask implements Runnable {

  private int width, height, frameNum;
  private RenderID id;

  private ColorArrayInputLink link;
  private List<RenderCallback> callbacks;
  private SafeImage[] imgs;
  private Lock[] locks;

  private AtomicInteger count;

  public RenderTask(int width, int height, int frameNum, ColorArrayInputLink link,
      RenderCallback... _callbacks) {
    this.width = width;
    this.height = height;
    this.frameNum = frameNum;
    this.link = link;
    id = new RenderID();

    imgs = new SafeImage[frameNum];
    for (int i = 0; i < imgs.length; i++) {
      imgs[i] = new SafeImage(width, height);
    }

    locks = new ReentrantLock[width];

    for (int i = 0; i < width; i++) {
      locks[i] = new ReentrantLock();
    }

    callbacks = new ArrayList<>();
    if (_callbacks != null) {
      callbacks.addAll(Arrays.asList(_callbacks));
    }

    count = new AtomicInteger(width * height);
  }

  @Override
  public void run() {
    out:
    for (int i = 0; i < width; i++) {
      if (locks[i].tryLock()) {
        //Lock, then don't unlock. This means that we don't have to perform any further checks.
        for (int j = 0; j < height; j++) {

          Color[] renderedPix = link.get(new RenderParams(width, height, i, j, frameNum, id));
          for (int k = 0; k < frameNum; k++) {
            imgs[k].setColor(i, j, renderedPix[k]);
          }

          if (count.decrementAndGet() == 1) {
            doCallbacks();
            break out;
          }
        }
      }
    }
  }

  private void doCallbacks() {
    for (RenderCallback c : callbacks) {
      c.callback(imgs);
    }
  }
}

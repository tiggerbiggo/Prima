package com.tiggerbiggo.prima.primaplay.core;

import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.utils.calculation.Vector2;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NewRenderer {
  private static ExecutorService exec = Executors.newFixedThreadPool(8);

  public static Future<BufferedImage[]> renderAsync(ColorArrayInputLink in, int width, int height, int num){
    return exec.submit(new RenderSlave(in, width, height, num));
  }

  static class RenderSlave implements Callable<BufferedImage[]> {
    private boolean isDone = false;
    private ConcurrentLinkedQueue<Vector2> posQueue;
    private ColorArrayInputLink in;
    private BufferedImage[] imgs;
    private int width, height, num;

    RenderSlave(ColorArrayInputLink _in, int _width, int _height, int _num){
      in = Objects.requireNonNull(_in);
      width = check(_width);
      height = check(_height);
      num = check(_num);

      imgs = new BufferedImage[num];
      for(int i=0; i<num; i++){
        imgs[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      }

      posQueue = new ConcurrentLinkedQueue<>();

      for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
          posQueue.add(new Vector2(i, j));
        }
      }
    }

    public int check(int n){
      if(n <=0) throw new IllegalArgumentException();
      return n;
    }

    @Override
    public BufferedImage[] call() throws Exception {
      //Get coordinate from the stack to populate variable before loop
      Vector2 pos = posQueue.poll();

      //Repeat until no more elements are available
      while (pos != null) {
        int x, y;
        x = pos.iX();
        y = pos.iY();

        //Calculates the array of colours from the next link
        Color[] colors = in.get(new RenderParams(width, height, x, y, num));

        //if invalid number of colours returned, break
        if (colors.length != num) {
          System.out.println("Error in Renderer Run method: Invalid color array length");
          break;
        } else {
          //Else render the colours to the image array
          //iterates over each image and sets the colour of the pixel at (x,y)
          for (int i = 0; i < num; i++) {
            imgs[i].setRGB(x, y, colors[i].getRGB());
          }
        }
        //get next position vector
        pos = posQueue.poll();
        if (Thread.interrupted()) {
          break; //Break if interrupted.
        }
      }

      return imgs;
    }
  }
}

package com.tiggerbiggo.primaplay.node.core;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The core class for building and rendering Fragment based images.
 */
public class Renderer implements Runnable {

  public static final int THREADNUM = 6;

  private Thread[] threads;
  private boolean setup = false;
  private boolean isDone = false;
  private ConcurrentLinkedQueue<Vector2> fragList;
  private InputLink<Color[]> link;
  private BufferedImage[] imgs;
  private int w, h, n;

  /**
   * Creates a new Renderer
   *
   * @param link The renderable link object
   * @param w The width of the image
   * @param h The height of the image
   * @param n The number of frames in the image
   */
  public Renderer(InputLink<Color[]> link, int w, int h, int n) {
    //Check for dangerous null values

    if (link == null) {
      throw new IllegalArgumentException("Link cannot be null");
    }

    //Create a stack to store the link objects in for easy retrieval by the threads.
    fragList = new ConcurrentLinkedQueue<>();

    this.link = link;

    //Store variables for width, height and number of frames
    this.w = w;
    this.h = h;
    this.n = n;

    //Iterate over the ranges 0 -> w and 0 -> h to add an entry for every link in the map.
    //This is needed so each thread knows which pixel to access when writing to the image.
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        fragList.add(new Vector2(i, j));
      }
    }

    //Creates a new array of images and populates it.
    imgs = new BufferedImage[n];
    for (int i = 0; i < n; i++) {
      imgs[i] = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    }
  }

  public static BufferedImage[] render(InputLink<Color[]> link, int width, int height, int n) {
    Renderer r = new Renderer(link, width, height, n);
    r.startBuild();
    r.joinAll();
    return r.getImgs();
  }

  public static BufferedImage renderSingle(InputLink<Color[]> link, int width, int height) {
    return render(link, width, height, 1)[0];
  }

  /**
   * Optional callback method, can be overridden to call back after each rendered pixel completed
   *
   * @param x the X coordinate of the pixel that was just rendered
   * @param y the Y coordinate of the pixel that was just rendered
   */
  public void callback(int x, int y) {
  }

  /**
   * Creates an array of threads and starts them running, effectively starting the render process.
   */
  public void startBuild() {
    threads = new Thread[THREADNUM];
    for (int i = 0; i < THREADNUM; i++) {
      threads[i] = new Thread(this);
    }
    setup = true;
    for (Thread t : threads) {
      t.start();
    }
  }

  /**
   * Interrupts all threads then waits until execution finishes. Once execution returns all threads
   * will have stopped.
   */
  public void interruptAll() {
    for (int i = 0; i < THREADNUM; i++) {
      try {
        threads[i].interrupt();
      } catch (SecurityException ex) {
        System.err.println("Interrupt failed: Security Exception.");
        return;
      }
    }
    joinAll();
  }

  /**
   * The main run method. This should not be called from outside this class. Keeps rendering each
   * pixel until completed.
   */
  @Override
  public void run() {
    //Check if setup has been done
    if (setup) {
      //Get coordinate from the stack to populate variable before loop
      Vector2 pos = getNext();

      //Repeat until no more elements are available
      while (pos != null) {
        int x, y;
        x = pos.iX();
        y = pos.iY();

        //Calculates the array of colours from the next link
        Color[] colors = link.get(new RenderParams(w, h, x, y, n));

        //if invalid number of colours returned, break
        if (colors.length != n) {
          System.out.println("Error in Renderer Run method: Invalid color array length");
          break;
        } else {
          //Else render the colours to the image array
          //iterates over each image and sets the colour of the pixel at (x,y)
          for (int i = 0; i < n; i++) {
            imgs[i].setRGB(x, y, colors[i].getRGB());
          }
        }
        //get next position vector
        pos = getNext();

        //Ended pixel render, call back
        callback(x, y);
        if (Thread.interrupted()) {
          break; //Break if interrupted.
        }
      }
    }
  }

  /**
   * Synchronized pop from the list of positions
   *
   * @return Next element if exists, if stack empty returns null
   */
  private Vector2 getNext() {
    return fragList.poll();
  }

  /**
   * Joins all currently working threads in this object to the thread that called this method. This
   * effectively results in the thread waiting until the build operation has completed.
   */
  public void joinAll() {
    if (setup) {
      for (Thread t : threads) {
        try {
          t.join();
        } catch (InterruptedException | NullPointerException e) {
        }
      }
    }
  }

  /**
   * @return Boolean value for whether or not the calculation has finished.
   */
  public boolean isDone() {
    if (!isDone) {
      if (!setup) {
        return false;
      }
      for (int i = 0; i < THREADNUM; i++) {
        try {
          if (threads[i].isAlive()) {
            return false;
          }
        } catch (Exception e) {
          return false;
        }
      }
      isDone = true;
      return true;
    } else {
      return true;
    }
  }

  /**
   * @return The image array. Can be used even if calculation unfinished, resulting in viewing the
   * images as they are rendering.
   */
  public BufferedImage[] getImgs() {
    return imgs;
  }
}

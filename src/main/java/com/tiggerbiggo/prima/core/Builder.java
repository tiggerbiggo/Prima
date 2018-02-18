package com.tiggerbiggo.prima.core;

import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;

/**
 * The core class for building and rendering Fragment based images.
 */
public class Builder implements Runnable {
    public static final int THREADNUM = 6;

    private Thread[] threads;
    private boolean setup = false;
    private boolean isDone = false;
    private ArrayDeque<Vector2> fragList;
    private Fragment<Color[]> fragment;
    private BufferedImage[] imgs = null;
    private int w, h, n;

    /**Creates a new Builder
     *
     * @param fragment The renderable fragment object
     * @param w The width of the image
     * @param h The height of the image
     * @param n The number of frames in the image
     */
    public Builder(Fragment<Color[]> fragment, int w, int h, int n) {
        //Check for dangerous null values

        if (fragment == null) {
            throw new IllegalArgumentException("Fragment cannot be null");
        }

        //Create a stack to store the fragment objects in for easy retrieval by the threads.
        fragList = new ArrayDeque<>();

        this.fragment = fragment;

        //Store variables for width, height and number of frames
        this.w = w;
        this.h = h;
        this.n = n;

        //Iterate over the ranges 0 > w and 0 > h to add an entry for every fragment in the map.
        //This is needed so each thread knows which pixel to access when writing to the image.
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                fragList.add(new Vector2(i, j));

        //Creates a new array of images and populates it.
        imgs = new BufferedImage[n];
        for (int i = 0; i < n; i++) {
            imgs[i] = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        }
    }

    /**
     * Optional callback method, can be overridden to call back after each rendered pixel completed
     *
     * @param x the X coordinate of the pixel that was just rendered
     * @param y the Y coordinate of the pixel that was just rendered
     */
    public void callback(int x, int y){}

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
     * Interrupts all threads then waits until execution finishes.
     * Once execution returns all threads will have stopped.
     */
    public void interruptAll() {
        for (int i = 0; i < THREADNUM; i++) {
            try {
                threads[i].interrupt();
            } catch (SecurityException ex) {}
        }
        joinAll();
    }

    /**
     * The main run method. This should not be called from outside this class.
     * Keeps rendering each pixel until completed.
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

                //Calculates the array of colours from the next fragment
                Color[] colors = fragment.get(x, y, w, h, n);

                //if invalid number of colours returned, break
                if (colors.length != n) {
                    System.out.println("Error in Builder Run method: Invalid color array length");
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
                if(Thread.interrupted()) break; //Break if interrupted.
            }
        }
    }

    /**
     * Synchronized pop from the list of positions
     *
     * @return Next element if exists, if stack empty returns null
     */
    private synchronized Vector2 getNext() {
        if (fragList.isEmpty())
            return null;
        return fragList.pop();
    }

    /**
     * Joins all currently working threads in this object to the thread that called this method.
     * This effectively results in the thread waiting until the build operation has completed.
     */
    public void joinAll() {
        if (setup) {
            for (Thread t : threads) {
                try {
                    t.join();
                } catch (InterruptedException | NullPointerException e) {}
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
                    if (threads[i].isAlive())
                        return false;
                } catch (Exception e) {
                    return false;
                }
            }
            isDone = true;
            return true;
        } else return true;
    }

    /**
     * @return The image array. Can be used even if calculation unfinished, resulting in viewing the images as they are rendering.
     */
    public BufferedImage[] getImgs() {
        return imgs;
    }
}

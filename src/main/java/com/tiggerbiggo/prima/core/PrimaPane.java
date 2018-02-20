package com.tiggerbiggo.prima.core;

import com.tiggerbiggo.prima.processing.fragment.Fragment;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 */
public class PrimaPane extends JPanel{
    private Fragment<Color[]> in;
    private Fragment<Color[]>[][] map;

    private BufferedImage[] imgs = null;

    private int counter = 0;
    private boolean building = true;

    private Timer t;
    Builder b = null;

    /**
     * @return
     *
     * @return
     */
    public PrimaPane(Fragment<Color[]> in) {
        super();
        this.in = in;
    }

    /**
     * @param
     *
     * @return PrimaPane
     */
    public PrimaPane startTimer(long ms){
        t = new Timer();
        TimerTask tt = new TimerTask() {
            /**
             * @param
             */
            @Override
            public void run() {
                repaint();
            }
        };
        t.scheduleAtFixedRate(tt, 0, ms);

        return this;
    }

    /**
     * @param
     */
    public void stopTimer(){
        t.cancel();
        t = null;
    }

    /**
     * @param
     *
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(building && b != null)
            imgs = b.getImgs();
        if(imgs != null)
            g.drawImage(imgs[counter % imgs.length], 0, 0, null);
        counter++;

    }

    /**
     * @param
     * @return PrimaPane
     */
    public synchronized PrimaPane reBuild(){
        try {
            b = new Builder(in, getWidth(), getHeight(), 60);
            b.startBuild();

            building = true;
            b.joinAll();
            building = false;
        }
        catch(Exception e){
            System.out.println("bad things");
        }
        return this;
    }

    /**
     * @author A678364
     * Created on 20/02/2018
     * @return BufferedImage[]
     */
    public BufferedImage[] getImgs() {
        return imgs;
    }
}

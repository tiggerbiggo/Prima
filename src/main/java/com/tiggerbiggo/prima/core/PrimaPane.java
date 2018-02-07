package com.tiggerbiggo.prima.core;

import com.tiggerbiggo.prima.processing.fragment.Fragment;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class PrimaPane extends JPanel{
    private Fragment<Color[]> in;
    private Fragment<Color[]>[][] map;

    private BufferedImage[] imgs = null;

    private int counter = 0;
    private boolean building = true;

    private Timer t;
    Builder b = null;

    public PrimaPane(Fragment<Color[]> in) {
        super();
        this.in = in;
    }

    public PrimaPane startTimer(long ms){
        t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        };
        t.scheduleAtFixedRate(tt, 0, ms);

        return this;
    }

    public void stopTimer(){
        t.cancel();
        t = null;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(building && b != null)
            imgs = b.getImgs();
        if(imgs != null)
            g.drawImage(imgs[counter % imgs.length], 0, 0, null);
        counter++;

    }

    public synchronized PrimaPane reBuild(){
        try {
            map = in.build(getWidth(), getHeight());
            b = new Builder(map);
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

    public BufferedImage[] getImgs() {
        return imgs;
    }
}

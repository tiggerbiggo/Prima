package com.tiggerbiggo.prima.processing.tasks.render;

import com.tiggerbiggo.prima.core.int2;
import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.processing.tasks.Task;

import java.awt.image.BufferedImage;

public class RenderTask extends Task
{
    BufferedImage img;
    float[][] map;
    int width, height, current, max;
    Gradient g;

    public RenderTask(float[][] map, Gradient g) throws IllegalArgumentException
    {
        if(map == null) throw new IllegalArgumentException("Map must be non-null");

        width = map.length;
        height = map[0].length;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        current = 0;
        max = width * height;

        this.map = map;
        this.g = g;
    }


    @Override
    public void doTask()
    {
        int2 indexToRender = getNext();
        if(indexToRender == null) return;

        float result = getResult(indexToRender);

        img.setRGB(indexToRender.getX(), indexToRender.getY(), g.evaluate(result).getRGB());
    }

    protected float getResult(int2 in)
    {
        int x, y;
        x = in.getX();
        y = in.getY();

        return map[x][y];
    }

    public synchronized int2 getNext() {
        if(current <=-1 || isDone()) return null;

        return new int2(current%width, current++/ width);
    }

    @Override
    public boolean isDone() {
        return current >= max;
    }

    public BufferedImage getImage()
    {
        return img;
    }
}

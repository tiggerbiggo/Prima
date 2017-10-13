package Graphics;

import Calculation.Calculation;
import Core.float2;
import Core.int2;
import Processing.Task;

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
    public void doTask(Object in)
    {
        int2 converted = convertObject(in);
        if(converted == null) return;

        float result = getResult(converted);

        img.setRGB(converted.getX(), converted.getY(), g.evaluate(result).getRGB());
    }

    protected float getResult(int2 in)
    {
        int x, y;
        x = in.getX();
        y = in.getY();

        return map[x][y];
    }

    @Override
    public Object getNext() {
        if(current <=-1 || isDone()) return null;

        return new int2(current%width, current++/ width);
    }

    @Override
    public boolean isDone() {
        return current >= max;
    }

    int2 convertObject(Object in)
    {
        try
        {
            return (int2)in;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public BufferedImage getImage()
    {
        return img;
    }
}

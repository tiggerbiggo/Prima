package Graphics;

import Calculation.Calculation;
import Core.float2;
import Core.int2;
import Processing.Task;

import java.awt.image.BufferedImage;

public class RenderTask extends Task
{
    BufferedImage img;
    float2[][] map;
    int width, height, current, max;
    RenderMode mode;
    Gradient g;

    public RenderTask(float2[][] map, RenderMode mode, Gradient g) throws IllegalArgumentException
    {
        if(map == null) throw new IllegalArgumentException("Map must be non-null");

        width = map.length;
        height = map[0].length;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        current = 0;
        max = width * height;

        this.map = map;
        this.g = g;
        this.mode = mode;
    }


    @Override
    public void doTask(Object in) {
        int2 converted = convertObject(in);
        if(in == null) return;

        float result = 0;

        int x, y;
        x = converted.getX();
        y = converted.getY();

        float2 cache = map[x][y];

        switch(mode)
        {
            case ADD:
                result = cache.getX() + cache.getY();
                break;
            case MULTIPLY:
                result = cache.getX() * cache.getY();
                break;
        }

        img.setRGB(x, y, g.evaluate(result, false).getRGB());
    }

    @Override
    public Object getNext() {
        if(current <=-1 || isDone()) return null;

        return new int2(current%(width -1), current++/ height);
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

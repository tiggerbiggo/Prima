package Graphics;

import Core.float2;
import Core.int2;
import Processing.Task;

/**
 * Created by Administrator on 13/10/2017.
 */
public class PreRenderTask extends Task
{
    float2[][] map;
    float[][] outMap;
    RenderMode mode;
    int width, height, current, max;

    public PreRenderTask(float2[][] map, RenderMode mode) throws IllegalArgumentException
    {
        if(map == null) throw new IllegalArgumentException("Map must be non-null");

        width = map.length;
        height = map[0].length;

        max = width * height;

        outMap = new float[width][height];

        this.map = map;
        this.mode = mode;
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

    @Override
    public void doTask(Object in) {
        int2 converted = convertObject(in);

        int x, y;
        x = converted.getX();
        y = converted.getY();

        float2 cache = map[x][y];

        float result = 0;
        switch(mode)
        {
            case ADD:
                result = cache.getX() + cache.getY();
                break;
            case MULTIPLY:
                result = cache.getX() * cache.getY();
                break;
        }
        outMap[x][y] = result;
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

    public float[][] getOutMap()
    {
        return outMap;
    }
}

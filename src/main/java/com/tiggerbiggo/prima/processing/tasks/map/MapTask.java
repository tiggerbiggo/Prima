package com.tiggerbiggo.prima.processing.tasks.map;
import com.tiggerbiggo.prima.core.float2;
import com.tiggerbiggo.prima.processing.tasks.Task;

public abstract class MapTask extends Task
{
    protected float2[][] map;
    protected int current = 0;
    protected int max;
    protected int width, height;

    public MapTask(float2[][] map)
    {
        if(map == null) throw new IllegalArgumentException("Map must not be null!");
        this.map = map;
        width = map.length;
        height = map[0].length;
        max = width * height;
    }

    public abstract void doTask();

    public synchronized float2 getNext() {
        if(current <=-1 || isDone()) return null;

        float2 got = map[current%width][current/ width];
        current++;
        return got;
    }

    @Override
    public boolean isDone() {
        return current >= max;
    }

    float2 convertObject(Object in)
    {
        try
        {
            return (float2)in;
        }
        catch(Exception e)
        {
            return null;
        }
    }
}

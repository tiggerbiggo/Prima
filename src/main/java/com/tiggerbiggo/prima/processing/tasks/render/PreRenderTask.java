package com.tiggerbiggo.prima.processing.tasks.render;

import com.tiggerbiggo.prima.core.float2;
import com.tiggerbiggo.prima.core.int2;
import com.tiggerbiggo.prima.graphics.RenderMode;
import com.tiggerbiggo.prima.processing.tasks.Task;

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

    public synchronized int2 getNext() {
        if(current <=-1 || isDone()) return null;

        return new int2(current%width, current++/ width);
    }

    @Override
    public boolean isDone() {
        return current >= max;
    }

    @Override
    public void doTask() {
        int2 indexToFlatten = getNext();

        if(indexToFlatten == null) return;

        int x, y;
        x = indexToFlatten.getX();
        y = indexToFlatten.getY();

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

    public float[][] getOutMap()
    {
        return outMap;
    }
}

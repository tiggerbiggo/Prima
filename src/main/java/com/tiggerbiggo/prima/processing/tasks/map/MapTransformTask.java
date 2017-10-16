package com.tiggerbiggo.prima.processing.tasks.map;
import com.tiggerbiggo.prima.core.float2;

public class MapTransformTask extends MapTask
{
    public MapTransformTask(float2[][] map)
    {
        super(map);
    }

    @Override
    public void doTask() {
        float2 toTransform = getNext();

        if(toTransform == null) return;

        doFormula(toTransform);
    }

    public void doFormula(float2 in)
    {
        //Override method from object constructor.
    }

    public float2[][] getMap()
    {
        return map;
    }
}

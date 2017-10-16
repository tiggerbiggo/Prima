package com.tiggerbiggo.prima.processing.tasks.map;
import com.tiggerbiggo.prima.core.float2;

/**
 * An example implementation of Task which swaps the x and y components of each float2 object.
 * @author tiggerbiggo
 */
public class ExampleTask extends MapTask
{

    public ExampleTask(float2[][] map)
    {
        super(map);
    }

    @Override
    public void doTask() {
        float2 toTransform = getNext();
        if(toTransform == null) return;
        //swap the x and y coords

        float tmp = toTransform.getX();
        toTransform.set(toTransform.getY(), tmp);
    }
}

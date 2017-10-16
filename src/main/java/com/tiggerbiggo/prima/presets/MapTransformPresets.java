package com.tiggerbiggo.prima.presets;

import com.tiggerbiggo.prima.core.float2;
import com.tiggerbiggo.prima.processing.tasks.map.MapTransformTask;

public class MapTransformPresets
{

    public static MapTransformTask getPreset(float2[][] map, TransformTypes type)
    {
        switch(type)
        {
            case SINSIN:
                return new MapTransformTask(map){
                    @Override
                    public void doFormula(float2 in)
                    {
                        float x, y;
                        x = in.getX();
                        y = in.getY();

                        y=(float)Math.sin(y)/(float)Math.PI;
                        x=(float)Math.sin(x)/(float)Math.PI;

                        in.set(x, y);
                    }
                };
            case SINCOS:
                return new MapTransformTask(map){
                    @Override
                    public void doFormula(float2 in)
                    {
                        float x, y;
                        x = in.getX();
                        y = in.getY();

                        y=(float)Math.sin(y);
                        x=(float)Math.cos(x);

                        in.set(x, y);
                    }
                };
            case MAGNETISM:
                return new MapTransformTask(map){
                    @Override
                    public void doFormula(float2 in)
                    {
                        float x, y;
                        x = in.getX();
                        y = in.getY();

                        y=(float)Math.sin(Math.cosh(x) * y);


                        in.set(x, y);
                    }
                };
            case OTHER:
                return new MapTransformTask(map){
                    @Override
                    public void doFormula(float2 in)
                    {
                        float x, y;
                        x = in.getX();
                        y = in.getY();

                        y=(float)Math.sin(Math.tanh(x)*Math.tan(y));

                        in.set(x, y);
                    }
                };
        }
        return null;
    }
}

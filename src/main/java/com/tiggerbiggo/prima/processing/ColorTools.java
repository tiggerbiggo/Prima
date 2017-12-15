package com.tiggerbiggo.prima.processing;

import java.awt.*;

public class ColorTools
{
    public static float getBrightness(Color in)
    {
        return getMax(in) / 255.0f;
    }

    public static float getSaturation(Color in)
    {
        float min, max;
        min = getMin(in);
        max = getMax(in);

        if (max != 0)
            return (max - min) / max;
        else
            return 0;
    }

    public static float getHue(Color in)
    {

        return 0;
    }

    public static int getMax(Color in){
        return Math.max(in.getRed(), Math.max(in.getGreen(), in.getBlue()));
    }

    public static int getMin(Color in){
        return Math.min(in.getRed(), Math.min(in.getGreen(), in.getBlue()));
    }
}

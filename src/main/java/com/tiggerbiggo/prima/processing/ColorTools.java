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
        if(getSaturation(in) == 0)
            return 0;

        float min, max;
        min = getMin(in);
        max = getMax(in);

        float redc = (max - in.getRed()) / (max - min);
        float greenc = (max - in.getGreen()) / (max - min);
        float bluec = (max - in.getBlue()) / (max - min);

        float hue;

        if (in.getRed() == max)
            hue = bluec - greenc;
        else if (in.getGreen() == max)
            hue = 2.0f + redc - bluec;
        else
            hue = 4.0f + greenc - redc;
        hue = hue / 6.0f;
        if (hue < 0)
            hue = hue + 1.0f;
        return hue;
    }

    public static int getMax(Color in){
        return Math.max(in.getRed(), Math.max(in.getGreen(), in.getBlue()));
    }

    public static int getMin(Color in){
        return Math.min(in.getRed(), Math.min(in.getGreen(), in.getBlue()));
    }
}

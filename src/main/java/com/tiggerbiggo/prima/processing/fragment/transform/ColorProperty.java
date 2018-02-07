package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.calculation.ColorTools;
import java.awt.Color;
import java.io.Serializable;
import java.util.function.Function;

/**
 * Provides a means of converting colors to doubles using various properties of that color.
 */
public enum ColorProperty implements Serializable
{
    H((color -> {
        return ColorTools.getHue(color);
    })),
    S((color -> {
        return ColorTools.getSaturation(color);
    })),
    V((color -> {
        return ColorTools.getBrightness(color);
    })),
    R((color -> {
        return color.getRed()/255d;
    })),
    G((color -> {
        return color.getGreen()/255d;
    })),
    B((color -> {
        return color.getBlue()/255d;
    }));

    Function<Color, Double> func;

    ColorProperty(Function<Color, Double> func) {
        this.func = func;
    }

    public double convert(Color c)
    {
        return func.apply(c);
    }
}

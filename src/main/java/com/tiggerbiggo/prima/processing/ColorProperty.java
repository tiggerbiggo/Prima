package com.tiggerbiggo.prima.processing;

import java.awt.*;
import java.util.function.Function;

public enum ColorProperty
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
        return color.getRed()/255f;
    })),
    G((color -> {
        return color.getGreen()/255f;
    })),
    B((color -> {
        return color.getBlue()/255f;
    }));

    Function<Color, Float> func;

    ColorProperty(Function<Color, Float> func) {
        this.func = func;
    }

    public float convert(Color c)
    {
        return func.apply(c);
    }
}

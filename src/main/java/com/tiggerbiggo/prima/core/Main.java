package com.tiggerbiggo.prima.core;

import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.presets.MapTypes;
import com.tiggerbiggo.prima.presets.TransformTypes;

import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        Builder b = new Builder(8, 500, 500);
        b.setPrint(true);

        Gradient g = new Gradient(Color.black, Color.red, true);

        b.fullBuildAndWrite(
                new float2(0, 0),
                new float2(5, 5),
                MapTypes.REGULAR,
                TransformTypes.MAGNETISM,
                60,
                g,
                "Ayylmao");
    }
}

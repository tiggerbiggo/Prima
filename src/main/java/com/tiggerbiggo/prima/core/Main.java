package com.tiggerbiggo.prima.core;

import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.presets.MapTypes;
import com.tiggerbiggo.prima.presets.TransformTypes;

import java.awt.*;
import java.io.File;

public class Main
{
    public static void main(String[] args)
    {
        Gradient g = new Gradient(Color.blue, Color.green, true);
        Builder b = new Builder(8, new float2(0, 0),
                new float2(10, 10),MapTypes.REGULAR,
                TransformTypes.SINSIN,g);

        FileManager.writeGif(b.build(300, 300, 60), "Giffy");
    }
}

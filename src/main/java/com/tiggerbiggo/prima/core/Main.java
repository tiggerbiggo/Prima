package com.tiggerbiggo.prima.core;

import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.presets.MapGenerator;
import com.tiggerbiggo.prima.presets.MapTypes;
import com.tiggerbiggo.prima.presets.Transform;
import com.tiggerbiggo.prima.processing.ColorProperty;
import com.tiggerbiggo.prima.processing.fragment.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main
{

    public static void main(String[] args)
    {
        Gradient g = new Gradient(Color.white, Color.blue, true);

        String[] filenames = {
                "conchie.png"
        };

        int[] scales = {
                2,
                1,
                1
        };

        for(int i=0; i<filenames.length; i++)
        {
            doImage(filenames[i], filenames[i], g, scales[i]);
        }
    }

    public static void doImage(String imageName, String outName, Gradient g, int scaleFactor)
    {
        BufferedImage img = null;

        try
        {
            img = ImageIO.read(new File(imageName));
        }
        catch (Exception e)
        {
            System.exit(1);
        }

        MapGenFragment gen = new MapGenFragment(new float2(0), new float2(1));
        //TransformFragment t = new TransformFragment(gen, Transform.TANNY);
        //CombineFragment combine = new CombineFragment(t, new ConstFragment(new float2(1)), CombineType.ADD);
        ImageConvertFragment convert = new ImageConvertFragment(img, gen, ColorProperty.V);
        RenderFragment render = new RenderFragment(convert, 60, g);

        try
        {
            Builder B = new Builder(render.build(new int2(img.getWidth()/scaleFactor, img.getHeight()/scaleFactor)));
            B.startBuild();
            B.joinAll();

            FileManager.writeGif(B.getImgs(), outName);
        }
        catch (Exception e)
        {

        }
    }
}

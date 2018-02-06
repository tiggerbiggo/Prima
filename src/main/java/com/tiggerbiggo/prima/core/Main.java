package com.tiggerbiggo.prima.core;

import com.tiggerbiggo.prima.graphics.DoubleGradient;
import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.graphics.SafeImage;
import com.tiggerbiggo.prima.graphics.SimpleGradient;
import com.tiggerbiggo.prima.presets.Transform;
import com.tiggerbiggo.prima.processing.ColorProperty;
import com.tiggerbiggo.prima.processing.fragment.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main
{
    static final int XDIM = 300;
    static final int YDIM = 300;
    public static void main(String[] args) {

        Fragment<Vector2> f, d;

        //f = new MapGenFragment(imageSize.minus(), imageSize);
        f = new MapGenFragment(new Vector2(-2), new Vector2(2));
        d = new MapGenFragment(new Vector2(-2), new Vector2(2));
        //f = new CombineFragment(f, new ConstFragment(2.8), CombineType.MULTIPLY);
        //f = new NearestPointFragment(f, points);
        //f = new CombineFragment(f, new NoiseGenFragment(0.1), CombineType.ADD);
        //f = new KaliedoFragment(4, f, new Vector2(0, 0));
        //f = new CombineFragment(new ConstFragment(.1), new NearestPointFragment(f, points), CombineType.MULTIPLY);
        //f = new CombineFragment(f, d, CombineType.ADD);
        //f = new TransformFragment(f, Transform.MAGNETISM);
        //f = new NearestPointFragment(f, points);
        f = new MandelFragment(f, 500);
        //f = new CombineFragment(f, new ConstFragment(0.01), CombineType.MULTIPLY);
        //f = new TransformFragment(f, Transform.SINSIN);
        //d = new TransformFragment(d, Transform.MAGNETISM);

        //f = new ImageConvertFragment(img, f, ColorProperty.V);
        //f = new CombineFragment(f, new ConstFragment(0.6), CombineType.MULTIPLY);

        Fragment<Color[]> r;

        Gradient g;
        //g= new DoubleGradient(Color.BLACK,  Color.blue, Color.red, true);
        g = new SimpleGradient(Color.white, Color.BLUE, true);

        //r = new RenderFragment(f, 60, g);
        //r = new ImageCycleFragment(images, f, 0, 0);
        //r = new ImageMapFragment(f, img, 60, new Vector2(300));
        //r = new FadeImageFragment(120, true, f, images);
        //r = new RotateImageFragment(new SafeImage(img),f, 120, new Vector2(1000), new Vector2(500, 1000));
        //r = new SuperSampleFragment(1, r);
        //r = new RawColorFragment(f, 60);
        r = new OscillateFragment(f, d, 60, g);
        try
        {
            Builder b;
            //b = new Builder(render.build(new Vector2(img.getWidth()/5, img.getHeight()/5)));
            //b = new Builder(r.build(imageSize.iX(), imageSize.iY()));
            b = new Builder(r.build(XDIM,YDIM));
            b.startBuild();
            b.joinAll();

            String filename;
            int number = 1;
            do{
                filename = "morethings" + number;
                System.out.println("File: " + filename);
                number++;
            }while(new File(filename + ".gif").exists());

            FileManager.writeGif(b.getImgs(), filename);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

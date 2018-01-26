package com.tiggerbiggo.prima.core;

import com.tiggerbiggo.prima.graphics.DoubleGradient;
import com.tiggerbiggo.prima.graphics.Gradient;
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
    public static void main(String[] args) {


//        for(int i=0; i<50; i++) {
//            Random r = new Random();
//            double x, y;
//            x = r.nextDouble()*2; x-=1;
//            y = r.nextDouble()*2; y-=1;
//            points.add(new Vector2(x, y));
//        }

        ArrayList<Vector2> points = new ArrayList<>();
        points.add(new Vector2(0));

        int n = 1;
        for (double mul = 0; mul <= 1; mul += 0.05)
        {
            for (int i = 0; i < n; i++) {
                double angle = ((double) i / n) * 2 * Math.PI;
                angle += mul;
                double x, y;
                x = Math.sin(angle) * mul;
                y = Math.cos(angle) * mul;
                points.add(new Vector2(x, y));
            }
            n+=2;
        }

        BufferedImage img;
        try {
            img = ImageIO.read(new File("spectro.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Fragment<Vector2> f;

        //f = new MapGenFragment(imageSize.minus(), imageSize);
        f = new MapGenFragment(new Vector2(-1), new Vector2(1));
        //f = new CombineFragment(f, new ConstFragment(2.8), CombineType.MULTIPLY);
        //f = new TransformFragment(f, Transform.MAGNETISM);
        //f = new NearestPointFragment(f, points);
        f = new CombineFragment(f, new NoiseGenFragment(0.02), CombineType.ADD);
        //f = new CombineFragment(f, new ConstFragment(new Vector2(10)), CombineType.MULTIPLY);
        f = new KaliedoFragment(20, f, Vector2.ONE);
        //f = new NearestPointFragment(f, points);
        //f = new MandelFragment(f, 500);
        //f = new CombineFragment(f, new ConstFragment(0.1), CombineType.MULTIPLY);
        //f = new ImageConvertFragment(img, f, ColorProperty.V);
        f = new CombineFragment(f, new ConstFragment(0.6), CombineType.MULTIPLY);

        Fragment<Color[]> r;

        Gradient g;
        //g= new DoubleGradient(Color.BLACK,  Color.blue, Color.red, true);
        g = new SimpleGradient(Color.white, Color.BLUE, true);

        r = new RenderFragment(f, 60, g);
        //r = new ImageCycleFragment(images, f, 0, 0);
        //r = new ImageMapFragment(f, img, 60, new Vector2(300));
        //r = new FadeImageFragment(120, true, f, images);
        r = new SuperSampleFragment(2, r);
        try
        {
            Builder b;
            //b = new Builder(render.build(new Vector2(img.getWidth()/5, img.getHeight()/5)));
            //b = new Builder(r.build(imageSize.iX(), imageSize.iY()));
            b = new Builder(r.build(300, 300));
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

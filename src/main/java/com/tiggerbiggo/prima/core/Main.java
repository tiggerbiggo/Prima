package com.tiggerbiggo.prima.core;

import com.tiggerbiggo.prima.graphics.DoubleGradient;
import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.presets.Transform;
import com.tiggerbiggo.prima.processing.fragment.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
        String outName = "pretty/";
        String extension = ".png";

        int start, end, diff;
        start=1;
        end=72;
        diff=end-start;

        BufferedImage[] images = new BufferedImage[diff];

        for(int i=0; i<diff; i++){
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(outName + (i+start) + extension)); //image00XX.jpg
                images[i] = img;
            } catch (IOException e) {
                System.out.println("oh no an error" + i);
                System.exit(1);
            }
        }

        Vector2 imageSize = new Vector2(images[0].getWidth(), images[0].getHeight());
        imageSize = Vector2.divide(imageSize, new Vector2(100));

        Fragment<Vector2> f;

        //f = new MapGenFragment(imageSize.minus(), imageSize);
        f = new MapGenFragment(Vector2.MINUSTWO, Vector2.TWO);
        f = new TransformFragment(f, Transform.MAGNETISM);
        //f = new CombineFragment(f, new ConstFragment(2.8), CombineType.MULTIPLY);
        //f = new TransformFragment(f, Transform.SINSIN);
        //f = new CombineFragment(f, new NoiseGenFragment(5, 0.2), CombineType.ADD);
        //f = new CombineFragment(f, new ConstFragment(new Vector2(1)), CombineType.MULTIPLY);
        //f = new MandelFragment(f, 500);
        //f = new CombineFragment(f, new ConstFragment(0.1), CombineType.MULTIPLY);

        Fragment<Color[]> r;

        //r = new ImageCycleFragment(images, f, 0, 0);
        Gradient g = new DoubleGradient(Color.BLACK, Color.RED, Color.RED, true);
        r = new RenderFragment(f, 60, g);

        //r = new ImageMapFragment(f, images[5], 60, new Vector2(100));
        try
        {
            Builder b;
            //b = new Builder(render.build(new Vector2(img.getWidth()/5, img.getHeight()/5)));
            //b = new Builder(r.build(Vector2.multiply(imageSize, new Vector2(100))), true);
            b = new Builder(r.build(300, 300), true);
            b.startBuild();
            b.joinAll();

            FileManager.writeGif(b.getImgs(), "morethings9");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

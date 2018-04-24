package com.tiggerbiggo.prima.core;

import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.graphics.HueCycleGradient;
import com.tiggerbiggo.prima.graphics.SafeImage;
import com.tiggerbiggo.prima.presets.Transform;
import com.tiggerbiggo.prima.processing.fragment.Fragment;
import com.tiggerbiggo.prima.processing.fragment.generate.ConstFragment;
import com.tiggerbiggo.prima.processing.fragment.generate.MapGenFragment;
import com.tiggerbiggo.prima.processing.fragment.render.AnimationFragment;
import com.tiggerbiggo.prima.processing.fragment.render.ImageRenderFragment;
import com.tiggerbiggo.prima.processing.fragment.render.MaskFragment;
import com.tiggerbiggo.prima.processing.fragment.render.MaskedCombineFragment;
import com.tiggerbiggo.prima.processing.fragment.render.RenderFragment;
import com.tiggerbiggo.prima.processing.fragment.transform.CombineFragment;
import com.tiggerbiggo.prima.processing.fragment.transform.CombineType;
import com.tiggerbiggo.prima.processing.fragment.transform.ImageConvertFragment;
import com.tiggerbiggo.prima.processing.fragment.transform.TransformFragment;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Main {

  void main(String[] args) throws IOException {
    SafeImage img = new SafeImage(ImageIO.read(new File("cat.png")));
    SafeImage imgmask = new SafeImage(ImageIO.read(new File("maskCat.png")));

    Gradient g;
    //g = new SimpleGradient(Color.BLACK, Color.WHITE, true);
    g = new HueCycleGradient();

    Fragment<Vector2> f;
    Fragment<Vector2[]> anim;
    Fragment<Color[]> r1, r2;

    ArrayList<Vector2> points = new ArrayList<>();
    points.add(new Vector2(0.5));

    f = new MapGenFragment(0, 5);
    f = new TransformFragment(f, Transform.SINSIN);

    anim = new AnimationFragment(f, Vector2::new);
    r1 = new RenderFragment(anim, g);

    f = new MapGenFragment(0, 1);
    anim = new AnimationFragment(f, AnimationFragment.STILL);
    //r2 = new RenderFragment(anim, g);
    r2 = new ImageRenderFragment(anim, img);

    f = new MapGenFragment(0, 1);
    f = new ImageConvertFragment(f, imgmask, ImageConvertFragment.V);
    f = new CombineFragment(f, new ConstFragment(0.9), CombineType.MULTIPLY);

    MaskFragment mask = new MaskFragment(f, Vector2::X);
    r1 = new MaskedCombineFragment(r2, r1, mask);

    Builder b = new Builder(r1, 300, 300, 60);
    b.startBuild();
    b.joinAll();

    String filename;
    int number = 1;
    do {
      filename = "newones" + number;
      System.out.println("File: " + filename);
      number++;
    } while (new File(filename + ".gif").exists());

    FileManager.writeGif(b.getImgs(), filename);
  }
}




/*

f = new MapGenFragment(Vector2.ZERO, Vector2.ONE);
        f = new ImageConvertFragment(img, f, ColorProperty.V, ColorProperty.V);

        anim = new AnimationFragment(f, AnimationFragment.AnimTypes.SIMPLE.getF());

        r1 = new ImageRenderFragment(
                new AnimationFragment(
                        new MapGenFragment(
                                Vector2.ZERO,
                                Vector2.ONE),
                        AnimationFragment.AnimTypes.STILL.getF()),
                img);

        r2 = new RenderFragment(anim, g);

        r1 = new MaskedCombineFragment(r1, r2, new MaskFragment(f, Vector2::X));






import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.graphics.SimpleGradient;
import com.tiggerbiggo.prima.gui.MainFrame;
import com.tiggerbiggo.prima.gui.PrimaPane;
import com.tiggerbiggo.prima.presets.Transform;
import com.tiggerbiggo.prima.processing.fragment.*;
import com.tiggerbiggo.prima.processing.fragment.generate.MapGenFragment;
import com.tiggerbiggo.prima.processing.fragment.render.old.RenderFragment;
import com.tiggerbiggo.prima.processing.fragment.transform.TransformFragment;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class Main
{
    static final int XDIM = 300;
    static final int YDIM = 300;
    public static void main(String[] args) {

        Fragment<Vector2> f, d;

        //f = new MapGenFragment(imageSize.minus(), imageSize);
        MapGenFragment mg = new MapGenFragment(new Vector2(-3), new Vector2(3));
        //d = new MapGenFragment(new Vector2(-3), new Vector2(3));
        //f = new CombineFragment(f, new ConstFragment(2.8), CombineType.MULTIPLY);
        //f = new NearestPointFragment(f, points);
        //f = new CombineFragment(f, new NoiseGenFragment(0.1), CombineType.ADD);
        //f = new KaliedoFragment(4, f, new Vector2(0, 0));
        //d = new KaliedoFragment(4, d, new Vector2(0, 0));
        //f = new CombineFragment(new ConstFragment(.1), new NearestPointFragment(f, points), CombineType.MULTIPLY);
        //f = new CombineFragment(f, new ConstFragment(1), CombineType.ADD);
        //f = new TransformFragment(f, Transform.SINSIN);
        //f = new NearestPointFragment(f, points);
        //f = new MandelFragment(f, 500);
        //f = new CombineFragment(f, new ConstFragment(0.01), CombineType.MULTIPLY);
        TransformFragment tr = new TransformFragment(mg, Transform.SINSIN);
        //d = new TransformFragment(d, Transform.MAGNETISM);

        //f = new ImageConvertFragment(img, f, ColorProperty.V);
        //f = new CombineFragment(f, new ConstFragment(0.6), CombineType.MULTIPLY);

        Fragment<Color[]> r;

        Gradient g;
        //g= new DoubleGradient(Color.BLACK,  Color.blue, Color.red, true);
        g = new SimpleGradient(Color.RED, Color.YELLOW, true);

        r = new RenderFragment(tr, 120, g);
        //r = new ImageCycleFragment(images, f, 0, 0);
        //r = new ImageMapFragment(f, img, 60, new Vector2(300));
        //r = new FadeImageFragment(120, true, f, images);
        //r = new RotateImageFragment(new SafeImage(img),f, 120, new Vector2(1000), new Vector2(500, 1000));
        //r = new SuperSampleFragment(1, r);
        //r = new RawColorFragment(f, 60);
        //r = new OscillateFragment(f, d, 60, g);
        try {
            Builder b;
            //b = new Builder(render.build(new Vector2(img.getWidth()/5, img.getHeight()/5)));
            //b = new Builder(r.build(imageSize.iX(), imageSize.iY()));
            b = new Builder(r, XDIM, YDIM, 60);
            b.startBuild();
            b.joinAll();


//            JFrame fr = new JFrame(){};
//            fr.setSize(XDIM, YDIM*2);
//            fr.setLayout(new GridLayout(2, 0));
//            fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            PrimaPane pp = new PrimaPane(r);
//            pp.setSize(XDIM, YDIM);
//
//            JButton b = new JButton();
//            b.addActionListener(e -> pp.reBuild());
//
//            fr.add(pp.startTimer(1000/30));
//            fr.add(b);
//            fr.setVisible(true);
//            pp.reBuild();
//
//
//
//            MainFrame mf = new MainFrame(tr);
//            mf.setSize(100, 300);
//            mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            mf.setVisible(true);

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
*/
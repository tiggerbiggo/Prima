package com.tiggerbiggo.prima.core;

import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.presets.MapGenerator;
import com.tiggerbiggo.prima.presets.MapTypes;
import com.tiggerbiggo.prima.presets.Transform;
import com.tiggerbiggo.prima.processing.fragment.*;

import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        Gradient g = new Gradient(Color.blue, Color.black, true);
        Gradient h = new Gradient(Color.black, Color.red, true);

        Fragment<float2>[][] frags;
        Fragment<Color[]> render;

        Fragment<float2> initMap = new MapGenFragment(float2.ZERO, float2.ONE);

        TransformFragment T = new TransformFragment(initMap, Transform.SINSIN);

        render = new RenderFragment(T, 60, g);

        int2[] sizes = {
                new int2(300, 300),
                new int2(500, 500),
                new int2(700, 700),
                new int2(1000, 1000)
        };

        String[] filenames = {
                "300",
                "500",
                "700",
                "1000"
        };

        try {
            for(int i=0; i<sizes.length; i++) {
                Builder b = new Builder(render.build(sizes[i]));
                b.startBuild();
                b.joinAll();

                System.out.println("Writing...");
                FileManager.writeGif(b.getImgs(), filenames[i]);
            }
        }
        catch (IllegalMapSizeException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



        //Builder b = new Builder(8, new float2(0, 0),
        //        new float2(10, 10),MapTypes.REGULAR,
        //        TransformTypes.MAGNETISM,g);

        //FileManager.writeGif(b.build(300, 300, 60), "Giffy");
    }
}

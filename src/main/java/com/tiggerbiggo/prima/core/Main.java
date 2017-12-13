package com.tiggerbiggo.prima.core;

import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.presets.MapGenerator;
import com.tiggerbiggo.prima.presets.MapTypes;
import com.tiggerbiggo.prima.presets.TransformTypes;
import com.tiggerbiggo.prima.processing.fragment.*;

import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        Gradient g = new Gradient(Color.blue, Color.black, true);
        Gradient h = new Gradient(Color.black, Color.red, true);

        Fragment<float2>[][] frags;
        Fragment<Color[]>[][] render;

        frags = MapGenerator.getFragMap(300,300, new float2(-2, -2), new float2(2, 2));

        render = new Fragment[frags.length][frags[0].length];

        for(int i=0; i<frags.length; i++)
        {
            if(i % 40 == 0)
                System.out.printf("Building, %f percent.\n", ((float)i/frags.length)*100);
            for(int j=0; j<frags[0].length; j++)
            {
                MandelFragment M = new MandelFragment(frags[i][j],100);
                ValueFragment V = new ValueFragment(new float2(0.1f));

                CombineFragment C = new CombineFragment(M, V, CombineType.MULTIPLY);

                //TransformFragment A = new TransformFragment(B, TransformTypes.TANNY.getPreset());
                //CombineFragment combine = new CombineFragment(A, B, CombineType.MULTIPLY);

                RenderFragment renderFragment = new RenderFragment(C, 60, g);

                if((i+j)%2 == 0)
                    renderFragment.setGradient(h);

                render[i][j] = renderFragment;
            }
        }

        Builder b = new Builder(render);

        b.startBuild();

        b.joinAll();

        System.out.println("Writing...");
        FileManager.writeGif(b.getImgs(),"YayAnotherTest");

        //Builder b = new Builder(8, new float2(0, 0),
        //        new float2(10, 10),MapTypes.REGULAR,
        //        TransformTypes.MAGNETISM,g);

        //FileManager.writeGif(b.build(300, 300, 60), "Giffy");
    }
}

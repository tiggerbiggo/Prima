package Core;

import Graphics.*;
import Processing.*;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main
{
    public static void main(String[] args)
    {
        Main inst = new Main();
        inst.doThing(500, 500, 6);
    }

    public void doThing(int x, int y, int threadNum)
    {
        System.out.println("Started build, making initial map...");
        float2[][] map = new float2[x][y];

        for(int i=0; i<x; i++)
        {
            for(int j=0; j<y; j++)
            {
                map[i][j] = new float2(i/(x/(float)Math.PI), j/(y/(float)Math.PI));
            }
        }

        System.out.println("Transforming map...");
        FormulaTask formulaTask = new FormulaTask(map){
            @Override
            public void doFormula(float2 in) {
                float x, y;
                x = in.getX();
                y = in.getY();

                float z = (0.5f*y)%(1.0f/height); //0.5f
                z -= (0.5f/height);

                if(z<0) x = (float)Math.PI-x;

                y=(float)Math.sin(Math.cos(x*2)*y*5);


                //x = (float)Math.sin(x);

                in.set(Math.abs(x), Math.abs(y));
            }
        };

        ProcessManager manager = new ProcessManager(threadNum, formulaTask);
        manager.runAndWait();

        System.out.println("Flattening map...");

        PreRenderTask preRenderTask = new PreRenderTask(map, RenderMode.ADD);
        manager = new ProcessManager(threadNum, preRenderTask);
        manager.runAndWait();

        float[][] calculatedMap = preRenderTask.getOutMap();

        System.out.println("Rendering...");
        RenderSequenceTask renderTask =
                new RenderSequenceTask(
                        calculatedMap,
                        new Gradient(
                                Color.white,
                                Color.blue,
                                true),
                        50);
        manager = new ProcessManager(threadNum, renderTask);
        manager.runAndWait();

        BufferedImage[] imgSequence = renderTask.getImgSequence();

        System.out.println("Finished Construction, File out in progress...");

        FileManager.writeGif(imgSequence, "ItsAGif");

        System.out.println("Done!");
    }

    public static void printArray(Object[][] array)
    {
        for(Object[] a : array)
        {
            for(Object o : a)
            {
                System.out.println(o);
            }
        }
    }

}

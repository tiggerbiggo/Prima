package Core;

import Graphics.*;
import Presets.MapGenerator;
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
        inst.doThing(501, 501, 6);
    }

    public void doThing(int x, int y, int threadNum)
    {
        System.out.println("Started build, making initial map...");
        float2[][] map = MapGenerator.standard(x, y, new float2(-2, 0), new float2(5, 5));

        System.out.println("Transforming map...");
        FormulaTask formulaTask = new FormulaTask(map){
            @Override
            public void doFormula(float2 in) {
                float x, y;
                x = in.getX();
                y = in.getY();

                float z = ((0.5f*height)*height*y)%(height); //0.5f
                z -= (0.5/height);

                //if(z<0) x = (float)Math.PI-x;

                y=(float)Math.sin(Math.cosh(x)*Math.cos(y));


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
                        60);
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

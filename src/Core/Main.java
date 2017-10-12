package Core;

import Graphics.Gradient;
import Graphics.RenderMode;
import Graphics.RenderTask;
import Processing.ExampleTask;
import Processing.FormulaTask;
import Processing.ProcessManager;
import javafx.scene.paint.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Time;
import java.util.Timer;

public class Main
{
    public static void main(String[] args)
    {
        Main inst = new Main();
        inst.doThing(10, 5, 6);
    }

    public void doThing(int x, int y, int threadNum)
    {
        float2[][] map = new float2[x][y];

        for(int i=0; i<x; i++)
        {
            for(int j=0; j<y; j++)
            {
                map[i][j] = new float2(i/1000.0f, j/1000.0f);
            }
        }

        FormulaTask formulaTask = new FormulaTask(map){
            @Override
            public void doFormula(float2 in) {
                float x, y;
                x = in.getX();
                y = in.getY();

                in.set(1+(float)Math.sin(3.0*x), 1+(float)Math.cos(3.0*y));
            }
        };

        ProcessManager manager = new ProcessManager(threadNum, formulaTask);
        Thread t = new Thread(manager);
        t.run();

        while(t.isAlive())
        {
            try
            {
                wait();
            }
            catch(Exception e) {}
        }

        RenderTask renderTask = new RenderTask(map, RenderMode.ADD, new Gradient(Color.blue, Color.MAGENTA));

        manager =
                new ProcessManager(threadNum, renderTask);
        t = new Thread(manager);

        t.run();

        while(t.isAlive())
        {
            try
            {
                wait();
            }
            catch(Exception e) {}
        }

        try {
            File out = new File("test" + ".png");
            ImageIO.write(renderTask.getImage(), "png", out);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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

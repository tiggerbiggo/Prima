package com.tiggerbiggo.prima.core;

import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.graphics.RenderMode;
import com.tiggerbiggo.prima.presets.MapGenerator;
import com.tiggerbiggo.prima.presets.MapTransformPresets;
import com.tiggerbiggo.prima.presets.MapTypes;
import com.tiggerbiggo.prima.presets.TransformTypes;
import com.tiggerbiggo.prima.processing.ProcessManager;
import com.tiggerbiggo.prima.processing.tasks.map.MapTransformTask;
import com.tiggerbiggo.prima.processing.tasks.render.PreRenderTask;
import com.tiggerbiggo.prima.processing.tasks.render.RenderSequenceTask;
import com.tiggerbiggo.prima.processing.tasks.render.RenderTask;

import java.awt.image.BufferedImage;

public class Builder
{
    int threadNum, x, y;

    boolean printProgress = false;

    public void setPrint(boolean state)
    {
        printProgress = state;
    }

    public Builder(int threadNum, int x, int y)
    {
        this.threadNum = threadNum;
        this.x = x;
        this.y = y;
    }

    public void fullBuildAndWrite(float2 offset, float2 scale, MapTypes mapType, TransformTypes transformType, int numOfFrames, Gradient g, String filename)
    {
        writeGif(
                fullBuildSequence(
                        offset,
                        scale,
                        mapType,
                        transformType,
                        numOfFrames,
                        g),
                filename);
    }

    public BufferedImage[] fullBuildSequence(float2 offset, float2 scale, MapTypes mapType, TransformTypes transformType, int numOfFrames, Gradient g)
    {
        print("Started build, making initial map...");
        float2[][] map = MapGenerator.getMap(x, y, offset, scale, mapType);
        return transformAndRenderSequence(map, transformType, g, numOfFrames);
    }

    public BufferedImage fullBuild(float2 offset, float2 scale, MapTypes mapType, TransformTypes transformType, Gradient g)
    {
        print("Started build, making initial map...");
        float2[][] map = MapGenerator.getMap(x, y, offset, scale, mapType);
        return transformAndRender(map, transformType, g);
    }

    public BufferedImage[] transformAndRenderSequence(float2[][] map, TransformTypes transformType,  Gradient g, int numOfFrames)
    {
        print("Transforming map...");
        doTransform(map, transformType);

        print("Flattening  and rendering map...");
        return renderSequence(map, numOfFrames, g);
    }

    public BufferedImage transformAndRender(float2[][] map, TransformTypes transformType,  Gradient g)
    {
        print("Transforming map...");
        doTransform(map, transformType);

        print("Flattening  and rendering map...");
        return render(map, g);
    }

    public void doTransform(float2[][] map, TransformTypes type)
    {
        MapTransformTask mapTransformTask = MapTransformPresets.getPreset(map, type);

        ProcessManager manager = new ProcessManager(threadNum, mapTransformTask);
        manager.runAndWait();
    }

    public BufferedImage[] renderSequence(float2[][] map, int frames, Gradient g)
    {
        float[][] calculatedMap = preRender(map);

        System.out.println("Rendering...");
        RenderSequenceTask renderTask =
                new RenderSequenceTask(calculatedMap,g,frames);

        ProcessManager manager = new ProcessManager(threadNum, renderTask);
        manager.runAndWait();

        return renderTask.getImgSequence();
    }

    public BufferedImage render(float2[][] map, Gradient g)
    {
        float[][] calculatedMap = preRender(map);

        RenderTask renderTask = new RenderTask(calculatedMap, g);

        ProcessManager manager = new ProcessManager(threadNum, renderTask);
        manager.runAndWait();

        return renderTask.getImage();
    }

    public float[][] preRender(float2[][] map)
    {
        PreRenderTask preRenderTask = new PreRenderTask(map, RenderMode.ADD);
        ProcessManager manager = new ProcessManager(threadNum, preRenderTask);
        manager.runAndWait();

        return preRenderTask.getOutMap();
    }

    public void writeGif(BufferedImage[] imgSequence, String filename)
    {
        if(imgSequence == null)return;
        FileManager.writeGif(imgSequence, filename);
    }

    void print(String s)
    {
        if(printProgress) System.out.println(s);
    }

    public void setSize(int x, int y)
    {
        if(x <=0 || y<=0) return;

        this.x = x;
        this.y = y;
    }
}

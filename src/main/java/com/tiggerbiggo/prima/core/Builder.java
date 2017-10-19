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
import javafx.util.Callback;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Builder
{
    private int threadNum;
    private float2 offset, scale;
    private MapTypes mapType;
    private TransformTypes transformType;
    private Gradient g;

    public Builder(int threadNum, float2 offset, float2 scale, MapTypes mapType, TransformTypes transformType, Gradient g) {
        this.threadNum = threadNum;
        this.offset = offset;
        this.scale = scale;
        this.mapType = mapType;
        this.transformType = transformType;
        this.g = g;
    }
    public Builder(){
        this(8,
                new float2(0,0),
                new float2(1,1),
                MapTypes.REGULAR,
                TransformTypes.SINSIN,
                new Gradient(Color.black, Color.white, true));
    }

    public BufferedImage build(int x, int y)
    {
        //Create map
        float2[][] map = MapGenerator.getMap(x, y, offset, scale, mapType);

        //Transform map
        MapTransformTask mapTransformTask = MapTransformPresets.getPreset(map, transformType);
        ProcessManager manager = new ProcessManager(threadNum, mapTransformTask);
        manager.runAndWait();

        //Flatten map
        PreRenderTask preRenderTask = new PreRenderTask(map, RenderMode.ADD);
        manager = new ProcessManager(threadNum, preRenderTask);
        manager.runAndWait();
        float[][] calculatedMap = preRenderTask.getOutMap();

        //Render
        RenderTask renderTask = new RenderTask(calculatedMap, g);
        manager = new ProcessManager(threadNum, renderTask);
        manager.runAndWait();

        return renderTask.getImage();
    }

    public BufferedImage[] build(int x, int y, int frameNum)
    {
        //Create map
        float2[][] map = MapGenerator.getMap(x, y, offset, scale, mapType);

        //Transform map
        MapTransformTask mapTransformTask = MapTransformPresets.getPreset(map, transformType);
        ProcessManager manager = new ProcessManager(threadNum, mapTransformTask);
        manager.runAndWait();

        //Flatten map
        PreRenderTask preRenderTask = new PreRenderTask(map, RenderMode.ADD);
        manager = new ProcessManager(threadNum, preRenderTask);
        manager.runAndWait();
        float[][] calculatedMap = preRenderTask.getOutMap();

        //Render
        RenderSequenceTask renderSequenceTask = new RenderSequenceTask(calculatedMap, g, frameNum);
        manager = new ProcessManager(threadNum, renderSequenceTask);
        manager.runAndWait();

        return renderSequenceTask.getImgSequence();
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        if(threadNum <=0) return;
        this.threadNum = threadNum;
    }

    public float2 getOffset() {
        return offset;
    }

    public void setOffset(float2 offset) {
        if(offset == null) return;
        this.offset = offset;
    }

    public float2 getScale() {
        return scale;
    }

    public void setScale(float2 scale) {
        if(scale == null) return;
        this.scale = scale;
    }

    public Gradient getGradient() {
        return g;
    }

    public void setGradient(Gradient g) {
        if(g == null) return;
        this.g = g;
    }

    public TransformTypes getTransformType() {
        return transformType;
    }

    public void setTransformType(TransformTypes transformType) {
        this.transformType = transformType;
    }
}

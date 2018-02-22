package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.graphics.SimpleGradient;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.awt.*;
import java.io.Serializable;

public class RenderFragment implements Fragment<Color[]>, Serializable
{
    Fragment<Vector2[]> in;
    Gradient g;

    public RenderFragment(Fragment<Vector2[]> in, Gradient g) {
        if(in == null || g == null) throw new IllegalArgumentException("Number of frames cannot be <= 0");

        this.in = in;
        this.g = g;
    }

    public RenderFragment(Fragment<Vector2[]> in)
    {
        this(in, new SimpleGradient());
    }

    /** The main calculation method. All processing for a given pixel should be done in this method.
     *
     * @param x The X position of the pixel being rendered
     * @param y The Y position of the pixel being rendered
     * @param w The width of the image
     * @param h The height of the image
     * @param num The number of frames in the animation
     * @return The output of the fragment
     */
    @Override
    public Color[] get(int x, int y, int w, int h, int num) {
        Vector2[] base = in.get(x, y, w, h, num);
        if(base == null) return null;

        Color[] colorArray = new Color[num];

        for(int i=0; i<num; i++) {
            colorArray[i] = g.evaluate(base[i]);
        }

        return colorArray;
    }
}

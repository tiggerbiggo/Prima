package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.graphics.SimpleGradient;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.awt.*;
import java.io.Serializable;

public class RenderFragment implements Fragment<Color[]>, Serializable
{
    Fragment<Vector2[]> in;
    Fragment<Vector2[]>[][] map;
    Gradient g;

    public RenderFragment(Fragment<Vector2[]> in, Gradient g)
    {
        if(in == null || g == null) throw new IllegalArgumentException("Number of frames cannot be null");

        this.in = in;
        this.g = g;
    }

    public RenderFragment(Fragment<Vector2[]> in)
    {
        this(in, new SimpleGradient());
    }

    @Override
    public Color[] get() {
        Vector2[] base = in.get();
        if(base == null) return null;
        int num = base.length;

        Color[] cA = new Color[num];

        for(int i=0; i<num; i++) {
            cA[i] = g.evaluate(base[i]);
        }

        return cA;
    }

    @Override
    public Fragment<Color[]>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        map = in.build(xDim, yDim);
        return new RenderFragment[xDim][yDim];
    }

    @Override
    public Fragment<Color[]> getNew(int i, int j) {
        return new RenderFragment(map[i][j], g);
    }
}

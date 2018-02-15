package com.tiggerbiggo.prima.processing.fragment.render.old;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.graphics.SimpleGradient;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.awt.*;
import java.io.Serializable;

public class RenderFragment implements Fragment<Color[]>, Serializable
{
    Fragment<Vector2> in;
    Fragment<Vector2>[][] map;
    int num;
    Gradient g;

    public RenderFragment(Fragment<Vector2> in, int num, Gradient g)
    {
        if(num <=0) throw new IllegalArgumentException("Number of frames cannot be null");

        this.in = in;
        this.num = num;
        this.g = g;
    }

    public RenderFragment(Fragment<Vector2> in)
    {
        this(in, 1, new SimpleGradient());
    }

    @Override
    public Color[] get() {
        Vector2 base = in.get();
        if(base == null) base = Vector2.ZERO;
        Color[] cA = new Color[num];

        double increment = 1.0f/num;

        for(int i=0; i<num; i++)
        {
            cA[i] = g.evaluate(Vector2.add(base , new Vector2(i*increment)));
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
        return new RenderFragment(map[i][j], num, g);
    }
}

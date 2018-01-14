package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.graphics.SimpleGradient;

import java.awt.*;

public class RenderFragment implements Fragment<Color[]>
{
    Fragment<Vector2> in;
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


    public void setGradient(Gradient g)
    {
        if(g != null)
            this.g = g;
    }

    @Override
    public Color[] get() {
        Vector2 base = in.get();
        Color[] cA = new Color[num];

        double increment = 1.0f/num;

        for(int i=0; i<num; i++)
        {
            cA[i] = g.evaluate(Vector2.add(base , new Vector2(i*increment)));
        }

        return cA;
    }

    @Override
    public Fragment<Color[]>[][] build(int xDim, int yDim) throws IllegalMapSizeException {
        Fragment<Vector2>[][] map;
        try {
            map = in.build(xDim, yDim);
        }
        catch(IllegalMapSizeException ex)
        {
            throw ex;
        }

        if(Fragment.checkArrayDims(map, xDim, yDim))
        {
            RenderFragment[][] thisArray = new RenderFragment[xDim][yDim];
            for(int i=0; i<xDim; i++)
            {
                for(int j=0; j<yDim; j++)
                {
                    thisArray[i][j] = new RenderFragment(map[i][j], num, g);
                }
            }
            return thisArray;
        }
        else throw new IllegalMapSizeException();
    }
}

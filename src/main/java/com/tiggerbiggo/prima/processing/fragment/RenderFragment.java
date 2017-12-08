package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.float2;
import com.tiggerbiggo.prima.graphics.Gradient;

import java.awt.*;

public class RenderFragment implements Fragment<Color[]>
{
    Fragment<float2> in;
    int num;
    Gradient g;

    public RenderFragment(Fragment<float2> in, int num, Gradient g)
    {
        if(num <=0) throw new IllegalArgumentException("Number of frames cannot be null");

        this.in = in;
        this.num = num;
        this.g = g;
    }

    public RenderFragment(Fragment<float2> in)
    {
        this(in, 1, new Gradient());
    }


    public void setGradient(Gradient g)
    {
        if(g != null)
            this.g = g;
    }

    @Override
    public Color[] get() {
        float base = in.get().toFloat();
        Color[] cA = new Color[num];

        float increment = 1.0f/num;

        for(int i=0; i<num; i++)
        {
            cA[i] = g.evaluate(base + (i*increment));
        }

        return cA;
    }
}

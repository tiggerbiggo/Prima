package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.float2;

public class MandelFragment implements Fragment<float2>
{

    private Fragment<float2> frag;
    private int iter;

    public MandelFragment(Fragment<float2> c, int iter)
    {
        this.frag = c;
        this.iter = iter;
    }

    @Override
    public float2 get() {

        float2 z = float2.ZERO;
        float2 c = frag.get();

        if(c == null) return null;

        for(int i=0; i<iter; i++)
        {
            float a, b;

            a=z.getX();
            b=z.getY();

            z = new float2(
                    (a*a)-(b*b)+c.getY(),
                    (2*a*b) + c.getX()
            );

            if(z.magnitude() >2)
            {
                return new float2(i, i);
            }
        }
        return float2.ZERO;
    }
}

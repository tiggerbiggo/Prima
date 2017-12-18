package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.float2;
import com.tiggerbiggo.prima.core.int2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

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

            a=z.X();
            b=z.Y();

            z = new float2(
                    (a*a)-(b*b)+c.Y(),
                    (2*a*b) + c.X()
            );

            if(z.magnitude() >2)
            {
                return new float2(i, i);
            }
        }
        return float2.ZERO;
    }

    @Override
    public Fragment<float2>[][] build(int2 dims) throws IllegalMapSizeException {
        Fragment<float2>[][] map;
        try {
            map = frag.build(dims);
        }
        catch(IllegalMapSizeException ex)
        {
            throw ex;
        }

        if(Fragment.checkArrayDims(map, dims))
        {
            Fragment<float2>[][] meFragment = new Fragment[dims.X()][dims.Y()];
            for(int i=0; i<dims.X(); i++)
            {
                for(int j=0; j<dims.Y(); j++)
                {
                    meFragment[i][j] = new MandelFragment(map[i][j],iter);
                }
            }
            return meFragment;
        }
        throw new IllegalMapSizeException();
    }
}

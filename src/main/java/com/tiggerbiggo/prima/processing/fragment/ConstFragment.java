package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.float2;
import com.tiggerbiggo.prima.core.int2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

public class ConstFragment implements Fragment<float2>
{
    private float2 val;

    public ConstFragment(float2 val)
    {
        this.val = val;
    }

    @Override
    public float2 get() {
        return val;
    }

    @Override
    public Fragment<float2>[][] build(int2 dims) throws IllegalMapSizeException
    {
        if(dims == null)
        {
            throw new IllegalMapSizeException();
        }

        ConstFragment[][] thisFragment = new ConstFragment[dims.X()][dims.Y()];

        for(int i = 0; i<dims.X(); i++)
        {
            for(int j = 0; j<dims.Y(); j++)
            {
                thisFragment[i][j] = new ConstFragment(val);
            }
        }
        return thisFragment;
    }
}

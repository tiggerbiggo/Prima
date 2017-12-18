package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.float2;
import com.tiggerbiggo.prima.core.int2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.presets.Transform;

import java.util.ArrayList;

public class TransformFragment implements Fragment<float2>
{
    private Fragment<float2> in;
    private Transform t;

    private ArrayList<TransformFragment> myList;

    public TransformFragment(Fragment<float2> in, Transform t)
    {
        this.in = in;
        this.t = t;
    }

    @Override
    public float2 get() {
        return transform(in.get());
    }

    @Override
    public Fragment<float2>[][] build(int2 dims) throws IllegalMapSizeException {
        Fragment<float2>[][] map;
        try
        {
            map = in.build(dims);
        }
        catch (IllegalMapSizeException ex)
        {
            throw ex;
        }

        if(Fragment.checkArrayDims(map, dims))
        {
            TransformFragment[][] thisArray = new TransformFragment[dims.X()][dims.Y()];
            for(int i=0; i<dims.X(); i++)
            {
                for(int j=0; j<dims.Y(); j++)
                {
                    thisArray[i][j] = new TransformFragment(map[i][j], t);
                }
            }
            return thisArray;
        }

        throw new IllegalMapSizeException();
    }

    private float2 transform(float2 in)
    {
        return t.doFormula(in);
    }
}

package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.presets.Transform;

import java.util.ArrayList;

public class TransformFragment implements Fragment<Vector2>
{
    private Fragment<Vector2> in;
    private Transform t;

    private ArrayList<TransformFragment> myList;

    public TransformFragment(Fragment<Vector2> in, Transform t)
    {
        this.in = in;
        this.t = t;
    }

    @Override
    public Vector2 get() {
        return transform(in.get());
    }

    @Override
    public Fragment<Vector2>[][] build(Vector2 dims) throws IllegalMapSizeException {
        Fragment<Vector2>[][] map;
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
            TransformFragment[][] thisArray = new TransformFragment[dims.iX()][dims.iY()];
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

    private Vector2 transform(Vector2 in)
    {
        return t.doFormula(in);
    }
}

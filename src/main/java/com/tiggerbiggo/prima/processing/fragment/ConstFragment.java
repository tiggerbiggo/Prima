package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

public class ConstFragment implements Fragment<Vector2>
{
    private Vector2 val;

    public ConstFragment(Vector2 val)
    {
        this.val = val;
    }

    @Override
    public Vector2 get() {
        return val;
    }

    @Override
    public Fragment<Vector2>[][] build(Vector2 dims) throws IllegalMapSizeException
    {
        if(dims == null)
        {
            throw new IllegalMapSizeException();
        }

        ConstFragment[][] thisFragment = new ConstFragment[dims.iX()][dims.iY()];

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

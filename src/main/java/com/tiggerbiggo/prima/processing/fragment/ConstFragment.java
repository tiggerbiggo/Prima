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
    public ConstFragment(double xy)
    {
        this.val = new Vector2(xy);
    }

    @Override
    public Vector2 get() {
        return val;
    }

    @Override
    public Fragment<Vector2>[][] build(int xDim, int yDim) throws IllegalMapSizeException
    {
        if(xDim <=0 || yDim <=0)
        {
            throw new IllegalMapSizeException();
        }

        ConstFragment[][] thisFragment = new ConstFragment[xDim][yDim];

        for(int i = 0; i<xDim; i++)
        {
            for(int j = 0; j<yDim; j++)
            {
                thisFragment[i][j] = new ConstFragment(val);
            }
        }
        return thisFragment;
    }
}

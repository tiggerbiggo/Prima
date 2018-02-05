package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

import java.io.Serializable;

public class ConstFragment implements Fragment<Vector2>, Serializable
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
    public Fragment<Vector2>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        return new ConstFragment[xDim][yDim];
    }

    @Override
    public Fragment<Vector2> getNew(int i, int j) {
        return new ConstFragment(val);
    }
}

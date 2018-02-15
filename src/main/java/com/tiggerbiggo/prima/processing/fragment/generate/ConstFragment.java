package com.tiggerbiggo.prima.processing.fragment.generate;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.gui.ControlPane;
import com.tiggerbiggo.prima.gui.components.VectorPane;
import com.tiggerbiggo.prima.processing.fragment.Controllable;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import javax.swing.*;
import java.io.Serializable;

public class ConstFragment implements Fragment<Vector2>, Serializable, Controllable
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

    @Override
    public ControlPane getControls(ControlPane p) {
        VectorPane v = new VectorPane(val, -10000, 10000, 0.1);
        p.add(v);
        return p;
    }
}

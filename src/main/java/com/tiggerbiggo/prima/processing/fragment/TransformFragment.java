package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.float2;
import com.tiggerbiggo.prima.processing.Transform;

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

    private float2 transform(float2 in)
    {
        return t.doFormula(in);
    }
}

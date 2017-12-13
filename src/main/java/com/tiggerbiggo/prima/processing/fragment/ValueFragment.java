package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.float2;

public class ValueFragment implements Fragment<float2>
{
    private float2 val;

    public ValueFragment(float2 val)
    {
        this.val = val;
    }

    @Override
    public float2 get() {
        return val;
    }
}

package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.float2;

public class ConstFragment implements Fragment<float2>
{
    private float2 val;

    public ConstFragment(float2 val)
    {
        this.val = val;
    }

    public void set(float2 val)
    {
        this.val = val;
    }

    @Override
    public float2 get() {
        return val;
    }


}

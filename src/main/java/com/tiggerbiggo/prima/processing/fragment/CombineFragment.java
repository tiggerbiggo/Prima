package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.float2;

public class CombineFragment implements Fragment<float2>
{
    Fragment<float2> A;
    Fragment<float2> B;
    CombineType type;

    public CombineFragment(Fragment<float2> A, Fragment<float2> B, CombineType type)
    {
        this.A = A;
        this.B = B;
        this.type = type;
    }

    @Override
    public float2 get() {
        return type.combine(A, B);
    }
}

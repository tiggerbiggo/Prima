package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.float2;

public enum CombineType{
    ADD{
        @Override
        public float2 combine(Fragment<float2> A, Fragment<float2> B) {
            return float2.add(
                    A.get(),
                    B.get()
            );
        }
    },
    MULTIPLY{
        @Override
        public float2 combine(Fragment<float2> A, Fragment<float2> B) {
            return float2.multiply(
                    A.get(),
                    B.get()
            );
        }
    },
    DIFF{
        @Override
        public float2 combine(Fragment<float2> A, Fragment<float2> B) {
            return float2.subtract(
                    A.get(),
                    B.get()
            );
        }
    },
    ABSDIFF{
        @Override
        public float2 combine(Fragment<float2> A, Fragment<float2> B) {
            return float2.abs(
                    float2.subtract(
                            A.get(),
                            B.get()
                    )
            );
        }
    };

    public float2 combine(Fragment<float2> A, Fragment<float2> B){return A.get();}
}

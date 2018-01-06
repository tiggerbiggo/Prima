package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;

public enum CombineType{
    ADD{
        @Override
        public Vector2 combine(Fragment<Vector2> A, Fragment<Vector2> B) {
            return Vector2.add(
                    A.get(),
                    B.get()
            );
        }
    },
    MULTIPLY{
        @Override
        public Vector2 combine(Fragment<Vector2> A, Fragment<Vector2> B) {
            return Vector2.multiply(
                    A.get(),
                    B.get()
            );
        }
    },
    DIFF{
        @Override
        public Vector2 combine(Fragment<Vector2> A, Fragment<Vector2> B) {
            return Vector2.subtract(
                    A.get(),
                    B.get()
            );
        }
    },
    ABSDIFF{
        @Override
        public Vector2 combine(Fragment<Vector2> A, Fragment<Vector2> B) {
            return Vector2.abs(
                    Vector2.subtract(
                            A.get(),
                            B.get()
                    )
            );
        }
    };

    public Vector2 combine(Fragment<Vector2> A, Fragment<Vector2> B){return A.get();}
}

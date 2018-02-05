package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;

import java.io.Serializable;

/**
 * Contains combination methods to be used with CombineFragment
 * @see CombineFragment
 */
public enum CombineType implements Serializable {
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
    SUBTRACT{
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

    public abstract Vector2 combine(Fragment<Vector2> A, Fragment<Vector2> B);
}

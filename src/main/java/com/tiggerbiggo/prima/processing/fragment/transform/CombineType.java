package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.io.Serializable;

/**
 * Contains combination methods to be used with CombineFragment
 * @see CombineFragment
 */
public enum CombineType implements Serializable {
    ADD{
        /**
         * @param
         *
         * @return Vector2
         */
        @Override
        public Vector2 combine(Fragment<Vector2> A, Fragment<Vector2> B, int x, int y, int w, int h, int num) {
            return Vector2.add(
                    A.get(x, y, w, h, num),
                    B.get(x, y, w, h, num)
            );
        }
    },
    MULTIPLY{
        /**
         * @param
         *
         * @return Vector2
         */
        @Override
        public Vector2 combine(Fragment<Vector2> A, Fragment<Vector2> B, int x, int y, int w, int h, int num) {
            return Vector2.multiply(
                    A.get(x, y, w, h, num),
                    B.get(x, y, w, h, num)
            );
        }
    },
    SUBTRACT{
        /**
         * @param
         *
         * @return Vector2
         */
        @Override
        public Vector2 combine(Fragment<Vector2> A, Fragment<Vector2> B, int x, int y, int w, int h, int num) {
            return Vector2.subtract(
                    A.get(x, y, w, h, num),
                    B.get(x, y, w, h, num)
            );
        }
    },
    ABSDIFF{
        /**
         * @param
         *
         * @return Vector2
         */
        @Override
        public Vector2 combine(Fragment<Vector2> A, Fragment<Vector2> B, int x, int y, int w, int h, int num) {
            return Vector2.abs(
                    Vector2.subtract(
                            A.get(x, y, w, h, num),
                            B.get(x, y, w, h, num)
                    )
            );
        }
    };

    /**
     * @param
     *
     * @return Vector2
     */
    public abstract Vector2 combine(Fragment<Vector2> A, Fragment<Vector2> B, int x, int y, int w, int h, int num);
}

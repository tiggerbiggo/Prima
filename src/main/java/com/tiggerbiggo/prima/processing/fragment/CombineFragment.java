package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.core.int2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

public class CombineFragment implements Fragment<Vector2> {
    Fragment<Vector2> A;
    Fragment<Vector2> B;
    CombineType type;

    public CombineFragment(Fragment<Vector2> A, Fragment<Vector2> B, CombineType type) {
        this.A = A;
        this.B = B;
        this.type = type;
    }

    @Override
    public Vector2 get() {
        return type.combine(A, B);
    }

    @Override
    public Fragment<Vector2>[][] build(Vector2 dims) throws IllegalMapSizeException {
        Fragment<Vector2>[][] mapA;
        Fragment<Vector2>[][] mapB;

        try {
            mapA = A.build(dims);
            mapB = B.build(dims);
        }
        catch (IllegalMapSizeException e)
        {
            throw e;
        }

        boolean isValid =
            mapA != null &&
            mapB != null &&
            mapA.length == mapB.length &&
            mapA[0].length == mapB[0].length;

        if (isValid)
        {
            int width = mapA.length;
            int height = mapA[0].length;
            CombineFragment[][] thisArray = new CombineFragment[width][height];
            for (int i = 0; i < width; i++)
            {
                for (int j = 0; j < height; j++)
                {
                    thisArray[i][j] = new CombineFragment(mapA[i][j], mapB[i][j], type);
                }
            }
            return thisArray;
        }
        else
        {
            throw new IllegalMapSizeException();
        }
    }
}

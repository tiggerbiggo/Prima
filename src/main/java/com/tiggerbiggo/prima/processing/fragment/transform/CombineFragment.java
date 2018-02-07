package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.io.Serializable;

/**
 * Combines 2 fragments to produce an output
 */
public class CombineFragment implements Fragment<Vector2>, Serializable {
    Fragment<Vector2> A, B;
    Fragment<Vector2>[][] mapA, mapB;
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
    public Fragment<Vector2>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException{
        mapA = A.build(xDim, yDim);
        mapB = B.build(xDim, yDim);
        return new CombineFragment[xDim][yDim];
    }

    @Override
    public Fragment<Vector2> getNew(int i, int j) {
        return new CombineFragment(mapA[i][j], mapB[i][j], type);
    }
}

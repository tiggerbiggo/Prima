package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.io.Serializable;

/**
 * Combines 2 fragments to produce an output
 */
public class CombineFragment implements Fragment<Vector2>, Serializable {
    Fragment<Vector2> A, B;
    CombineType type;

    public CombineFragment(Fragment<Vector2> A, Fragment<Vector2> B, CombineType type) {
        this.A = A;
        this.B = B;
        this.type = type;
    }

    @Override
    public Vector2 get(int x, int y, int w, int h, int num) { return type.combine(A, B, x, y, w, h, num); }
}

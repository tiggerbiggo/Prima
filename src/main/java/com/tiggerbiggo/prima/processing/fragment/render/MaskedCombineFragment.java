package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.calculation.ColorTools;
import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.Fragment;
import jdk.management.resource.internal.TotalResourceContext;

import java.awt.*;

public class MaskedCombineFragment implements Fragment<Color[]> {

    Fragment<Color[]> A, B;
    Fragment<Double> maskFragment;

    public MaskedCombineFragment(Fragment<Color[]> a, Fragment<Color[]> b, Fragment<Double> maskFragment) {
        A = a;
        B = b;
        this.maskFragment = maskFragment;
    }

    @Override
    public Color[] get(int x, int y, int w, int h, int num) {
        Color[] cA, cB;

        cA = A.get(x, y, w, h, num);
        cB = B.get(x, y, w, h, num);
        double mask = maskFragment.get(x, y, w, h, num);

        if(cA == null || cB == null) return null;

        Color[] toReturn = new Color[num];

        for(int i=0; i<num; i++){
            toReturn[i] = ColorTools.colorLerp(cA[i], cB[i], mask);
        }

        return toReturn;
    }
}

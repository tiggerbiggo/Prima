package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.calculation.ColorTools;
import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.Fragment;
import jdk.management.resource.internal.TotalResourceContext;

import java.awt.*;

/**
 */
public class MaskedCombineFragment implements Fragment<Color[]> {

    Fragment<Color[]> A, B;
    Fragment<Double> maskFragment;

    /**
     * @return
     *
     * @return
     * @return
     * @return
     */
    public MaskedCombineFragment(Fragment<Color[]> a, Fragment<Color[]> b, Fragment<Double> maskFragment) {
        A = a;
        B = b;
        this.maskFragment = maskFragment;
    }

    /** The main calculation method. All processing for a given pixel should be done in this method.
     *
     * @param x The X position of the pixel being rendered
     * @param y The Y position of the pixel being rendered
     * @param w The width of the image
     * @param h The height of the image
     * @param num The number of frames in the animation
     * @return The output of the fragment
     */
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

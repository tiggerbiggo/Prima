package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.graphics.SafeImage;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.awt.*;

public class ImageFadeFragment implements Fragment<Color[]> {
    SafeImage[] imgs;
    Fragment<Vector2[]> position, fade;


    @Override
    public Color[] get() {
        return new Color[0];
    }

    @Override
    public Fragment<Color[]>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        return new Fragment[0][];
    }

    @Override
    public Fragment<Color[]> getNew(int i, int j) {
        return null;
    }
}

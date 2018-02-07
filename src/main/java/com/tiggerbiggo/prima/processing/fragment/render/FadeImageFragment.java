package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.calculation.ColorTools;
import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.graphics.SafeImage;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.awt.*;
import java.io.Serializable;

public class FadeImageFragment implements Fragment<Color[]>, Serializable{

    SafeImage[] imgs;
    Fragment<Vector2>[][] inArr;
    Fragment<Vector2> in;
    int num, x, y;
    boolean loop;

    private FadeImageFragment(int num, int x, int y, boolean loop, Fragment<Vector2> in, SafeImage ... imgs)
    {
        if(imgs == null || in == null) throw new IllegalArgumentException("Image array or Fragment cannot be null");
        if(imgs.length <=1) throw new IllegalArgumentException("Must have >= 2 images");

        this.imgs = imgs;
        this.in = in;
        this.num = num;
        this.loop = loop;
        this.x = x;
        this.y = y;
    }
    public FadeImageFragment(int num, boolean loop, Fragment<Vector2> in, SafeImage... imgs) {
        this(num, 0, 0, loop, in, imgs);
    }

    @Override
    public Color[] get() {
        double n = in.get().magnitude();
        double len = imgs.length-1;

        Color[] toReturn = new Color[num];

        for(int i=0; i<num; i++) {
            double current = n + ((i*len)/num);
            current = Gradient.normalise(current, len, loop);
            Color A, B;

            if(current == len) current =- 0.1; //protect from array out of bounds.

            A = new Color(imgs[(int)current].getRGB(x, y));
            B = new Color(imgs[(int)current+1].getRGB(x, y));

            toReturn[i] = ColorTools.colorLerp(A, B, current - ((int)current));
        }
        return toReturn;
    }

    @Override
    public Fragment<Color[]>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        inArr = in.build(xDim, yDim);
        return new FadeImageFragment[xDim][yDim];
    }

    @Override
    public Fragment<Color[]> getNew(int i, int j) {
        return new FadeImageFragment(num, i, j, loop, inArr[i][j], imgs);
    }
}
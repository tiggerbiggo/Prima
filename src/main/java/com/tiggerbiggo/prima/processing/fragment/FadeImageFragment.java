package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.calculation.ColorTools;
import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.graphics.Gradient;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FadeImageFragment implements Fragment<Color[]>{

    BufferedImage[] imgs;
    Fragment<Vector2> in;
    int num, x, y;
    boolean loop;

    private FadeImageFragment(int num, int x, int y, boolean loop, Fragment<Vector2> in, BufferedImage ... imgs)
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
    public FadeImageFragment(int num, boolean loop, Fragment<Vector2> in, BufferedImage ... imgs) {
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

            if(current == len) current =- 0.1;

            A = new Color(imgs[(int)current].getRGB(x, y));
            B = new Color(imgs[(int)current+1].getRGB(x, y));

            toReturn[i] = ColorTools.colorLerp(A, B, current - ((int)current));
        }
        return toReturn;
    }

    @Override
    public Fragment<Color[]>[][] build(int xDim, int yDim) throws IllegalMapSizeException {
        Fragment<Vector2>[][] inArr;
        try {
            inArr = in.build(xDim, yDim);
        }
        catch (IllegalMapSizeException e){
            throw e;
        }
        FadeImageFragment[][] toReturn = new FadeImageFragment[inArr.length][inArr[0].length];
        for(int i=0; i<inArr.length; i++)
        {
            for(int j=0; j<inArr[0].length; j++)
            {
                toReturn[i][j] = new FadeImageFragment(num, i, j, loop, inArr[i][j], imgs);
            }
        }
        return toReturn;
    }
}

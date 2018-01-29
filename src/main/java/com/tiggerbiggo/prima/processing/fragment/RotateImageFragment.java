package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.graphics.SafeImage;

import java.awt.*;

public class RotateImageFragment implements Fragment<Color[]> {

    Fragment<Vector2>[][] map;

    Vector2 multiplier, rotatePoint;
    SafeImage img;
    Fragment<Vector2> in;
    int num;

    public RotateImageFragment(SafeImage img, Fragment<Vector2> in, int num, Vector2 multiplier, Vector2 rotatePoint) {
        this.img = img;
        this.in = in;
        this.multiplier = multiplier;
        this.rotatePoint = rotatePoint;
        this.num = num;
    }

    @Override
    public Color[] get() {
        Vector2 start = Vector2.multiply(in.get(), multiplier);
        Color[] toReturn = new Color[num];

        for(int i=0; i<num; i++) {
            double angle = (double)i/num;
            angle *= Math.PI * 2;

            Vector2 tmp = Vector2.rotateAround(start, rotatePoint, angle);
            toReturn[i] = img.getColor(tmp);
        }

        return toReturn;
    }

    @Override
    public Fragment<Color[]>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        map = in.build(xDim, yDim);
        return new RotateImageFragment[xDim][yDim];
    }

    @Override
    public Fragment<Color[]> getNew(int i, int j) {
        return new RotateImageFragment(img, map[i][j], num, multiplier, rotatePoint);
    }
}

package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.graphics.SafeImage;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.awt.Color;

public class ImageRenderFragment implements Fragment<Color[]> {
    Fragment<Vector2[]> in;
    Fragment<Vector2[]>[][] map;
    SafeImage img;

    public ImageRenderFragment(Fragment<Vector2[]> in, SafeImage img) {
        this.in = in;
        this.img = img;
    }

    @Override
    public Color[] get() {
        Vector2[] points = in.get();
        if(points == null) return null;
        Color[] toReturn = new Color[points.length];
        for(int i = 0; i<points.length; i++){
            Vector2 point = Vector2.multiply(points[i], new Vector2(img.getWidth(), img.getHeight()));
            toReturn[i] = img.getColor(point);
        }
        return toReturn;
    }

    @Override
    public Fragment<Color[]>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        map = in.build(xDim, yDim);
        return new ImageRenderFragment[xDim][yDim];
    }

    @Override
    public Fragment<Color[]> getNew(int i, int j) {
        return new ImageRenderFragment(map[i][j], img);
    }
}

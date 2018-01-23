package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageCycleFragment implements Fragment<Color[]>{

    BufferedImage[] images;
    Fragment<Vector2>[][] map;
    Fragment<Vector2> in;
    int x, y;

    public ImageCycleFragment(BufferedImage[] images, Fragment<Vector2> in, int x, int y) {
        if(images == null) throw new IllegalArgumentException("Images cannot be null");
        this.images = images;
        this.in = in;
        this.x = x;
        this.y = y;
    }

    @Override
    public Color[] get() {
        int num = images.length;

        Vector2 inVector = in.get();
        int startFrame = (int)Math.abs(inVector.magnitude());

        int modX, modY;
        modX = x%images[0].getWidth();
        modY = y%images[0].getHeight();

        Color[] toReturn = new Color[num];

        for(int i=0; i<num; i++) {
            int rgb = images[(i+startFrame)%num].getRGB(modX, modY);
            toReturn[i] = new Color(rgb);
        }
        return toReturn;
    }

    @Override
    public Fragment<Color[]>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        map = in.build(xDim, yDim);
        return new ImageCycleFragment[xDim][yDim];
    }

    @Override
    public Fragment<Color[]> getNew(int i, int j) {
        return new ImageCycleFragment(images, map[i][j], i, j);
    }
}

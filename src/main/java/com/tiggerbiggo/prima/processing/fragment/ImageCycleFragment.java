package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageCycleFragment implements Fragment<Color[]>{

    BufferedImage[] images;
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

        for(int i=0; i<num; i++)
        {
            int rgb = images[(i+startFrame)%num].getRGB(modX, modY);
            toReturn[i] = new Color(rgb);
        }

        return toReturn;
    }

    @Override
    public Fragment<Color[]>[][] build(int xDim, int yDim) throws IllegalMapSizeException
    {
        Fragment<Vector2>[][] inArr;
        try {
            inArr = in.build(xDim, yDim);
        }
        catch (IllegalMapSizeException e){
            throw e;
        }
        ImageCycleFragment[][] toReturn = new ImageCycleFragment[inArr.length][inArr[0].length];
        for(int i=0; i<inArr.length; i++)
        {
            for(int j=0; j<inArr[0].length; j++)
            {
                toReturn[i][j] = new ImageCycleFragment(images, inArr[i][j], i, j);
            }
        }
        return toReturn;
    }
}

package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageMapFragment implements Fragment<Color[]>{

    Fragment<Vector2> in;
    BufferedImage img;
    int num;
    Vector2 multiplier;

    public ImageMapFragment(Fragment<Vector2> in, BufferedImage img, int num, Vector2 multiplier) {
        if(num <=0) throw new IllegalArgumentException("Number of frames must be >=1");
        if(multiplier == null) multiplier = Vector2.ONE;
        this.in = in;
        this.img = img;
        this.num = num;
        this.multiplier = multiplier;
    }

    @Override
    public Color[] get() {
        Vector2 start = in.get();
        if(start == null) return null;

        start = Vector2.abs(start);
        start = Vector2.multiply(start, multiplier);

        Color[] toReturn = new Color[num];

        for(int i=0; i<num; i++) {
            int rgb, iX, iY;
            double x, y;
            Vector2 a;

            x=start.X();
            y=start.Y();

            a=new Vector2(((double)i)/num);
            a = Vector2.multiply(a, multiplier);


            x+=x+a.X();
            y+=y+a.Y();

            iX=Math.abs((int)x%img.getWidth());
            iY=Math.abs((int)y%img.getHeight());

            rgb = img.getRGB(iX, iY);
            toReturn[i] = new Color(rgb);
        }

        return toReturn;
    }

    @Override
    public Fragment<Color[]>[][] build(int xDim, int yDim) throws IllegalMapSizeException {
        Fragment<Vector2>[][] inArr;
        try {
            inArr = in.build(xDim, yDim);
        }
        catch (IllegalMapSizeException e)
        {
            throw e;
        }

        ImageMapFragment[][] toReturn = new ImageMapFragment[inArr.length][inArr[0].length];
        for(int i=0; i<inArr.length; i++)
        {
            for(int j=0; j<inArr[0].length; j++)
            {
                toReturn[i][j] = new ImageMapFragment(inArr[i][j], img, num, multiplier);
            }
        }
        return toReturn;
    }
}

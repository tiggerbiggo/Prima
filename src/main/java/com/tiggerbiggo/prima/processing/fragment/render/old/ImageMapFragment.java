package com.tiggerbiggo.prima.processing.fragment.render.old;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.graphics.SafeImage;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.awt.*;

public class ImageMapFragment implements Fragment<Color[]> {

    Fragment<Vector2> in;
    Fragment<Vector2>[][] map;
    SafeImage img;
    int num;
    Vector2 multiplier;

    public ImageMapFragment(Fragment<Vector2> in, SafeImage img, int num, Vector2 multiplier) {
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
    public Fragment<Color[]>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        map = in.build(xDim, yDim);
        return new ImageMapFragment[xDim][yDim];
    }

    @Override
    public Fragment<Color[]> getNew(int i, int j) {
        return new ImageMapFragment(map[i][j], img, num, multiplier);
    }
}

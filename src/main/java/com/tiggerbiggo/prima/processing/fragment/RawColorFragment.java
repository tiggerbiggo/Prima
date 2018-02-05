package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

import java.awt.*;

public class RawColorFragment implements Fragment<Color[]> {

    Fragment<Vector2> in;
    Fragment<Vector2>[][] map;
    int num;

    public RawColorFragment(Fragment<Vector2> in, int num) {
        if(in == null || num <=0) throw new IllegalArgumentException();
        this.in = in;
        this.num = num;
    }

    @Override
    public Color[] get() {
        Vector2 v = in.get();

        Color[] toReturn = new Color[num];
        for(int i=0; i<num; i++){
            double percent = (i*Math.PI)/num;
            toReturn[i] = convert(Vector2.add(v, new Vector2(percent)));
        }

        return toReturn;
    }

    @Override
    public Fragment<Color[]>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        map = in.build(xDim, yDim);
        return new RawColorFragment[xDim][yDim];
    }

    @Override
    public Fragment<Color[]> getNew(int i, int j) {
        return new RawColorFragment(map[i][j], num);
    }

    public Color convert(Vector2 v){
        v = new Vector2(Math.sin(v.X()), Math.sin(v.Y()));
        v = Vector2.add(v, new Vector2(1));
        v = Vector2.divide(v, new Vector2(2));
        v = Vector2.multiply(v, new Vector2(255));

        double r, g, b;
        r = v.X();
        g = b = v.Y();
        //b = (v.X()+v.Y())/2.0;

        return new Color((int)r, (int)g, (int)b);
    }
}

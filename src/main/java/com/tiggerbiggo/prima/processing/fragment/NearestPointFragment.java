package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

import java.util.ArrayList;

public class NearestPointFragment implements Fragment<Vector2>
{
    ArrayList<Vector2> points;
    Fragment<Vector2> in;

    public NearestPointFragment(Fragment<Vector2> in, ArrayList<Vector2> points) {
        this.points = points;
        this.in = in;
    }

    @Override
    public Vector2 get() {
        Vector2 inVector = in.get();
        Vector2 closest = null;

        for(Vector2 v : points) {
            Vector2 dist = Vector2.subtract(inVector, v);
            if(closest == null || closest.magnitude() > dist.magnitude())
                closest = dist;
        }
        return closest;
    }

    @Override
    public Fragment<Vector2>[][] build(int xDim, int yDim) throws IllegalMapSizeException {
        Fragment<Vector2>[][] map;
        try {
            map = in.build(xDim, yDim);
        }
        catch (IllegalMapSizeException ex) {
            throw ex;
        }

        if(Fragment.checkArrayDims(map, xDim, yDim)) {
            NearestPointFragment[][] thisArray = new NearestPointFragment[xDim][yDim];
            for(int i=0; i<xDim; i++) {
                for(int j=0; j<yDim; j++) {
                    thisArray[i][j] = new NearestPointFragment(map[i][j], points);
                }
            }
            return thisArray;
        }

        throw new IllegalMapSizeException();
    }
}

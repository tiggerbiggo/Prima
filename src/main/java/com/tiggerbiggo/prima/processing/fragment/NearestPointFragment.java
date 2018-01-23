package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

import java.util.ArrayList;

public class NearestPointFragment implements Fragment<Vector2>
{
    ArrayList<Vector2> points;
    Fragment<Vector2>[][] map;
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
    public Fragment<Vector2>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        map = in.build(xDim, yDim);
        return new NearestPointFragment[xDim][yDim];
    }

    @Override
    public Fragment<Vector2> getNew(int i, int j) {
        return new NearestPointFragment(map[i][j], points);
    }
}

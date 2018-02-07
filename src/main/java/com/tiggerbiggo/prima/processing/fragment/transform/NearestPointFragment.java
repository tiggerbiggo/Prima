package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.io.Serializable;
import java.util.ArrayList;

public class NearestPointFragment implements Fragment<Vector2>, Serializable
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
        int closestI = 0;

        for (int i = 0; i < points.size(); i++) {
            Vector2 v = points.get(i);
            Vector2 dist = Vector2.subtract(inVector, v);
            if (closest == null || closest.magnitude() > dist.magnitude()) {
                closest = dist;
                closestI = i;
            }
        }
        return new Vector2(closest.magnitude());//points.get(closestI);
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

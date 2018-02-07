package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

import java.io.Serializable;

public class KaliedoFragment implements Fragment<Vector2>, Serializable{

    int rotationNum;
    Fragment<Vector2> in;
    Fragment<Vector2>[][] map;
    Vector2 rotationPoint;

    public KaliedoFragment(int rotationNum, Fragment<Vector2> in, Vector2 rotationPoint) {
        this.rotationNum = rotationNum;
        this.in = in;
        this.rotationPoint = rotationPoint;
    }

    @Override
    public Vector2 get() {
        Vector2 point;
        double baseAngle, angle;
        int multiplier;

        point = in.get();
        point = Vector2.add(point, rotationPoint);

        baseAngle = Math.PI * 2;
        baseAngle /= rotationNum;

        angle = Vector2.angleBetween(new Vector2(5, 0),point);
        multiplier = (int)(angle / baseAngle);

        angle = baseAngle * -multiplier;
        if(multiplier % 2 == 0){
            //angle = baseAngle - angle;
        }


        point = Vector2.rotateAround(point, rotationPoint, angle);
        point = Vector2.subtract(point, rotationPoint);

        return point;
    }

    @Override
    public Fragment<Vector2>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        map = in.build(xDim, yDim);
        return new KaliedoFragment[xDim][yDim];
    }

    @Override
    public Fragment<Vector2> getNew(int i, int j) {
        return new KaliedoFragment(rotationNum, map[i][j], rotationPoint);
    }
}

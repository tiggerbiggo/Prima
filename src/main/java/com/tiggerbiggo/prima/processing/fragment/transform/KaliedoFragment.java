package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.io.Serializable;

public class KaliedoFragment implements Fragment<Vector2>, Serializable{

    int rotationNum;
    Fragment<Vector2> in;
    Vector2 rotationPoint;

    public KaliedoFragment(int rotationNum, Fragment<Vector2> in, Vector2 rotationPoint) {
        this.rotationNum = rotationNum;
        this.in = in;
        this.rotationPoint = rotationPoint;
    }

    @Override
    public Vector2 get(int x, int y, int w, int h, int num) {
        Vector2 point;
        double baseAngle, angle;
        int multiplier;

        point = in.get(x, y, w, h, num);
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
}

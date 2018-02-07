package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.graphics.Gradient;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.awt.*;

public class OscillateFragment implements Fragment<Color[]> {

    Fragment<Vector2> from, to;
    Fragment<Vector2>[][] fromMap, toMap;
    int num;
    Gradient g;

    public OscillateFragment(Fragment<Vector2> from, Fragment<Vector2> to, int num, Gradient g) {
        this.from = from;
        this.to = to;
        this.num = num;
        this.g = g;
    }

    @Override
    public Color[] get() {
        Color[] toReturn = new Color[num];

        Vector2 a, b;
        a = from.get();
        b = to.get();

        for(int i=0; i<num; i++){
            double percent = (double)i / num;
            percent *= Math.PI * 2;
            percent = Math.sin(percent);
            percent += 1;
            percent /= 2;
            Vector2 point = Vector2.lerpVector(a, b, percent);

            toReturn[i] = g.evaluate(point);
        }

        return toReturn;
    }

    //ok, this should take 2 fragments, and "wobble" between them both

    @Override
    public Fragment<Color[]>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        fromMap = from.build(xDim, yDim);
        toMap = to.build(xDim, yDim);
        return new OscillateFragment[xDim][yDim];
    }

    @Override
    public Fragment<Color[]> getNew(int i, int j) {
        return new OscillateFragment(fromMap[i][j], toMap[i][j], num, g);
    }
}

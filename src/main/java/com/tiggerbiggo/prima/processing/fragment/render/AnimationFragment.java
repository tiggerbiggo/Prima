package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.calculation.Calculation;
import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.util.function.Function;

public class AnimationFragment implements Fragment<Vector2[]> {

    Fragment<Vector2> in;
    Fragment<Vector2>[][] map;
    Function<Double, Vector2> func;
    int num;

    public AnimationFragment(Fragment<Vector2> in, int num, Function<Double, Vector2> func) {
        this.in = in;
        this.func = func;
        this.num = num;
    }

    @Override
    public Vector2[] get() {
        Vector2 start = in.get();

        Vector2[] toReturn = new Vector2[num];
        for(int i=0; i<num; i++){
            double percent = (double)i/num;
            toReturn[i] = Vector2.add(func.apply(percent), start);
        }

        return toReturn;
    }

    @Override
    public Fragment<Vector2[]>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        map = in.build(xDim, yDim);
        return new AnimationFragment[xDim][yDim];
    }

    @Override
    public Fragment<Vector2[]> getNew(int i, int j) {
        return new AnimationFragment(map[i][j], num, func);
    }

    enum AnimTypes{
        SIMPLE((i) -> {
            return new Vector2(i);
        }),
        SINSIN((i) -> {
            return new Vector2(Math.sin(i*Math.PI));
        }),
        REVERSE((i)->{
            return new Vector2(Calculation.modLoop(i, true));
        });
        Function<Double, Vector2> f;
        AnimTypes(Function<Double, Vector2> f){this.f = f;}
    }
}

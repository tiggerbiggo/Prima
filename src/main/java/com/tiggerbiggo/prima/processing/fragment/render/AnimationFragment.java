package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.calculation.Calculation;
import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.util.function.Function;

public class AnimationFragment implements Fragment<Vector2[]> {

    Fragment<Vector2> in;
    Function<Double, Vector2> func;

    public AnimationFragment(Fragment<Vector2> in, Function<Double, Vector2> func) {
        this.in = in;
        this.func = func;
    }

    @Override
    public Vector2[] get(int x, int y, int w, int h, int num) {
        Vector2 start = in.get(x, y, w, h, num);

        Vector2[] toReturn = new Vector2[num];
        for(int i=0; i<num; i++){
            double percent = (double)i/num;
            toReturn[i] = Vector2.add(func.apply(percent), start);
        }

        return toReturn;
    }

    public enum AnimTypes{
        SIMPLE(Vector2::new),
        SINSIN((i) -> {
            return new Vector2(Math.sin(i*Math.PI*2)*(1/Math.PI));
        }),
        REVERSE((i)->{
            return new Vector2(Calculation.modLoop(i, true));
        });
        Function<Double, Vector2> f;
        AnimTypes(Function<Double, Vector2> f){this.f = f;}

        public Function<Double, Vector2> getF() {
            return f;
        }
    }
}

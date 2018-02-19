package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.calculation.Calculation;
import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.util.function.Function;

public class MaskFragment implements Fragment<Double> {

    private Function<Vector2, Double> func;
    private Fragment<Vector2> in;

    public MaskFragment(Fragment<Vector2> in, Function<Vector2, Double> func) {
        this.func = func;
        this.in = in;
    }

    @Override
    public Double get(int x, int y, int w, int h, int num) {
        Vector2 v = in.get(x, y, w, h, num);
        if(v == null) return 0d;
        return Calculation.mod(func.apply(v), 1);
    }

    public enum MaskConversionType{
        X(Vector2::X),
        Y(Vector2::Y),
        XPLUSY((v) -> v.X() + v.Y()),
        MAGNITUDE(Vector2::magnitude);

        Function<Vector2, Double> func;

        MaskConversionType(Function<Vector2, Double> func) {this.func = func;}

        public Function<Vector2, Double> getFunction() {
            return func;
        }
    }
}

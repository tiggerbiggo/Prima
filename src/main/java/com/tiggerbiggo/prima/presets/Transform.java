package com.tiggerbiggo.prima.presets;

import com.tiggerbiggo.prima.core.Vector2;

import java.util.function.BiFunction;

/**
 * Contains transform types to use with TransformFragment
 * @see com.tiggerbiggo.prima.processing.fragment.TransformFragment
 */
public enum Transform {
    SINSIN((x, y) -> {
        y = Math.sin(y) / Math.PI;
        x = Math.sin(x) / Math.PI;
        return new Vector2(x, y);
    }),
    SINX((x, y) -> {
        x = Math.sin(x) / Math.PI;
        return new Vector2(x, y);
    }),
    SINY((x, y) -> {
        y = Math.sin(y) / Math.PI;
        return new Vector2(x, y);
    }),
    MAGNETISM((x, y) -> {
        y = Math.sin(Math.cosh(x) * y);
        return new Vector2(x, y);
    }),
    TANNY((x, y) -> {
        y = Math.sin(Math.tanh(x) * Math.tan(y));
        return new Vector2(x, y);
    }),
    CHOPPY((x, y) -> {
        if ((Math.abs(x) + Math.abs(y)) % 1 <= 0.5) {
            x *= -1; y *= -1;
        }
        if ((Math.abs(x) - Math.abs(y)) % 1 <= 0.5) {
            x *= -1; y *= -1;
        }
        return new Vector2(x, y);
    }),
    NEW((x, y) -> {
        if ((x - y) < 0.1) {
            return null;
        }
        return new Vector2(x, y);
    }),
    HARMONIC((x, y) -> {
        for(int i=0; i<100; i++)
        {
            x = Math.sin(x);
            y = Math.sin(y);
        }
        return new Vector2(x, y);
    }),
    NEGATE((x, y) -> {
        x *= -1;
        y *= -1;

        return new Vector2(x, y);
    }),
    TESTSUM((x, y) -> {

        return new Vector2(x, y);
    });

    private BiFunction<Double, Double, Vector2> function;

    Transform(BiFunction<Double, Double, Vector2> function) {
        this.function = function;
    }

    public Vector2 doFormula(Vector2 in) {
        return function.apply(in.X(), in.Y());
    }
}

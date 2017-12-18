package com.tiggerbiggo.prima.presets;

import com.tiggerbiggo.prima.core.float2;

import java.util.function.BiFunction;

public enum Transform {
    SINSIN((x, y) -> {
        y = (float) Math.sin(y) / (float) Math.PI;
        x = (float) Math.sin(x) / (float) Math.PI;
        return new float2(x, y);
    }),
    MAGNETISM((x, y) -> {
        y = (float) Math.sin(Math.cosh(x) * y);
        return new float2(x, y);
    }),
    TANNY((x, y) -> {
        y = (float) Math.sin(Math.tanh(x) * Math.tan(y));
        return new float2(x, y);
    }),
    CHOPPY((x, y) -> {
        if ((Math.abs(x) + Math.abs(y)) % 1 <= 0.5) {
            x *= -1; y *= -1;
        }
        if ((Math.abs(x) - Math.abs(y)) % 1 <= 0.5) {
            x *= -1; y *= -1;
        }
        return new float2(x, y);
    }),
    NEW((x, y) -> {
        if ((x - y) < 0.1) {
            return null;
        }
        return new float2(x, y);
    });

    private BiFunction<Float, Float, float2> function;

    Transform(BiFunction<Float, Float, float2> function) {
        this.function = function;
    }

    public float2 doFormula(float2 in) {
        return function.apply(in.X(), in.Y());
    }
}

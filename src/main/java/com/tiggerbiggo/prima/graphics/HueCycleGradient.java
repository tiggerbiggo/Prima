package com.tiggerbiggo.prima.graphics;

import com.tiggerbiggo.prima.core.Vector2;

import java.awt.*;

public class HueCycleGradient extends Gradient {
    @Override
    public Color evaluate(Vector2 a) {
        Color c = Color.getHSBColor((a.fX()+a.fY())/2, 1, 1);
        return c;
    }
}

package com.tiggerbiggo.prima.graphics;

import com.tiggerbiggo.prima.calculation.Calculation;
import com.tiggerbiggo.prima.calculation.ColorTools;

import java.awt.*;

/**
 * Stores colors and calculates gradients.
 * @author tiggerbiggo
 */
public class Gradient
{
    Color c1, c2;
    boolean loop;

    public Gradient(Color c1, Color c2, boolean loop) throws IllegalArgumentException{
        this.c1 = c1;
        this.c2 = c2;
        this.loop = loop;
    }
    public Gradient(){
        this(Color.black, Color.white, false);
    }

    public Color evaluate(float a){
        if(a<0) a=Math.abs(a);
        if(a > 1) a = Calculation.modLoop(a, 1);

        if(loop) {
            if(a<0.5f){
                a *= 2;
            }
            else{
                a -= 0.5f;
                a = 1-(a*2);
            }
        }
        return ColorTools.colorLerp(c1, c2, a);
    }
}

package com.tiggerbiggo.prima.graphics;

import com.tiggerbiggo.prima.calculation.Calculation;
import com.tiggerbiggo.prima.calculation.ColorTools;
import com.tiggerbiggo.prima.core.Vector2;

import java.awt.*;
import java.io.Serializable;

/**Stores colors and calculates gradients
 */
public abstract class Gradient implements Serializable
{
    /**
     * Abstract method for evaluating gradients
     * @param a The vector to evaluate
     * @return The evaluated color
     */
    public abstract Color evaluate(Vector2 a);


    public static double normalise(double in, double modVal, boolean loop)
    {
        in = Math.abs(in);
        in = Calculation.mod(in, modVal);

        if (loop){
            if (in < (modVal/2)) in *= 2;
            else {
                in -= modVal/2;
                in = modVal - (in * 2);
            }
        }
        return in;
    }

    /**
     * Normalises a number to between 0 and 1, optionally looped
     * @param in The number to bound
     * @param loop Whether to loop the number so it goes from 0 to 1, then back to 0
     * @return The normalised number
     */
    public static double normalise(double in, boolean loop) {
        return normalise(in, 1, loop);
    }
}

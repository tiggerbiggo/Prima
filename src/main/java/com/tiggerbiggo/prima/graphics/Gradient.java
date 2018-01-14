package com.tiggerbiggo.prima.graphics;

import com.tiggerbiggo.prima.calculation.Calculation;
import com.tiggerbiggo.prima.calculation.ColorTools;
import com.tiggerbiggo.prima.core.Vector2;

import java.awt.*;

/**Stores colors and calculates gradients
 */
public abstract class Gradient
{
    /**
     * Abstract method for evaluating gradients
     * @param a The vector to evaluate
     * @return The evaluated color
     */
    public abstract Color evaluate(Vector2 a);

    /**
     * Normalises a number to between 0 and 1, optionally looped
     * @param in The number to bound
     * @param loop Whether to loop the number so it goes from 0 to 1, then back to 0
     * @return The normalised number
     */
    public double normalise(double in, boolean loop) {
        in = Math.abs(in);
        in = Calculation.mod(in, 1);

        if (loop){
            if (in < 0.5) in *= 2;
            else {
                in -= 0.5f;
                in = 1 - (in * 2);
            }
        }
        return in;
    }
}

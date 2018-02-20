package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.io.Serializable;

/**
 */
public class MandelFragment implements Fragment<Vector2>, Serializable {
    private Fragment<Vector2> frag;
    private int iterations;
    private double multiplier;

    /**
     * @return
     *
     * @return
     * @return
     * @return
     */
    public MandelFragment(Fragment<Vector2> c, int iterations, double multiplier) {
        this.frag = c;
        this.iterations = iterations;
        this.multiplier = multiplier;
    }

    /** The main calculation method. All processing for a given pixel should be done in this method.
     *
     * @param x The X position of the pixel being rendered
     * @param y The Y position of the pixel being rendered
     * @param w The width of the image
     * @param h The height of the image
     * @param num The number of frames in the animation
     * @return The output of the fragment
     */
    @Override
    public Vector2 get(int x, int y, int w, int h, int num) {
        //get the point we are rendering
        Vector2 c = frag.get(x, y, w, h, num);

        if(c == null) return null;
        //Start z at (0,0i), treating the y component as coefficient of i
        Vector2 z = Vector2.ZERO;

        for(int i = 0; i< iterations; i++) {
            //temporary hold values for this iteration
            double a, b;

            //set a and b from the current z value
            a=z.X();
            b=z.Y();

            //perform calculation for this iteration
            z = new Vector2(
                    (a*a)-(b*b)+c.Y(),
                    (2*a*b) + c.X()
            );

            //check for out of bounds
            if(z.magnitude() >2) {
                //double x = z.X();
                //double y = z.Y();
                return new Vector2(i*multiplier);
            }
        }
        //edge case to return zero if never escapes
        return Vector2.ZERO;
    }
}

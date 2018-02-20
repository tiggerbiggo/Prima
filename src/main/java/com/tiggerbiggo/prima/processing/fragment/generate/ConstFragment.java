package com.tiggerbiggo.prima.processing.fragment.generate;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.gui.ControlPane;
import com.tiggerbiggo.prima.gui.components.VectorPane;
import com.tiggerbiggo.prima.processing.fragment.Controllable;
import com.tiggerbiggo.prima.processing.fragment.Fragment;
import java.io.Serializable;

/**A fragment that returns a constant value no matter the X, Y, W, H, or num parameters.
 *
 * <p>Usually used for scaling or otherwise transforming an entire image by the same amount.</p>
 */
public class ConstFragment implements Fragment<Vector2>, Serializable, Controllable
{
    private Vector2 val;

    /**Creates a new ConstFragment
     * @param val The vector2 value for this Fragment
     */
    public ConstFragment(Vector2 val)
    {
        this.val = val;
    }

    /**Shorthand for defining this fragment without explicitly declaring a Vector2 object
     * @param xy The X and Y component of the vector
     */
    public ConstFragment(double xy)
    {
        this.val = new Vector2(xy);
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
        return val;
    }

    @Override
    public ControlPane getControls(ControlPane p) {
        VectorPane v = new VectorPane(val, -10000, 10000, 0.1);
        p.add(v);
        return p;
    }
}

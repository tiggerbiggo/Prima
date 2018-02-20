package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 */
public class NearestPointFragment implements Fragment<Vector2>, Serializable
{
    ArrayList<Vector2> points;
    Fragment<Vector2> in;

    /**
     * @return
     *
     * @return
     * @return
     */
    public NearestPointFragment(Fragment<Vector2> in, ArrayList<Vector2> points) {
        this.points = points;
        this.in = in;
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
        Vector2 inVector = in.get(x, y, w, h, num);
        Vector2 closest = null;

        for (int i = 0; i < points.size(); i++) {
            Vector2 v = points.get(i);
            Vector2 dist = Vector2.subtract(inVector, v);
            if (closest == null || closest.magnitude() > dist.magnitude()) {
                closest = dist;
            }
        }
        return new Vector2(closest.magnitude());//points.get(closestI);
    }
}

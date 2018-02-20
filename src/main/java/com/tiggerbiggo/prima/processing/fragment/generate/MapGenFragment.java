package com.tiggerbiggo.prima.processing.fragment.generate;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.gui.ControlPane;
import com.tiggerbiggo.prima.gui.components.VectorPane;
import com.tiggerbiggo.prima.processing.fragment.Controllable;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.io.Serializable;

/**
 */
public class MapGenFragment implements Fragment<Vector2>, Serializable, Controllable {
    double aX, aY, dx, dy;

    /**
     * @return
     *
     * @return
     * @return
     */
    public MapGenFragment(Vector2 A, Vector2 B) {
        aX = A.X();
        aY = A.Y();

        dx = B.X() - aX;
        dy = B.Y() - aY;
    }

    /**
     * @return
     *
     * @return
     * @return
     * @return
     * @return
     */
    public MapGenFragment(double aX, double aY, double bX, double bY){
        this(new Vector2(aX, aY), new Vector2(bX, bY));
    }

    /**
     * @return
     *
     * @return
     * @return
     */
    public MapGenFragment(double A, double B){
        this(A, A, B, B);
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
    public Vector2 get(int x, int y, int w, int h, int num){
        Vector2 v = new Vector2(
                aX+(x*(dx/w)),
                aY+(y*(dy/h))
        );
        return v;
    }

//    @Override
//    public Fragment<Vector2>[][] build(int xDim, int yDim) throws IllegalMapSizeException {
//        Fragment<Vector2>[][] map = MapGenerator.getFragMap(xDim, yDim, A, B);
//        return map;
//    }
//
//    @Override
//    public Fragment<Vector2>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {return null;}
//
//    @Override
//    public Fragment<Vector2> getNew(int i, int j) {return null;}

    /**
     * @param
     *
     * @return ControlPane
     */
    @Override
    public ControlPane getControls(ControlPane p) {
        if(p == null) return p;

        VectorPane aPane, bPane;

//        aPane = new VectorPane(A, -1000, 1000, 0.1);
//        bPane = new VectorPane(B, -1000, 1000, 0.1);
//
//        p.add(aPane);
//        p.add(bPane);

        return p;
    }
}

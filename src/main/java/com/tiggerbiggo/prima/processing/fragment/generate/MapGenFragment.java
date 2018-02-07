package com.tiggerbiggo.prima.processing.fragment.generate;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.gui.ControlPane;
import com.tiggerbiggo.prima.gui.components.VectorPane;
import com.tiggerbiggo.prima.presets.MapGenerator;
import com.tiggerbiggo.prima.processing.fragment.Controllable;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import javax.swing.*;
import java.io.Serializable;

public class MapGenFragment implements Fragment<Vector2>, Serializable, Controllable {
    Vector2 A, B;

    public MapGenFragment(Vector2 A, Vector2 B) {
        this.A = A;
        this.B = B;
    }

    @Override
    public Vector2 get(){return Vector2.ZERO;} //here to satisfy constructor.

    @Override
    public Fragment<Vector2>[][] build(int xDim, int yDim) throws IllegalMapSizeException {
        Fragment<Vector2>[][] map = MapGenerator.getFragMap(xDim, yDim, A, B);
        return map;
    }

    @Override
    public Fragment<Vector2>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {return null;}

    @Override
    public Fragment<Vector2> getNew(int i, int j) {return null;}

    @Override
    public ControlPane getControls(ControlPane p) {
        if(p == null) return p;

        VectorPane aPane, bPane;

        aPane = new VectorPane(A, -1000, 1000, 0.1);
        bPane = new VectorPane(B, -1000, 1000, 0.1);

        p.add(aPane);
        p.add(bPane);

        return p;
    }
}

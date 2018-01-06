package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.presets.MapGenerator;

public class MapGenFragment implements Fragment<Vector2> {
    Vector2 A, B;

    //Top Left, Bottom Right
    //TL        B
    public MapGenFragment(Vector2 A, Vector2 B)
    {
        this.A = A;
        this.B = B;
    }

    @Override
    public Vector2 get() {
        return Vector2.ZERO; //here to satisfy constructor. DON'T USE THIS!
    }

    @Override
    public Fragment<Vector2>[][] build(Vector2 dims) throws IllegalMapSizeException {
        Fragment<Vector2>[][] map = MapGenerator.getFragMap(dims.iX(), dims.iY(), A, B);
        return map;
    }
}

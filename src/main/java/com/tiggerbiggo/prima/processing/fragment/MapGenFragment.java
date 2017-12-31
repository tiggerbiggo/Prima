package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.core.int2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.presets.MapGenerator;

public class MapGenFragment implements Fragment<Vector2> {
    Vector2 TL, BR;

    //Top Left, Bottom Right
    //TL        BR
    public MapGenFragment(Vector2 TL, Vector2 BR)
    {
        this.TL = TL;
        this.BR = BR;
    }

    @Override
    public Vector2 get() {
        return Vector2.ZERO; //here to satisfy constructor. DON'T USE THIS!
    }

    @Override
    public Fragment<Vector2>[][] build(Vector2 dims) throws IllegalMapSizeException {
        Fragment<Vector2>[][] map = MapGenerator.getFragMap(dims.iX(), dims.iY(), TL, BR);
        return map;
    }
}

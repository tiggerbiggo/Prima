package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.float2;
import com.tiggerbiggo.prima.core.int2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.presets.MapGenerator;

public class MapGenFragment implements Fragment<float2> {
    float2 TL, BR;

    //Top Left, Bottom Right
    //TL        BR
    public MapGenFragment(float2 TL, float2 BR)
    {
        this.TL = TL;
        this.BR = BR;
    }

    @Override
    public float2 get() {
        return float2.ZERO; //here to satisfy constructor. DON'T USE THIS!
    }

    @Override
    public Fragment<float2>[][] build(int2 dims) throws IllegalMapSizeException {
        Fragment<float2>[][] map = MapGenerator.getFragMap(dims.X(), dims.Y(), TL, BR);
        return map;
    }
}

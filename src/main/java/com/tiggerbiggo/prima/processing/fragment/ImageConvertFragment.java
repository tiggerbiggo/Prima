package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.float2;
import com.tiggerbiggo.prima.core.int2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.processing.ColorProperty;

import java.awt.image.BufferedImage;

public class ImageConvertFragment implements Fragment<float2>{

    private BufferedImage img;
    private Fragment<float2> pos;
    private ColorProperty convX, convY;

    public ImageConvertFragment(BufferedImage img, Fragment<float2> pos, ColorProperty convX, ColorProperty convY) {
        this.img = img;
        this.pos = pos;
        this.convX = convX;
        this.convY = convY;
    }
    /*
    (0,0)   +---------------+   (1,0)
            |               |
            |               |
            |     image     |
            |               |
            |               |
    (0,1)   +---------------+   (1,1)
    */

    @Override
    public float2 get() {
        float2 point = pos.get();

        point = point.mod(1);
        point = float2.multiply(
                point,
                new float2(
                        img.getWidth(),
                        img.getHeight()
                ));


        return point;
    }

    @Override
    public Fragment<float2>[][] build(int2 dims) throws IllegalMapSizeException {
        return new Fragment[0][];
    }
}

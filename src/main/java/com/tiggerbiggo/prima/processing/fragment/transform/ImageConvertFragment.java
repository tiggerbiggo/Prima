package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.graphics.SafeImage;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.awt.*;
import java.io.Serializable;

public class ImageConvertFragment implements Fragment<Vector2>, Serializable{

    private SafeImage img;
    private Fragment<Vector2> pos;
    private ColorProperty convX, convY;

    public ImageConvertFragment(SafeImage img, Fragment<Vector2> pos, ColorProperty convX, ColorProperty convY) {
        this.img = img;
        this.pos = pos;
        this.convX = convX;
        this.convY = convY;
    }

    public ImageConvertFragment(SafeImage img, Fragment<Vector2> pos, ColorProperty conv)
    {
        this(img, pos, conv, conv);
    }

    @Override
    public Vector2 get(int x, int y, int w, int h, int num) {
        Vector2 point = pos.get(x, y, w, h, num);

        point = Vector2.multiply(
                point,
                new Vector2(
                        img.getWidth(),
                        img.getHeight()
                ));
        Color c = new Color(img.getRGB(point.iX(), point.iY()));
        return new Vector2(convX.convert(c), convY.convert(c));
    }

    /*
    (0,1)   +---------------+   (1,1)
            |               |
            |               |
            |     image     |
            |               |
            |               |
    (0,0)   +---------------+   (1,0)
    */
}

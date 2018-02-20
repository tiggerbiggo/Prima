package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.graphics.SafeImage;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.awt.*;
import java.io.Serializable;

/**
 */
public class ImageConvertFragment implements Fragment<Vector2>, Serializable{

    private SafeImage img;
    private Fragment<Vector2> pos;
    private ColorProperty convX, convY;

    /**
     * @return
     *
     * @return
     * @return
     * @return
     * @return
     */
    public ImageConvertFragment(Fragment<Vector2> pos, SafeImage img, ColorProperty convX, ColorProperty convY) {
        this.img = img;
        this.pos = pos;
        this.convX = convX;
        this.convY = convY;
    }

    /**
     * @return
     *
     * @return
     * @return
     * @return
     */
    public ImageConvertFragment(Fragment<Vector2> pos, SafeImage img, ColorProperty conv)
    {
        this(pos, img, conv, conv);
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

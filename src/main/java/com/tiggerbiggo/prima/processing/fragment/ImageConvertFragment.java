package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.core.int2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.processing.ColorProperty;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageConvertFragment implements Fragment<Vector2>{

    private BufferedImage img;
    private Fragment<Vector2> pos;
    private ColorProperty convX, convY;

    public ImageConvertFragment(BufferedImage img, Fragment<Vector2> pos, ColorProperty convX, ColorProperty convY) {
        this.img = img;
        this.pos = pos;
        this.convX = convX;
        this.convY = convY;
    }

    public ImageConvertFragment(BufferedImage img, Fragment<Vector2> pos, ColorProperty conv)
    {
        this(img, pos, conv, conv);
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
    public Vector2 get() {
        Vector2 point = pos.get();

        point = point.mod(1);
        point = Vector2.abs(point);
        point = Vector2.multiply(
                point,
                new Vector2(
                        img.getWidth(),
                        img.getHeight()
                ));
        try {
            Color c = new Color(img.getRGB(point.iX(), point.iY()));
            point.set(
                    convX.convert(c),
                    convY.convert(c)
            );

            return point;
        }
        catch(Exception e)
        {
            System.out.println("ayy");
        }

        return null;

    }

    @Override
    public Fragment<Vector2>[][] build(Vector2 dims) throws IllegalMapSizeException {
        Fragment<Vector2>[][] map;
        try
        {
            map = pos.build(dims);
        }
        catch (IllegalMapSizeException ex)
        {
            throw ex;
        }

        if(Fragment.checkArrayDims(map, dims))
        {
            ImageConvertFragment[][] thisArray = new ImageConvertFragment[dims.iX()][dims.iY()];
            for(int i=0; i<dims.X(); i++)
            {
                for(int j=0; j<dims.Y(); j++)
                {
                    thisArray[i][j] = new ImageConvertFragment(img, map[i][j], convX, convY);
                }
            }
            return thisArray;
        }

        throw new IllegalMapSizeException();
    }
}

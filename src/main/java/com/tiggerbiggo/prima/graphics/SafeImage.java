package com.tiggerbiggo.prima.graphics;

import com.tiggerbiggo.prima.core.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class SafeImage {
    private BufferedImage img;
    private int width, height;

    public SafeImage(BufferedImage img)throws IllegalArgumentException{
        if(img == null) throw new IllegalArgumentException();

        this.img = img;
        this.width = img.getWidth();
        this.height = img.getHeight();
    }

    public Color getColor(Vector2 in) {
        return getColor(in.iX(), in.iY());
    }

    public Color getColor(int x, int y) {
        return new Color(getRGB(x, y));
    }

    public int getRGB(Vector2 in){
        return getRGB(in.iX(), in.iY());
    }

    public int getRGB(int x, int y) {
        x = x%width;
        y = y%height;

        x = Math.abs(x);
        y = Math.abs(y);

        return img.getRGB(x, y);
    }
}

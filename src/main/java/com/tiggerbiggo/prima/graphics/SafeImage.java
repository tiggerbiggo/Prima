package com.tiggerbiggo.prima.graphics;

import com.tiggerbiggo.prima.core.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SafeImage implements Serializable{
    private transient BufferedImage img;
    private int width, height;

    public SafeImage(BufferedImage img)throws IllegalArgumentException{
        if(img == null) throw new IllegalArgumentException();

        this.img = img;
        this.width = img.getWidth();
        this.height = img.getHeight();
    }

    public SafeImage(int width, int height)throws IllegalArgumentException {
        if(width <=0 || height <=0) throw new IllegalArgumentException();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.width = width;
        this.height = height;
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

    public int getWidth(){ return img.getWidth(); }

    public int getHeight(){ return img.getHeight(); }

    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        int[] outArray = new int[width*height];
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                outArray[i + (j*width)] = img.getRGB(i, j);
            }
        }
        out.writeObject(outArray);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        try {
            int[] inArray = (int[])in.readObject();

            for(int i=0; i<width; i++){
                for(int j=0; j<height; j++){
                    img.setRGB(i, j, inArray[i + (j*width)]);
                }
            }
        }
        catch (IOException | ClassNotFoundException e) {
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
    }
}

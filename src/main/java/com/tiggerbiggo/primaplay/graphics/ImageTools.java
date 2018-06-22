package com.tiggerbiggo.primaplay.graphics;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ImageTools {
  public static Color colorFromPosition(SafeImage img, Vector2 pos){
    //denormalise the input vector
    pos = pos.multiply(img.getDimensions());

    //get x and y
    int x, y;
    x = pos.iX();
    y = pos.iY();

    //get truncated delta values, e.g 5.162 becomes 0.162 for both x and y
    double dx, dy;
    dx = pos.X() - x;
    dy = pos.Y() - y;

    //Colors for the 4 quadrants
    Color A, B, C, D;

    A = img.getColor(x, y);
    B = img.getColor(x+1, y);
    C = img.getColor(x, y+1);
    D = img.getColor(x+1, y+1);

    Color L, R;

    L = ColorTools.colorLerp(ColorTools.colorLerp(A, B, dx), ColorTools.colorLerp(C, D, dx), dy);
    R = ColorTools.colorLerp(ColorTools.colorLerp(A, C, dy), ColorTools.colorLerp(B, D, dy), dx);

    return ColorTools.colorAvg(L, R);
  }

  public static SafeImage[] toSafeImage(BufferedImage[] imgs){
    SafeImage[] toReturn = new SafeImage[imgs.length];
    for(int i=0; i<imgs.length; i++){
      toReturn[i] = new SafeImage(imgs[i]);
    }
    return toReturn;
  }

  public static BufferedImage[] toBufferedImage(SafeImage[] imgs){
    BufferedImage[] toReturn = new BufferedImage[imgs.length];
    for(int i=0; i<imgs.length; i++){
      toReturn[i] = imgs[i].getImg();
    }
    return toReturn;
  }

  public static BufferedImage toBufferedImage(SafeImage img){
    return img.getImg();
  }

  public static Image[] toFXImage(SafeImage[] img){
    return toFXImage(toBufferedImage(img));
  }

  public static Image[] toFXImage(BufferedImage[] img){
    Image[] toReturn = new Image[img.length];
    for(int i=0; i<img.length; i++){
      toReturn[i] = toFXImage(img[i]);
    }
    return toReturn;
  }

  public static Image toFXImage(BufferedImage img){
    return SwingFXUtils.toFXImage(img, null);
  }
  public static Image toFXImage(SafeImage img){
    return toFXImage(toBufferedImage(img));
  }
}

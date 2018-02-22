package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.calculation.ColorTools;
import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.graphics.SafeImage;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.awt.*;
import java.io.Serializable;
import java.util.function.Function;

/**Converts an image to a vector map given a conversion formula.
 *
 * <p>Conversion function presets are found as final fields within this class.</p>
 */
public class ImageConvertFragment implements Fragment<Vector2>, Serializable{

    private SafeImage img;
    private Fragment<Vector2> pos;
    private Function<Color, Double> convX, convY;

    /**Constructs a new Image Convert Fragment
     *
     * @param pos The position fragment to use for image sampling
     * @param img The image used to sample from
     * @param convX The conversion function for the X axis
     * @param convY The conversion function for the Y axis
     */
    public ImageConvertFragment(Fragment<Vector2> pos, SafeImage img, Function<Color, Double> convX, Function<Color, Double> convY) {
        this.img = img;
        this.pos = pos;
        this.convX = convX;
        this.convY = convY;
    }

    /** Shorter constructor for when the same conversion function is to be used for both X and Y
     *
     * @param pos The position fragment to use for image sampling
     * @param img The image used to sample from
     * @param conv The conversion function for both axes
     */
    public ImageConvertFragment(Fragment<Vector2> pos, SafeImage img, Function<Color, Double> conv)
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
        return new Vector2(convX.apply(c), convY.apply(c));
    }

    public static final Function<Color, Double> H = ColorTools::getHue;
    public static final Function<Color, Double> S = ColorTools::getSaturation;
    public static final Function<Color, Double> V = ColorTools::getBrightness;
    public static final Function<Color, Double> R = (c -> c.getRed()/255d);
    public static final Function<Color, Double> G = (c -> c.getGreen()/255d);
    public static final Function<Color, Double> B = (c -> c.getBlue()/255d);

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

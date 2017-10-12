package Calculation;
import java.awt.*;

public class ColorTools
{
    /**
     * Linearly interpolates between 2 colors. See lerp() for more information on how this functions.
     * @param c1 Start color
     * @param c2 End color
     * @param a Interpolation coefficient
     * @return new Color value generated from c1 and c2
     */
    public static Color colorLerp(Color c1, Color c2, float a)
    {
        return new Color(
                (int)Calculation.clampedLerp(c1.getRed(), c2.getRed(), a, 0, 255),
                (int)Calculation.clampedLerp(c1.getGreen(), c2.getGreen(), a, 0, 255),
                (int)Calculation.clampedLerp(c1.getBlue(), c2.getBlue(), a, 0, 255)
        );
    }
}

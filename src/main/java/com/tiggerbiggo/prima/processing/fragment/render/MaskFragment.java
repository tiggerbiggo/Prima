package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.calculation.Calculation;
import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.util.function.Function;

/**
 */
public class MaskFragment implements Fragment<Double> {

    private Function<Vector2, Double> func;
    private Fragment<Vector2> in;

    /**
     * @return
     *
     * @return
     * @return
     */
    public MaskFragment(Fragment<Vector2> in, Function<Vector2, Double> func) {
        this.func = func;
        this.in = in;
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
    public Double get(int x, int y, int w, int h, int num) {
        Vector2 v = in.get(x, y, w, h, num);
        if(v == null) return 0d;
        return Calculation.mod(func.apply(v), 1);
    }

    public enum MaskConversionType{
        X(Vector2::X),
        Y(Vector2::Y),
        XPLUSY((v) -> v.X() + v.Y()),
        MAGNITUDE(Vector2::magnitude);

        /**
         * Enum MaskConversionType ...
         *
         * @author A678364
         * Created on 20/02/2018
         */
        Function<Vector2, Double> func;

        /**
         * @return
         *
         * @return
         */
        MaskConversionType(Function<Vector2, Double> func) {this.func = func;}

        /**
         * @author A678364
         * Created on 20/02/2018
         * @return Function<Vector2 ,   Double>
         */
        public Function<Vector2, Double> getFunction() {
            return func;
        }
    }
}

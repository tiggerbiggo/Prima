package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.calculation.Calculation;
import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.util.function.Function;

/**
 */
public class AnimationFragment implements Fragment<Vector2[]> {

    Fragment<Vector2> in;
    Function<Double, Vector2> func;

    /**
     * @return
     *
     * @return
     * @return
     */
    public AnimationFragment(Fragment<Vector2> in, Function<Double, Vector2> func) {
        this.in = in;
        this.func = func;
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
    public Vector2[] get(int x, int y, int w, int h, int num) {
        Vector2 start = in.get(x, y, w, h, num);

        Vector2[] toReturn = new Vector2[num];
        for(int i=0; i<num; i++){
            double percent = (double)i/num;
            toReturn[i] = Vector2.add(func.apply(percent), start);
        }

        return toReturn;
    }

    public enum AnimTypes{
        SIMPLE(Vector2::new),
        SINSIN((i) -> {
            return new Vector2(Math.sin(i*Math.PI*2)*(1/Math.PI));
        }),
        REVERSE((i)->{
            return new Vector2(Calculation.modLoop(i, true));
        }),
        STILL((i) ->{
            return new Vector2(0);
        });
        /**
         * Enum AnimTypes ...
         *
         * @author A678364
         * Created on 20/02/2018
         */
        Function<Double, Vector2> f;
        /**
         * @return
         *
         * @return
         */
        AnimTypes(Function<Double, Vector2> f){this.f = f;}

        /**
         * @author A678364
         * Created on 20/02/2018
         * @return Function<Double ,   Vector2>
         */
        public Function<Double, Vector2> getF() {
            return f;
        }
    }
}

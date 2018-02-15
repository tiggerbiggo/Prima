package com.tiggerbiggo.prima.processing.fragment.generate;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.gui.ControlPane;
import com.tiggerbiggo.prima.processing.fragment.Controllable;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.Serializable;
import java.util.Random;

public class NoiseGenFragment implements Fragment<Vector2>, Serializable, Controllable {

    long seed;
    double mul;

    public NoiseGenFragment(long seed, double mul) {
        this.seed = seed;
        this.mul = mul;
    }
    public NoiseGenFragment(double mul){this(new Random().nextLong(), mul);}
    public NoiseGenFragment(){this(1);}

    @Override
    public Fragment<Vector2>[][] build(int xDim, int yDim) throws IllegalMapSizeException {
        NoiseGenFragment[][] map = new NoiseGenFragment[xDim][yDim];
        Random r = new Random(seed);
        for(int i = 0; i < xDim; i++) {
            for(int j = 0; j < yDim; j++) {
                map[i][j] = new NoiseGenFragment(r.nextLong(), mul);
            }
        }
        return map;
    }

    @Override
    public Fragment<Vector2>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException{return null;}
    @Override
    public Fragment<Vector2> getNew(int i, int j) {return null;}

    @Override
    public Vector2 get() {
        Random r = new Random(seed);
        return new Vector2(r.nextDouble()*mul, r.nextDouble()*mul);
    }

    @Override
    public ControlPane getControls(ControlPane p) {
        JSpinner s = new JSpinner(new SpinnerNumberModel(1, -1000, 1000, 0.1));
        s.addChangeListener(e -> mul = (double)s.getValue());
        p.add(s);
        return p;
    }
}
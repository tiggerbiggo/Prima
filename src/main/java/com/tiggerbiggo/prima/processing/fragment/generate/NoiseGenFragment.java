package com.tiggerbiggo.prima.processing.fragment.generate;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.gui.ControlPane;
import com.tiggerbiggo.prima.processing.fragment.Controllable;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import javax.swing.*;
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
    public Vector2 get(int x, int y, int w, int h, int num) {
        Random r = new Random(seed+(x+(w*y)));
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
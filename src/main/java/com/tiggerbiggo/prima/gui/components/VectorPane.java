package com.tiggerbiggo.prima.gui.components;

import com.tiggerbiggo.prima.core.Vector2;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class VectorPane extends JPanel {
    JLabel xLabel, yLabel;
    JSpinner xS, yS;
    Vector2 v;

    public VectorPane(Vector2 init, double min, double max, double step){
        v = init;
        xLabel = new JLabel("X:");
        yLabel = new JLabel("Y:");

        xS = new JSpinner(new SpinnerNumberModel(v.X(), min, max, step));
        yS = new JSpinner(new SpinnerNumberModel(v.Y(), min, max, step));

        ChangeListener change = e -> v.set(
                (double)xS.getValue(),
                (double)yS.getValue()
        );

        xS.addChangeListener(change);
        yS.addChangeListener(change);

        setLayout(new GridLayout(1, 4));

        add(xLabel);
        add(xS);
        add(yLabel);
        add(yS);
    }

    public Vector2 getVec(){return v;}
}

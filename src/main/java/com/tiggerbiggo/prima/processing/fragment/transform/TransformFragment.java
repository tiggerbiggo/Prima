package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.gui.ControlPane;
import com.tiggerbiggo.prima.presets.Transform;
import com.tiggerbiggo.prima.processing.fragment.Controllable;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import javax.swing.*;
import java.io.Serializable;

/**
 */
public class TransformFragment implements Fragment<Vector2>, Serializable, Controllable
{
    private final Fragment<Vector2> in;
    private Transform t;

    /**
     * @return
     *
     * @return
     * @return
     */
    public TransformFragment(Fragment<Vector2> in, Transform t) {
        this.in = in;
        this.t = t;
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
        return t.doFormula(in.get(x, y, w, h, num));
    }

    /**
     * @param
     *
     * @return ControlPane
     */
    @Override
    public ControlPane getControls(ControlPane p) {
        if(p == null) return null;

        JComboBox<Transform> transforms = new JComboBox<>();

        for(Transform tr : Transform.values()){
            transforms.addItem(tr);
        }

        transforms.addItemListener(e -> {
            t = transforms.getItemAt(transforms.getSelectedIndex());
            System.out.println(hashCode() + " Changed transform... " + transforms.getSelectedIndex());
        });

        p.add(transforms);

        Controllable c = this;
        JButton down = new JButton();
        down.addActionListener(e -> p.giveControllable(c));

        return p;
    }
}

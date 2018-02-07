package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;
import com.tiggerbiggo.prima.gui.ControlPane;
import com.tiggerbiggo.prima.presets.Transform;
import com.tiggerbiggo.prima.processing.fragment.Controllable;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import javax.swing.*;
import java.io.Serializable;

public class TransformFragment implements Fragment<Vector2>, Serializable, Controllable
{
    private final Fragment<Vector2> in;
    private Fragment<Vector2>[][] map;
    private Transform t;

    public TransformFragment(Fragment<Vector2> in, Transform t) {
        this.in = in;
        this.t = t;
    }

    @Override
    public Vector2 get() {
        return transform(in.get());
    }

//    @Override
//    public Fragment<Vector2>[][] build(int xDim, int yDim) throws IllegalMapSizeException {
//        Fragment<Vector2>[][] map;
//        try
//        {
//            map = in.build(xDim, yDim);
//        }
//        catch (IllegalMapSizeException ex)
//        {
//            throw ex;
//        }
//
//        if(Fragment.checkArrayDims(map, xDim, yDim))
//        {
//            TransformFragment[][] thisArray = new TransformFragment[xDim][yDim];
//            for(int i=0; i<xDim; i++)
//            {
//                for(int j=0; j<yDim; j++)
//                {
//                    thisArray[i][j] = new TransformFragment(map[i][j], t);
//                }
//            }
//            return thisArray;
//        }
//
//        throw new IllegalMapSizeException();
//    }

    @Override
    public Fragment<Vector2>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        map = in.build(xDim, yDim);
        return new TransformFragment[xDim][yDim];
    }

    @Override
    public Fragment<Vector2> getNew(int i, int j) {
        return new TransformFragment(map[i][j], t);
    }

    private Vector2 transform(Vector2 in){return t.doFormula(in);}

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

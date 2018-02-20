package com.tiggerbiggo.prima.gui;

import com.tiggerbiggo.prima.processing.fragment.Controllable;

import javax.swing.*;
import java.awt.*;

/**
 */
public class ControlPane extends JPanel{
    /**
     * @return
     */
    public ControlPane(){
        setLayout(new GridLayout(0, 1));
    }

    /**
     * @param
     *
     */
    public void giveControllable(Controllable c){}
}

package com.tiggerbiggo.prima.gui;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.Controllable;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import javax.swing.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    ControlPane pane;
    public MainFrame(Controllable c){
        setPane(c);
        pane = c.getControls(pane);
        add(pane);
    }

    private void setPane(Controllable c){
        if(c == null)return;
        if(pane != null)remove(pane);
        pane = new ControlPane(){
            @Override
            public void giveControllable(Controllable c) {
                setPane(c);
            }
        };
    }
}

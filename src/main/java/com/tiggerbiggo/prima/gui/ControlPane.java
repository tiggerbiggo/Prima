package com.tiggerbiggo.prima.gui;

import com.tiggerbiggo.prima.processing.fragment.Controllable;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class ControlPane extends JPanel {

  public ControlPane() {
    setLayout(new GridLayout(0, 1));
  }

  public void giveControllable(Controllable c) {
  }
}

package com.tiggerbiggo.prima.gui;

import com.tiggerbiggo.prima.processing.fragment.Controllable;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

  ControlPane pane;

  public MainFrame(Controllable c) {
    setPane(c);
    pane = c.getControls(pane);
    add(pane);
  }

  private void setPane(Controllable c) {
    if (c == null) {
      return;
    }
    if (pane != null) {
      remove(pane);
    }
    pane = new ControlPane() {
      @Override
      public void giveControllable(Controllable c) {
        setPane(c);
      }
    };
  }
}

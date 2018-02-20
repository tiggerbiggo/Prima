package com.tiggerbiggo.prima.processing.fragment;

import com.sun.istack.internal.NotNull;
import com.tiggerbiggo.prima.gui.ControlPane;

/**
 */
public interface Controllable {
    /**
     * @param
     *
     * @return ControlPane
     */
    ControlPane getControls(ControlPane p);
}

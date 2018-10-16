package com.tiggerbiggo.prima.primaplay.node.core;

import ch.hephaistos.utilities.loki.ReflectorGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import javafx.scene.Node;

public interface INode {
  String getName();
  String getDescription();
  default Node getFXNode(ChangeListener listener){
    ReflectorGrid grid = new ReflectorGrid();
    grid.addChangeListener(listener);
    grid.transfromIntoGrid(this);
    return grid;
  }
}

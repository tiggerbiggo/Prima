package com.tiggerbiggo.prima.play.node.core;

import ch.hephaistos.utilities.loki.ReflectorGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import javafx.scene.Node;

public interface INode {
  String getName();
  String getDescription();
  default Node getFXNode(ChangeListener listener){//Listener provides trigger events.
    ReflectorGrid grid = new ReflectorGrid();
    grid.transfromIntoGrid(this);
    return grid;
  }
}

package com.tiggerbiggo.prima.play.core.calculation;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class GUITools {
  public static void setAllAnchors(Node n, double val){
    AnchorPane.setBottomAnchor(n, val);
    AnchorPane.setTopAnchor(n, val);
    AnchorPane.setLeftAnchor(n, val);
    AnchorPane.setRightAnchor(n, val);
  }
}

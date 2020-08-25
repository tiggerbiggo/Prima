package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.sun.javafx.collections.ObservableListWrapper;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.NumberArrayInputLink;
import com.tiggerbiggo.prima.play.node.link.type.NumberArrayOutputLink;
import com.tiggerbiggo.prima.view.sample.components.DraggableValueField;
import java.util.Arrays;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class PixelMapperNode extends NodeInOut {
  @TransferGrid
  boolean gaps = true;

  @TransferGrid
  double multiplier = 0.2;

  @TransferGrid
  TrypDirection dir = TrypDirection.VERTICAL;

  NumberArrayInputLink startFrom;
  NumberArrayOutputLink out;

  public PixelMapperNode(){
    startFrom = new NumberArrayInputLink(){
      @Override
      public Double[] defaultValue(RenderParams p) {
        final int n = p.frameNum();
        Double[] toReturn = new Double[n];
        for (int i = 0; i < n; i++) {
          toReturn[i] = (double)i/n;
        }
        return toReturn;
      }
    };
    addInput(startFrom);

    out = new NumberArrayOutputLink() {
      @Override
      public Double[] get(RenderParams p) {
        int x, y;
        x = p.x();
        y = p.y();

        Double[] ret;

        if(dir == TrypDirection.HORIZONTAL){
          ret = getNumbers(x, startFrom.get(p), gaps && y % 2 == 0);
        }
        else{
          ret = getNumbers(y, startFrom.get(p), gaps && x % 2 == 0);
        }

        return ret;
      }
    };
    addOutput(out);
  }

  private Double[] getNumbers(double base, Double[] startFrom, boolean invert){
    Double[] toReturn = new Double[startFrom.length];
    for (int i = 0; i < toReturn.length; i++) {
      if(invert)
        toReturn[i] = (base * multiplier) - startFrom[i];
      else
        toReturn[i] = (base * multiplier) + startFrom[i];
    }
    return toReturn;
  }

  @Override
  public String getName() {
    return "Pixel Map Node";
  }

  @Override
  public String getDescription() {
    return "Outputs an integer number based on the X, Y coordinates of the pixel.";
  }

  @Override
  public Node getFXNode(ChangeListener listener) {
    ComboBox<TrypDirection> dirBox = new ComboBox<>();
    dirBox.setItems(new ObservableListWrapper<>(Arrays.asList(TrypDirection.values())));
    dirBox.setValue(dir);
    dirBox.setOnAction(e -> dir = dirBox.getValue());

    CheckBox gapBox = new CheckBox("Gaps");
    gapBox.setSelected(gaps);
    gapBox.setOnAction(event -> gaps = gapBox.isSelected());

    DraggableValueField mulValue = new DraggableValueField(multiplier, -5, 5, 0.05);
    mulValue.addCallback(value -> multiplier = value);

    return new VBox(dirBox, gapBox, mulValue);
  }
}

enum TrypDirection{
  VERTICAL("Vertical"),
  HORIZONTAL("Horizontal");

  String s;

  TrypDirection(String s){
    this.s = s;
  }

  @Override
  public String toString() {
    return s;
  }
}

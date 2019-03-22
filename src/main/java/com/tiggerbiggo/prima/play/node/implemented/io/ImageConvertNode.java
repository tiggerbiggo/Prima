package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.sun.javafx.collections.ObservableListWrapper;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorConvertType;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.ColorOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.ImageInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorArrayInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.defaults.MapGenDefaultLink;
import java.awt.Color;
import java.util.Arrays;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ImageConvertNode extends NodeInOut{

  @TransferGrid
  private ColorConvertType convertX = ColorConvertType.V;
  @TransferGrid
  private ColorConvertType convertY = ColorConvertType.V;

  @TransferGrid
  private boolean xLoop = false;
  @TransferGrid
  private boolean yLoop = false;

  private VectorInputLink pos;
  private VectorArrayInputLink animPos;
  private ImageInputLink img;

  private VectorOutputLink vecOut;
  private VectorArrayOutputLink animVecOut;
  private ColorOutputLink colOut;
  private ColorArrayOutputLink animColOut;

  public ImageConvertNode(){
    pos = new MapGenDefaultLink();
    animPos = new VectorArrayInputLink();
    img = new ImageInputLink();
    addInput(pos, animPos, img);

    vecOut = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        Color sample = colOut.get(p);

        return new Vector2(
            convertX.convertColor(sample),
            convertY.convertColor(sample));
      }
    };

    animVecOut = new VectorArrayOutputLink() {
      @Override
      public Vector2[] get(RenderParams p) {
        Vector2[] toReturn = new Vector2[p.frameNum()];
        Color[] sample = animColOut.get(p);
        for(int i=0; i<p.frameNum(); i++){
          toReturn[i] = new Vector2(convertX.convertColor(sample[i]), convertY.convertColor(sample[i]));
        }
        return toReturn;
      }
    };

    colOut = new ColorOutputLink() {
      @Override
      public Color get(RenderParams p) {
        SafeImage currentImage = img.get(p);

        Vector2 position = pos.get(p);

        return currentImage.getColor(currentImage.denormVectorAndLoop(position, xLoop, yLoop));
      }
    };

    animColOut = new ColorArrayOutputLink() {
      @Override
      public Color[] get(RenderParams p) {
        Color[] toReturn = new Color[p.frameNum()];
        Vector2[] positions = animPos.get(p);
        SafeImage currentImage = img.get(p);
        for(int i=0; i<p.frameNum(); i++){
          toReturn[i] = currentImage.getColor(currentImage.denormVectorAndLoop(positions[i], xLoop, yLoop));
        }
        return toReturn;
      }
    };
    addOutput(vecOut, animVecOut, colOut, animColOut);
  }

  @Override
  public String getName() {
    return "Image Convert Node";
  }

  @Override
  public String getDescription() {
    return "Converts a single still image to numbers.";
  }

  @Override
  public Node getFXNode(ChangeListener listener) {
    ObservableList<ColorConvertType> ol = new ObservableListWrapper<>(Arrays.asList(ColorConvertType.values()));

    ComboBox<ColorConvertType> typeX = new ComboBox<>(ol);
    typeX.setValue(convertX);
    typeX.setOnAction(e -> convertX = typeX.getValue());
    ComboBox<ColorConvertType> typeY = new ComboBox<>(ol);
    typeY.setValue(convertY);
    typeY.setOnAction(e -> convertY = typeY.getValue());

    CheckBox loopX = new CheckBox("X");
    loopX.setSelected(xLoop);
    loopX.setOnAction(e -> xLoop = loopX.isSelected());
    CheckBox loopY = new CheckBox("Y");
    loopY.setOnAction(e -> yLoop = loopY.isSelected());
    loopY.setSelected(yLoop);

    return new VBox(
        new Label("Convert"),
        new HBox(typeX, typeY),
        new Label("Loop:"),
        new HBox(loopX, loopY)
    );
  }
}

package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderCallback;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.core.render.Renderer;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.defaults.MapGenDefaultLink;
import java.awt.Color;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ImageCacheNode extends NodeInOut {

  SafeImage[] cached;

  @TransferGrid
  int width = 100;
  @TransferGrid
  int height = 100;
  @TransferGrid
  int frameCount = 60;

  ColorArrayInputLink colIn;
  VectorInputLink posIn;
  VectorInputLink timeIn;

  ColorArrayOutputLink colOut;

  public ImageCacheNode() {
    colIn = new ColorArrayInputLink("Colour");
    posIn = new MapGenDefaultLink("Position");
    timeIn = new VectorInputLink("Time");
    addInput(colIn, posIn, timeIn);

    colOut = new ColorArrayOutputLink("Out") {
      @Override
      public Color[] get(RenderParams p) {
        if(cached == null || cached[0] == null || cached.length < p.frameNum()){
          return ColorTools.colorArray(p.frameNum());
        }
        Color[] toReturn = new Color[p.frameNum()];
        Vector2 time = timeIn.get(p);
        for(int i=0; i<toReturn.length; i++){
          double percent = Math.abs(time.xy()) % 1;

          //multiply by number of images
          percent *= cached.length;
          percent += i;
          percent = percent % cached.length;

          SafeImage im = cached[(int)percent];
          //Get the color from each frame
          toReturn[i] = im.getColor(im.denormVector(posIn.get(p).mod(1)));
        }
        return toReturn;
      }

      @Override
      public void generateGLSLMethod(StringBuilder s) {
        //TODO
        throw new NotImplementedException();
      }

      @Override
      public String getMethodName() {
        //TODO
        throw new NotImplementedException();
      }
    };

    addOutput(colOut);
  }

  @Override
  public String getName() {
    return "Image Cache Node";
  }

  @Override
  public String getDescription() {
    return "Allows for recursion by caching the last rendered image.";
  }

  //TODO; this class lol

  @Override
  public Node getFXNode(ChangeListener listener) {
    Button cacheButton = new Button("Update");

    Spinner<Integer> widthSpin = new Spinner<>(1, 1000, width);
    widthSpin.valueProperty().addListener(o -> width = widthSpin.getValue());
    widthSpin.setEditable(true);

    Spinner<Integer> heightSpin = new Spinner<>(1, 1000, height);
    heightSpin.valueProperty().addListener(o -> height = heightSpin.getValue());
    heightSpin.setEditable(true);

    cacheButton.setOnAction(event -> {
      cacheButton.setDisable(true);
      Renderer.queueToDefaultRenderer(width, height, frameCount, colIn, new RenderCallback() {
        @Override
        public void callback(SafeImage[] imgs) {
          cached = imgs;
          cacheButton.setDisable(false);
        }
      });
      //Renderer.getInstance().queue(new RenderTask(width, height, ));
    });


    Spinner<Integer> frameSpin = new Spinner<>(1, 120, frameCount);
    frameSpin.valueProperty().addListener(o -> frameCount = frameSpin.getValue());
    frameSpin.setEditable(true);

    return new VBox(
            new HBox(new Label("Width"), widthSpin),
            new HBox(new Label("Height"), heightSpin),
            new HBox(new Label("Frames"), frameSpin),
            cacheButton
    );
  }
}

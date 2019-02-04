package com.tiggerbiggo.prima.primaplay.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.primaplay.core.render.RenderParams;
import com.tiggerbiggo.prima.primaplay.core.render.RenderTask;
import com.tiggerbiggo.prima.primaplay.core.render.Renderer;
import com.tiggerbiggo.prima.primaplay.graphics.ColorTools;
import com.tiggerbiggo.prima.primaplay.graphics.SafeImage;
import com.tiggerbiggo.prima.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.defaults.MapGenDefaultLink;
import java.awt.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;

@Deprecated
public class ImageCacheNode extends NodeInOut {

  SafeImage[] cached;

  int width = 100;
  int height = 100;

  ColorArrayInputLink colIn;
  VectorInputLink posIn;

  ColorArrayOutputLink colOut;

  public ImageCacheNode() {
    colIn = new ColorArrayInputLink();
    posIn = new MapGenDefaultLink();
    addInput(colIn, posIn);

    colOut = new ColorArrayOutputLink() {
      @Override
      public Color[] get(RenderParams p) {
        if(cached == null || cached.length != p.frameNum()){
          return ColorTools.blankArray(p.frameNum());
        }
        Color[] toReturn = new Color[p.frameNum()];
        for(int i=0; i<toReturn.length; i++){
          SafeImage im = cached[i];
          //Get the color from each frame
          toReturn[i] = im.getColor(im.denormVector(posIn.get(p)));
        }
        return toReturn;
      }
    };
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
    cacheButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //Renderer.getInstance().queue(new RenderTask(width, height, ));
      }
    });
    return null;
  }
}

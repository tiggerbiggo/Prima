package com.tiggerbiggo.prima.primaplay.node.implemented.output;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.primaplay.core.FileManager;
import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.graphics.ImageTools;
import com.tiggerbiggo.prima.primaplay.graphics.SafeImage;
import com.tiggerbiggo.prima.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.prima.primaplay.node.link.type.ImageArrayOutputLink;
import com.tiggerbiggo.utils.calculation.ReflectionHelper;
import java.lang.reflect.Field;

public class MultiImageLoadNode extends NodeHasOutput implements ChangeListener{
  @TransferGrid
  private String filename = "";

  private static Field f_filename = ReflectionHelper
      .getFieldFromClass(MultiImageLoadNode.class, "filename");

  private ImageArrayOutputLink out;

  private SafeImage[] imgs = null;

  public MultiImageLoadNode(){
    out = new ImageArrayOutputLink() {
      @Override
      public SafeImage[] get(RenderParams p) {
        if(imgs != null)
          return imgs;
        else
          return ImageTools.blankArray();
      }
    };
    addOutput(out);
  }

  @Override
  public String getName() {
    return "Multi Image Load node";
  }

  @Override
  public String getDescription() {
    return "Loads a list of images from a folder";
  }

  @Override
  public void onObjectValueChanged(Field field, Object o) {
    if(field.equals(f_filename)){
      imgs = ImageTools.toSafeImage(FileManager.getImgsFromFolder(filename, true));
    }
  }
}

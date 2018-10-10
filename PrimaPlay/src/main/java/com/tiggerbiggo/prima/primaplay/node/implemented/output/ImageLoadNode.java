package com.tiggerbiggo.prima.primaplay.node.implemented.output;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.primaplay.core.FileManager;
import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.graphics.ImageTools;
import com.tiggerbiggo.prima.primaplay.graphics.SafeImage;
import com.tiggerbiggo.prima.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.prima.primaplay.node.link.type.ImageOutputLink;
import com.tiggerbiggo.utils.calculation.ReflectionHelper;
import java.lang.reflect.Field;

public class ImageLoadNode extends NodeHasOutput implements ChangeListener{

  @TransferGrid
  private String filename = "";

  private static Field f_filename = ReflectionHelper.getFieldFromClass(ImageLoadNode.class, "filename");

  private ImageOutputLink out;

  private SafeImage img = null;

  public ImageLoadNode(){
    out = new ImageOutputLink() {
      @Override
      public SafeImage get(RenderParams p) {
        if(img != null)
          return img;
        else
          return ImageTools.blankImage();
      }
    };
    addOutput(out);
  }

  @Override
  public String getName() {
    return "Image Load node";
  }

  @Override
  public String getDescription() {
    return "Loads single image from a folder";
  }

  @Override
  public void onObjectValueChanged(Field field, Object oldValue, Object newValue, Object o) {
    if(field.equals(f_filename)){
      img = FileManager.safeGetImg(filename);
    }
  }
}

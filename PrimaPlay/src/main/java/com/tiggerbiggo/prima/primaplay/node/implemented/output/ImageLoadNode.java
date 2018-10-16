package com.tiggerbiggo.prima.primaplay.node.implemented.output;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.primaplay.core.FileManager;
import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.graphics.ImageTools;
import com.tiggerbiggo.prima.primaplay.graphics.SafeImage;
import com.tiggerbiggo.prima.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.prima.primaplay.node.link.type.ImageOutputLink;
import com.tiggerbiggo.utils.calculation.GUITools;
import com.tiggerbiggo.utils.calculation.ReflectionHelper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class ImageLoadNode extends NodeHasOutput {

  private File file;

  @TransferGrid
  private String filename = "";

  private static Field f_filename = ReflectionHelper
      .getFieldFromClass(ImageLoadNode.class, "filename");

  private ImageOutputLink out;

  private SafeImage img = null;

  public ImageLoadNode() {
    out = new ImageOutputLink() {
      @Override
      public SafeImage get(RenderParams p) {
        if (img != null) {
          return img;
        } else {
          return ImageTools.blankImage();
        }
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

  public void updateImage() {
    if (file != null) {
      img = FileManager.safeGetImg(file);
    }
  }

  @Override
  public Node getFXNode(ChangeListener listener) {
    TextField field = new TextField(filename);
    if (file == null) {
      file = new File(filename);
      updateImage();
    }

    Button loadButton = new Button("...");
    loadButton.getStyleClass().add("buttonRight");
    loadButton.setPrefWidth(40);
    loadButton.setPrefHeight(40);
    loadButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          String old = filename;
          file = FileManager.showOpenDialogue("imgs/", FileManager.IMGS);
          updateImage();
          filename = file.toString();
          field.setText(filename);
          listener.onObjectValueChanged(f_filename, old, filename, this);

        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    field.setPrefWidth(Region.USE_COMPUTED_SIZE);
    field.setPrefHeight(40);

    field.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        filename = field.getText();
      }
    });

    GUITools.setAllAnchors(field, 0);

    AnchorPane toReturn = new AnchorPane(new HBox(field, loadButton));

    return toReturn;
  }
}

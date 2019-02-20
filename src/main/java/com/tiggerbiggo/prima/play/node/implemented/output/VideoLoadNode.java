package com.tiggerbiggo.prima.play.node.implemented.output;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.FileManager;
import com.tiggerbiggo.prima.play.core.calculation.GUITools;
import com.tiggerbiggo.prima.play.core.calculation.ReflectionHelper;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ImageTools;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.core.NodeHasOutput;
import com.tiggerbiggo.prima.play.node.link.type.ImageArrayOutputLink;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class VideoLoadNode extends NodeHasOutput {
  private transient File file;

  @TransferGrid
  private String filename = "";

  SafeImage[] imgs;
  private ImageArrayOutputLink out;

  private static Field f_filename = ReflectionHelper
      .getFieldFromClass(VideoLoadNode.class, "filename");

  private transient Label nameLabel, widthLabel, heightLabel;

  final String nameString = "Filename: ";
  final String widthString = "Width: ";
  final String heightString = "Height: ";

  public VideoLoadNode() {
    out = new ImageArrayOutputLink() {
      @Override
      public SafeImage[] get(RenderParams p) {
        if(imgs == null) return ImageTools.blankArray();
        return imgs;
      }
    };
    addOutput(out);

    nameLabel = new Label(nameString + "N/A");
    widthLabel = new Label(widthString + "0");
    heightLabel = new Label(heightString + "0");
  }

  @Override
  public String getName() {
    return "Video Load Node";
  }

  @Override
  public String getDescription() {
    return "Loads MP4 videos";
  }

  public void updateVideo(){
    if (file != null) {
      imgs = FileManager.getImgsFromVideo(file);
      if(imgs != null && imgs[0] != null) {
        nameLabel.setText(nameString + file.getName());
        widthLabel.setText(widthString + imgs[0].getWidth());
        heightLabel.setText(heightString + imgs[0].getHeight() + ", " + imgs.length);
      }
      else
      {
        nameLabel.setText("ERROR, invalid file.");
      }
    }
  }

  @Override
  public Node getFXNode(ChangeListener listener){
    TextField field = new TextField(filename);
    if (file == null) {
      file = new File(filename);
      updateVideo();
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
          file = FileManager.showOpenDialogue(FileManager.MP4);
          filename = file.toString();
          updateVideo();
          field.setText(filename);

          listener.onObjectValueChanged(f_filename, old, filename, this);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    field.setPrefWidth(Region.USE_COMPUTED_SIZE);
    field.setPrefHeight(40);
    field.setEditable(false);

    GUITools.setAllAnchors(field, 0);
    AnchorPane loader = new AnchorPane(new HBox(field, loadButton));


    return new VBox(loader, nameLabel, widthLabel, heightLabel);

  }
}

package com.tiggerbiggo.prima.play.node.implemented.input;

import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.render.RenderID;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ImageTools;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.core.NodeHasInput;
import com.tiggerbiggo.prima.play.node.link.type.UniversalInputLink;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class AnalysisNode extends NodeHasInput {

  transient private static final int WIDTH = 140;
  transient private static final int HEIGHT = 140;
  transient private UniversalInputLink input;
  transient private Timeline timer;

  transient private ImageView view;

  transient private RenderID currentID;

  public AnalysisNode() {
    input = new UniversalInputLink();
    addInput(input);

    timer = new Timeline();
  }

  public void refreshPreview(){
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        SafeImage img = new SafeImage(WIDTH, HEIGHT);
        currentID = new RenderID();
        RenderID id = currentID; //to protect against pressing button twice, might cause issues
        for(int i=0; i<WIDTH; i++){
          for(int j=0; j<HEIGHT; j++){
            img.setColor(i, j, input.getColor(new RenderParams(WIDTH, HEIGHT, i, j, 1, id)));
          }
        }
        view.setImage(ImageTools.toFXImage(img));
      }
    });
  }

  @Override
  public void onLinked() {
    refreshPreview();
  }

  @Override
  public String getName() {
    return "Analysis Node";
  }

  @Override
  public String getDescription() {
    return "Analyzes node input, diagnostic and investigative tool.";
  }

  private static final String NO_STRING = "Mouse over to view details";

  @Override
  public Node getFXNode(ChangeListener listener) {
    Label detail = new Label(NO_STRING);

    view = new ImageView();
    view.setOnMouseMoved(event -> detail.setText(
        input.getDetail(
            new RenderParams(WIDTH, HEIGHT, (int)event.getX(), (int)event.getY(), 1, getCurrentID())
        )));
    view.setOnMouseExited(e -> detail.setText(NO_STRING));

    Button refresh = new Button("Refresh");
    refresh.setOnAction(e -> refreshPreview());
    return new VBox(view, detail, refresh);
  }

  private RenderID getCurrentID(){
    return currentID;
  }
}

package com.tiggerbiggo.prima.play.node.implemented.input;

import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.render.RenderCallback;
import com.tiggerbiggo.prima.play.core.render.RenderID;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.core.render.Renderer;
import com.tiggerbiggo.prima.play.graphics.ImageTools;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.core.NodeHasInput;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.prima.play.node.link.type.UniversalInputLink;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.awt.*;

public class AnalysisNode extends NodeHasInput {

  transient private static final int WIDTH = 140;
  transient private static final int HEIGHT = 140;
  transient private UniversalInputLink input;
  transient private ColorArrayInputLink renderIn;
  transient private Timeline timer;


  transient private boolean singular;
  transient private Slider frameSlider;
  transient private ImageView view;

  transient private RenderID currentID;

  transient private SafeImage[] imgs;

  public AnalysisNode() {
    input = new UniversalInputLink("Universal Input");
    addInput(input);

    renderIn = new ColorArrayInputLink("If you see this while running the program, tell tiggerbiggo"){
      @Override
      public Color[] get(RenderParams p) {
        return input.getColors(p);
      }
    };


    timer = new Timeline();
  }

  public void refreshPreview(){
    int frameNum = singular ? 1 : 60;
    Renderer.queueToDefaultRenderer(WIDTH, HEIGHT, frameNum, renderIn, new RenderCallback() {
      @Override
      public void callback(SafeImage[] _imgs) {
        imgs = _imgs;
        updateSlider();
        updateImageView(0);
      }
    });
  }

  private void updateImageView(int n){
    if(imgs == null || n >= imgs.length || n < 0) return;

    view.setImage(ImageTools.toFXImage(imgs[n]));
  }

  private void updateSlider(){
    OutputLink link = input.getCurrentLink();
    if(link != null){
      singular = link.isSingular();
      System.out.println("SINGULAR: " + singular);
    }
    else singular = true;

    if(imgs == null || singular){
      frameSlider.setVisible(false);
    }
    else{
      frameSlider.setVisible(true);
      frameSlider.setMin(0);
      frameSlider.setMax(imgs.length-1);
      frameSlider.setValue(0);
    }
  }


  @Override
  public void onLinked() {
    updateSlider();
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

    frameSlider = new Slider(0, 0, 0);
    frameSlider.valueProperty().addListener(o -> updateImageView((int)frameSlider.getValue()));

    return new VBox(view, frameSlider, detail, refresh);
  }

  private RenderID getCurrentID(){
    return currentID;
  }
}

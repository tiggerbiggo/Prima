package com.tiggerbiggo.prima.play.node.implemented.input;

import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.node.core.NodeHasInput;
import com.tiggerbiggo.prima.play.node.link.type.UniversalInputLink;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class AnalysisNode extends NodeHasInput {

  private UniversalInputLink input;

  Timeline timer;

  ImageView view;

  public AnalysisNode() {
    input = new UniversalInputLink();
    addInput(input);

    timer = new Timeline();
  }

  public void refreshPreview(){

  }

  @Override
  public String getName() {
    return "Analysis Node";
  }

  @Override
  public String getDescription() {
    return "Analyzes node input, diagnostic and investigative tool.";
  }

  @Override
  public Node getFXNode(ChangeListener listener) {
    return null;
  }
}

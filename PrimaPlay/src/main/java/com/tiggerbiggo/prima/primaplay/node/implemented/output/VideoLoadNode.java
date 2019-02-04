package com.tiggerbiggo.prima.primaplay.node.implemented.output;

import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.primaplay.node.core.NodeHasOutput;
import javafx.scene.Node;

public class VideoLoadNode extends NodeHasOutput {

  public VideoLoadNode() {

  }

  @Override
  public String getName() {
    return "Video Load Node";
  }

  @Override
  public String getDescription() {
    return "Loads MP4 videos";
  }

  @Override
  public Node getFXNode(ChangeListener listener) {
    return null;
  }
}

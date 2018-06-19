package sample;

import com.tiggerbiggo.primaplay.node.implemented.MapGenNode;
import com.tiggerbiggo.primaplay.node.implemented.io.AnimationNode;
import com.tiggerbiggo.primaplay.node.implemented.io.ImageListNode;
import gnode.GLink;
import gnode.GNode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

public class MainController implements Initializable{
  @FXML
  private Pane nodeCanvas;

  @Override
  public void initialize(URL url, ResourceBundle rb){
    nodeCanvas.getChildren().add(new GNode(200, 50, new ImageListNode(), nodeCanvas));
    nodeCanvas.getChildren().add(new GNode(200, 50, new MapGenNode(), nodeCanvas));
    nodeCanvas.getChildren().add(new GNode(200, 50, new AnimationNode(), nodeCanvas));

    nodeCanvas.setOnDragOver(event -> event.acceptTransferModes(TransferMode.ANY));
    nodeCanvas.setOnDragDropped(event -> {
      Object gestureSource = event.getGestureSource();
      if (gestureSource == null) {
        return;
      }
      if (gestureSource instanceof GLink) {
      }
    });
  }
}

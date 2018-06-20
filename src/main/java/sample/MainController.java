package sample;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.FileManager;
import com.tiggerbiggo.primaplay.graphics.Gradient;
import com.tiggerbiggo.primaplay.graphics.HueCycleGradient;
import com.tiggerbiggo.primaplay.node.core.RenderNode;
import com.tiggerbiggo.primaplay.node.implemented.BasicRenderNode;
import com.tiggerbiggo.primaplay.node.implemented.MapGenNode;
import com.tiggerbiggo.primaplay.node.implemented.io.AnimationNode;
import com.tiggerbiggo.primaplay.node.implemented.io.GradientNode;
import com.tiggerbiggo.primaplay.node.implemented.io.ImageListNode;
import com.tiggerbiggo.primaplay.node.implemented.io.TransformNode;
import gnode.GLink;
import gnode.GNode;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

public class MainController implements Initializable{
  @FXML
  private Pane nodeCanvas;

  @Override
  public void initialize(URL url, ResourceBundle rb){
    nodeCanvas.getChildren().add(new GNode(50, 50, 100, 100, new TransformNode(), nodeCanvas));
    nodeCanvas.getChildren().add(new GNode(50, 50, 100, 200, new MapGenNode(Vector2.MINUSTWO, Vector2.TWO), nodeCanvas));
    nodeCanvas.getChildren().add(new GNode(50, 50, 100, 300, new AnimationNode(), nodeCanvas));
    nodeCanvas.getChildren().add(new GNode(50, 50, 100, 300, new GradientNode(new HueCycleGradient()), nodeCanvas));

    RenderNode render = new BasicRenderNode();

    nodeCanvas.getChildren().add(new GNode(50, 50, 300, 100, render, nodeCanvas));

    Button b = new Button();
    b.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        FileManager.writeGif(render.render(100, 100, 60), "firstone");
      }
    });
    b.setMinWidth(50);
    b.setMinHeight(50);
    b.setText("Render");

    nodeCanvas.getChildren().add(b);

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

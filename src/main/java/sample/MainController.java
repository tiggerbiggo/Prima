package sample;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.FileManager;
import com.tiggerbiggo.primaplay.graphics.HueCycleGradient;
import com.tiggerbiggo.primaplay.graphics.ImageTools;
import com.tiggerbiggo.primaplay.node.core.RenderNode;
import com.tiggerbiggo.primaplay.node.implemented.BasicRenderNode;
import com.tiggerbiggo.primaplay.node.implemented.MapGenNode;
import com.tiggerbiggo.primaplay.node.implemented.io.AnimationNode;
import com.tiggerbiggo.primaplay.node.implemented.io.GradientNode;
import com.tiggerbiggo.primaplay.node.implemented.io.ImageListNode;
import com.tiggerbiggo.primaplay.node.implemented.io.KaliedoNode;
import com.tiggerbiggo.primaplay.node.implemented.io.TransformNode;
import com.tiggerbiggo.primaplay.node.implemented.io.iterative.MandelNode;
import gnode.GLink;
import gnode.GNode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class MainController implements Initializable {

  @FXML
  private Pane nodeCanvas;

  @FXML
  private ImageView imgView;

  Image[] imgArray = null;
  int currentImage;
  Timeline timer;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    nodeCanvas.getChildren().add(new GNode(new TransformNode(), nodeCanvas));
    nodeCanvas.getChildren().add(new GNode(new MapGenNode(Vector2.MINUSTWO, Vector2.TWO), nodeCanvas));
    nodeCanvas.getChildren().add(new GNode(new AnimationNode(), nodeCanvas));
    nodeCanvas.getChildren().add(new GNode(new GradientNode(new HueCycleGradient()), nodeCanvas));
    nodeCanvas.getChildren().add(new GNode(new MandelNode(300, 0.1), nodeCanvas));
    nodeCanvas.getChildren().add(new GNode(new KaliedoNode(6), nodeCanvas));
    nodeCanvas.getChildren().add(new GNode(new ImageListNode(FileManager.getImgsFromFolder("imgs")), nodeCanvas));

    RenderNode render = new BasicRenderNode();

    nodeCanvas.getChildren().add(new GNode(50, 50, 300, 100, render, nodeCanvas));

    Button b = new Button();
    b.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        imgArray = ImageTools.toFXImage(render.render(100, 100, 60));
        timer.play();
      }
    });

    timer = new Timeline(new KeyFrame(Duration.seconds(1.0 / 60),
        e -> {
          if (imgArray == null) {
            return;
          }
          currentImage++;
          if (currentImage >= imgArray.length) {
            currentImage = 0;
          }
          imgView.setImage(imgArray[currentImage]);
        }
    ));
    timer.setCycleCount(Animation.INDEFINITE);

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

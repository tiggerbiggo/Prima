package sample;

import com.sun.javafx.collections.ObservableListWrapper;
import com.tiggerbiggo.prima.primaplay.core.FileManager;
import com.tiggerbiggo.prima.primaplay.graphics.HueCycleGradient;
import com.tiggerbiggo.prima.primaplay.graphics.ImageTools;
import com.tiggerbiggo.prima.primaplay.node.core.INode;
import com.tiggerbiggo.prima.primaplay.node.core.RenderNode;
import com.tiggerbiggo.prima.primaplay.node.implemented.BasicRenderNode;
import com.tiggerbiggo.prima.primaplay.node.implemented.MapGenNode;
import com.tiggerbiggo.prima.primaplay.node.implemented.io.AnimationNode;
import com.tiggerbiggo.prima.primaplay.node.implemented.io.GradientNode;
import gnode.GLink;
import gnode.GNode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class MainController implements Initializable {

  @FXML
  private Pane nodeCanvas;
  @FXML
  private ImageView imgView;
  @FXML
  private Button btnPreview;
  @FXML
  private TextField txtFileName;
  @FXML
  private AnchorPane anchImageHolder;
  @FXML
  private Spinner<Integer> spnWidth, spnHeight;
  @FXML
  private ComboBox<Class<? extends INode>> comboNodeList;


  Image[] imgArray = null;
  int currentImage;
  Timeline timer;

  RenderNode render;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    nodeCanvas.getChildren().add(new GNode(new MapGenNode(), nodeCanvas));
    nodeCanvas.getChildren().add(new GNode(new AnimationNode(), nodeCanvas));
    nodeCanvas.getChildren().add(new GNode(new GradientNode(new HueCycleGradient()), nodeCanvas));
    render = new BasicRenderNode();
    nodeCanvas.getChildren().add(new GNode(50, 50, 300, 100, render, nodeCanvas));

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
    timer.setOnFinished(event -> imgView.setImage(null));

    nodeCanvas.setOnDragOver(event -> event.acceptTransferModes(TransferMode.ANY));
    nodeCanvas.setOnDragDropped(event -> {
      Object gestureSource = event.getGestureSource();
      if (gestureSource == null) {
        return;
      }
      if (gestureSource instanceof GLink) {
      }
    });

    spnWidth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 5000, 100, 50));
    spnHeight
        .setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 5000, 100, 50));

    comboNodeList.setItems(new ObservableListWrapper<>(NodeList.getAllImplementedNodes()));
    //imgView.fitWidthProperty().bind(anchImageHolder.widthProperty());
    //imgView.fitHeightProperty().bind(anchImageHolder.heightProperty());
  }

  @FXML
  private void onBtnPreview(ActionEvent e) {
    if (btnPreview.getText().equals("Preview")) {
      imgArray = ImageTools.toFXImage(render.render(100, 100, 60));
      timer.play();
      btnPreview.setText("Stop");
    } else {
      timer.stop();
      btnPreview.setText("Preview");
    }
  }

  @FXML
  private void onBtnSave(ActionEvent e) {
    FileManager.writeGif(render.render(spnWidth.getValue(), spnHeight.getValue(), 60),
        txtFileName.getText());
  }

  @FXML
  private void onBtnAddNode(ActionEvent e) {
    try {
      INode nodeInstance = comboNodeList.getValue().newInstance();
      nodeCanvas.getChildren().add(new GNode(nodeInstance, nodeCanvas));
    } catch (InstantiationException | IllegalAccessException ex) {
      ex.printStackTrace();
    }
  }
}

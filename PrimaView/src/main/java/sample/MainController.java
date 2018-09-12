package sample;

import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
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
import com.tiggerbiggo.prima.primaplay.node.implemented.io.ImageListNode;
import gnode.GLink;
import gnode.GUINode;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.StringConverter;

public class MainController implements Initializable, ChangeListener {

  @FXML
  public Pane nodeCanvas;
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
  @FXML
  private TextArea txtSavedText;


  Image[] imgArray = null;
  int currentImage;
  Timeline timer;

  RenderNode renderNode;

  List<GUINode> nodeList;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    nodeList = new ArrayList<>();

    renderNode = new BasicRenderNode();
    addNode(new GUINode(renderNode, nodeCanvas, this));
    //addNode(new GUINode(new ImageListNode(FileManager.getImgsFromFolder("imgs")), nodeCanvas, this));
    addNode(new GUINode(new MapGenNode(), nodeCanvas, this));
    addNode(new GUINode(new AnimationNode(), nodeCanvas, this));
    addNode(new GUINode(new GradientNode(new HueCycleGradient()), nodeCanvas, this));
    //nodeCanvas.getChildren().add(new GUINode(new GradientNode(new SimpleGradient(Color.RED, Color.BLUE, false)), nodeCanvas, this));


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
    timer.setOnFinished(e -> imgView.setImage(null));

    nodeCanvas.setOnDragOver(e -> e.acceptTransferModes(TransferMode.ANY));
    nodeCanvas.setOnDragDropped(e -> {
      Object gestureSource = e.getGestureSource();
      if (gestureSource == null) {
        return;
      }
      if (gestureSource instanceof GLink) {
      }
    });

    spnWidth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 5000, 100, 50));
    spnHeight
        .setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 5000, 100, 50));

    comboNodeList.setConverter(new StringConverter<Class<? extends INode>>() {
      @Override
      public String toString(Class<? extends INode> object) {
        try {
          return object.newInstance().getName();
        } catch (InstantiationException | IllegalAccessException e) {
        }
        return object.getName();
      }

      @Override
      public Class<? extends INode> fromString(String string) {
        return null;
      }
    });
    comboNodeList.setItems(new ObservableListWrapper<>(NodeReflection.getAllImplementedNodes()));
    //imgView.fitWidthProperty().bind(anchImageHolder.widthProperty());
    //imgView.fitHeightProperty().bind(anchImageHolder.heightProperty());
  }

  private void refreshImage(int width, int height, int n){
    imgArray = ImageTools.toFXImage(renderNode.render(width, height, n));
  }

  private void refreshImage(){
    refreshImage(100, 100, 60);
  }

  /**
   * Clears all nodes in the nodeList and all the objects in the nodeCanvas.
   */
  public void clearNodes(){
    nodeList.clear();
    nodeCanvas.getChildren().clear();
    //addNode(new GUINode(renderNode, nodeCanvas, this));
  }

  @FXML
  private void onBtnPreview(ActionEvent e) {
    if (btnPreview.getText().equals("Preview")) {
      refreshImage();
      timer.play();
      btnPreview.setText("Stop");
    } else {
      timer.stop();
      btnPreview.setText("Preview");
    }
  }

  @FXML
  private void onBtnSave(ActionEvent e) {
    FileManager.writeGif(renderNode.render(spnWidth.getValue(), spnHeight.getValue(), 60),
        txtFileName.getText());
  }

  @FXML
  private void onBtnSaveLayout(ActionEvent e){

    //purge deleted entries before save
    List<GUINode> toDelete = new ArrayList<>();
    for(GUINode node : nodeList){
      if(!nodeCanvas.getChildren().contains(node)){
        toDelete.add(node);
      }
    }
    nodeList.removeAll(toDelete);

    String ser = NodeSerializer.SerializeList(nodeList);
    System.out.println(ser);

    txtSavedText.setText(ser);

    StringSelection stringSelection = new StringSelection(ser);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, null);

    //NodeSerializer.parseNodes(ser, this);
  }

  @FXML
  private void onBtnLoadLayout(ActionEvent e){
    NodeSerializer.parseNodes(txtSavedText.getText(), this);
  }

  @FXML
  private void onBtnAddNode(ActionEvent e) {
    try {
      INode nodeInstance = comboNodeList.getValue().newInstance();
      GUINode newNode = new GUINode(nodeInstance, nodeCanvas, this);
      addNode(newNode);
    } catch (InstantiationException | IllegalAccessException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void onObjectValueChanged(Field field, Object object) {
    refreshImage();
  }

  private void addNode(GUINode node){
    nodeCanvas.getChildren().add(node);
    nodeList.add(node);
  }
}

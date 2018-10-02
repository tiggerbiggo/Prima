package sample;

import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.sun.javafx.collections.ObservableListWrapper;
import com.tiggerbiggo.prima.primaplay.core.FileManager;
import com.tiggerbiggo.prima.primaplay.graphics.ImageTools;
import com.tiggerbiggo.prima.primaplay.node.core.INode;
import com.tiggerbiggo.prima.primaplay.node.implemented.BasicRenderNode;
import guinode.GUILink;
import guinode.GUINode;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
import javafx.util.Duration;
import javafx.util.StringConverter;

public class MainController implements Initializable, ChangeListener {

  public NodePane nodePane;

  @FXML
  public AnchorPane nodeContainer;
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

  private String DEFAULT =
      "0@com.tiggerbiggo.prima.primaplay.node.implemented.MapGenNode@{\"aX\":0.0,\"aY\":0.0,\"dx\":1.0,\"dy\":1.0}@35@29\n"
      + "1@com.tiggerbiggo.prima.primaplay.node.implemented.io.TransformNode@{\"function\":\"SINSIN\"}@319@43\n"
      + "2@com.tiggerbiggo.prima.primaplay.node.implemented.BasicRenderNode@{}@925@92\n"
      + "3@com.tiggerbiggo.prima.primaplay.node.implemented.io.GradientNode@{}@752@82\n"
      + "4@com.tiggerbiggo.prima.primaplay.node.implemented.io.AnimationNode@{}@567@66\n"
      + "-\n"
      + "0@0@1@0\n"
      + "3@0@2@0\n"
      + "4@0@3@0\n"
      + "1@0@4@0\n";


  Image[] imgArray = null;
  int currentImage;
  Timeline timer;

  private Future<BufferedImage[]> renderTask;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    timer = new Timeline(new KeyFrame(Duration.seconds(1.0 / 60),
        e -> {
          imgArray = pollRenderer();
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

    nodePane = new NodePane(this);
    nodePane = NodeSerializer.parseNodes(DEFAULT, this);
    AnchorPane.setBottomAnchor(nodePane, 0.0);
    AnchorPane.setTopAnchor(nodePane, 0.0);
    AnchorPane.setLeftAnchor(nodePane, 0.0);
    AnchorPane.setRightAnchor(nodePane, 0.0);
    nodeContainer.getChildren().add(nodePane);

    nodePane.setOnDragOver(e -> e.acceptTransferModes(TransferMode.ANY));
    nodePane.setOnDragDropped(e -> {
      Object gestureSource = e.getGestureSource();
      if (gestureSource == null) {
        return;
      }
      if (gestureSource instanceof GUILink) {
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

  private Image[] pollRenderer(){
    if(renderTask == null) return imgArray;
    if(renderTask.isDone()) {
      try {
        Image[] toReturn = ImageTools.toFXImage(renderTask.get());
        renderTask = null;
        return toReturn;
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
        return imgArray;
      }
    }
    return imgArray;
  }

  public void startPreviewRender(){
    if(renderTask != null) renderTask.cancel(true);
    imgArray = null;
    renderTask = nodePane.renderAsync(100, 100, 60);
  }

  @FXML
  private void onBtnPreview(ActionEvent e) {
    if (btnPreview.getText().equals("Preview")) {
      startPreviewRender();
      timer.play();
      btnPreview.setText("Stop");
    } else {
      timer.stop();
      btnPreview.setText("Preview");
    }
  }

  @FXML
  private void onBtnSave(ActionEvent e) {
    FileManager.writeGif(nodePane.render(spnWidth.getValue(), spnHeight.getValue(), 60), txtFileName.getText());
  }

  @FXML
  private void onBtnSaveLayout(ActionEvent e){

    //purge deleted entries before save
    nodePane.purgeNodes();

    String ser = NodeSerializer.SerializeNodePane(nodePane);
    System.out.println(ser);

    txtSavedText.setText(ser);

    StringSelection stringSelection = new StringSelection(ser);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, null);

    //NodeSerializer.parseNodes(ser, this);
  }

  @FXML
  private void onBtnLoadLayout(ActionEvent e){
    try{
      NodePane tmpPane = NodeSerializer.parseNodes(txtSavedText.getText(), this);
      if(tmpPane != null){
        nodeContainer.getChildren().clear();
        nodeContainer.getChildren().add(tmpPane);
        nodePane.clearNodes();
        nodePane = tmpPane;
      }
    }
    catch(NodeParseException ex){
      //oh dear, display error message
    }
  }

  @FXML
  private void onBtnAddNode(ActionEvent e) {
    try {
      INode nodeInstance = comboNodeList.getValue().newInstance();
      //GUINode newNode = new GUINode(nodeInstance, nodePane, this);
      nodePane.addNode(nodeInstance);
    } catch (InstantiationException | IllegalAccessException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void onObjectValueChanged(Field field, Object object) {
    startPreviewRender();
  }
}

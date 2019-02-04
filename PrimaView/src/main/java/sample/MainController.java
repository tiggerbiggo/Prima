package sample;

import ch.hephaistos.utilities.loki.ReflectorGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.sun.javafx.collections.ObservableListWrapper;
import com.tiggerbiggo.prima.primaplay.core.FileManager;
import com.tiggerbiggo.prima.primaplay.core.render.RenderCallback;
import com.tiggerbiggo.prima.primaplay.graphics.ImageTools;
import com.tiggerbiggo.prima.primaplay.graphics.SafeImage;
import com.tiggerbiggo.prima.primaplay.node.core.INode;
import com.tiggerbiggo.utils.calculation.ReflectionHelper;
import guinode.GUILink;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javafx.util.StringConverter;

public class MainController implements Initializable, ChangeListener {

  public NodePane nodePane;

  public static MainController thisController;

  @FXML
  public Pane nodeContainer;
  @FXML
  private ImageView imgView;
  @FXML
  private Button btnPreview;
  @FXML
  private ComboBox<Class<? extends INode>> comboNodeList;
  @FXML
  private BorderPane pannableContainer;
  @FXML
  private ScrollPane scrollPane;
  @FXML
  private MenuBar menuBar;
  @FXML
  private VBox mainBox;

  private String DEFAULT = "0@com.tiggerbiggo.prima.primaplay.node.implemented.MapGenNode@{\"aX\":0.0,\"aY\":0.0,\"dx\":1.0,\"dy\":1.0}@35@29\n"
      + "1@com.tiggerbiggo.prima.primaplay.node.implemented.io.TransformNode@{\"function\":\"SINSIN\"}@326@99\n"
      + "2@com.tiggerbiggo.prima.primaplay.node.implemented.BasicRenderNode@{}@963@111\n"
      + "3@com.tiggerbiggo.prima.primaplay.node.implemented.io.GradientNode@{}@770@81\n"
      + "4@com.tiggerbiggo.prima.primaplay.node.implemented.io.AnimationNode@{}@599@53\n"
      + "5@com.tiggerbiggo.prima.primaplay.node.implemented.output.ColorNode@{\"r\":77,\"g\":128,\"b\":77}@418@405\n"
      + "6@com.tiggerbiggo.prima.primaplay.node.implemented.output.ColorNode@{\"r\":255,\"g\":255,\"b\":102}@428@268\n"
      + "-\n"
      + "0@0@1@0\n"
      + "3@0@2@0\n"
      + "4@0@3@0\n"
      + "6@0@3@1\n"
      + "5@0@3@2\n"
      + "1@0@4@0\n";


  Image[] imgArray = null;
  int currentImage;
  Timeline timer;

  File currentFile = null;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    thisController = this;


    new ReflectorGrid().addChangeListener(this);

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


    Label progressLabel = new Label();
    RenderBar renderBar = new RenderBar(progressLabel);
    mainBox.getChildren().add(new HBox(renderBar, progressLabel));

    mainBox.getStyleClass().add("NodePane");

    pannableContainer.minWidthProperty().bind(scrollPane.widthProperty());
    pannableContainer.minHeightProperty().bind(scrollPane.heightProperty());

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

    setupMenuBar();
  }

  public void startPreviewRender(){
    imgArray = null;
    nodePane.renderAsync(100, 100, 60, "Preview render", new RenderCallback() {
      @Override
      public void callback(SafeImage[] imgs) {
        imgArray = ImageTools.toFXImage(imgs);
      }
    });
  }

  private void setCurrentFile(File f){
    currentFile = f;
    ViewMain.setTitleToFile(f);
  }

  @FXML
  private void onBtnPreview() {
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
  private void onExportPressed(){
    ViewMain.getExportStage().showAndWait();
  }

  private void saveLayout(){
    try{
      File chosen;

      if(currentFile == null)
        chosen =  FileManager.showSaveDialogue();
      else
        chosen = currentFile;

      NodeSerializer.saveToFile(nodePane, chosen);

      setCurrentFile(chosen);
    }catch(IOException ex){
      ex.printStackTrace();
    }
  }

  private void loadLayout(){
    try{
      File chosen = FileManager.showOpenDialogue();

      NodePane tmpPane = NodeSerializer.parseFromFile(chosen, this);
      //Open file dialogue

      //NodeSerializer.parseNodes(txtSavedText.getText(), this);
      nodeContainer.getChildren().clear();
      nodePane.clearNodes();
      nodePane = tmpPane;

      AnchorPane.setBottomAnchor(nodePane, 0.0);
      AnchorPane.setTopAnchor(nodePane, 0.0);
      AnchorPane.setLeftAnchor(nodePane, 0.0);
      AnchorPane.setRightAnchor(nodePane, 0.0);

      nodeContainer.getChildren().add(nodePane);

      setCurrentFile(chosen);

      startPreviewRender();
      timer.stop();
      timer.play();


    }
    catch(NodeParseException | IOException ex){
      ex.printStackTrace();
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
  public void onObjectValueChanged(Field field, Object oldValue, Object newValue, Object object) {
    startPreviewRender();
  }
  private void setupMenuBar(){
    Menu file = new Menu("File");

    MenuItem save, saveAs, open, export;

    save = new MenuItem("Save");
    save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
    save.setOnAction(event -> saveLayout());

    saveAs = new MenuItem("Save As...");
    saveAs.setAccelerator(
        new KeyCodeCombination(
            KeyCode.S,
            KeyCombination.SHORTCUT_DOWN,
            KeyCombination.SHIFT_DOWN));
    saveAs.setOnAction(event -> {
      currentFile = null;
      saveLayout();
    });

    open = new MenuItem("Open...");
    open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));
    open.setOnAction(event -> loadLayout());

    export = new MenuItem("Export...");
    export.setOnAction(event -> ViewMain.getExportStage().showAndWait());

    file.getItems().addAll(save, saveAs, open, export);

    menuBar.getMenus().addAll(file);
  }

  public NodePane getNodePane() {
    return nodePane;
  }
}

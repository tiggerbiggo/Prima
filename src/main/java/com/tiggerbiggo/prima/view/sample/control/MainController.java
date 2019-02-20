package com.tiggerbiggo.prima.view.sample.control;

import ch.hephaistos.utilities.loki.ReflectorGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.sun.javafx.collections.ObservableListWrapper;
import com.tiggerbiggo.prima.play.core.FileManager;
import com.tiggerbiggo.prima.play.core.render.RenderCallback;
import com.tiggerbiggo.prima.play.graphics.ImageTools;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.core.INode;
import com.tiggerbiggo.prima.play.node.implemented.BasicRenderNode;
import com.tiggerbiggo.prima.view.guinode.GUILink;
import com.tiggerbiggo.prima.view.guinode.GUINode;
import com.tiggerbiggo.prima.view.sample.NodePane;
import com.tiggerbiggo.prima.view.sample.NodeParseException;
import com.tiggerbiggo.prima.view.sample.NodeReflection;
import com.tiggerbiggo.prima.view.sample.NodeSerializer;
import com.tiggerbiggo.prima.view.sample.RenderBar;
import com.tiggerbiggo.prima.view.sample.ViewMain;
import com.tiggerbiggo.prima.view.sample.components.AnimatedImageView;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class MainController implements Initializable, ChangeListener {

  public NodePane nodePane;

  public static MainController thisController;

  @FXML
  public Pane nodeContainer;
  @FXML
  private BorderPane pannableContainer;
  @FXML
  private ScrollPane scrollPane;
  @FXML
  private MenuBar menuBar;
  @FXML
  private VBox mainBox;

  private String DEFAULT = "0@com.tiggerbiggo.prima.play.node.implemented.output.MapGenNode@{\"aX\":0.0,\"aY\":0.0,\"dx\":1.0,\"dy\":1.0}@35@29\n"
      + "1@com.tiggerbiggo.prima.play.node.implemented.io.TransformNode@{\"function\":\"SINSIN\"}@326@99\n"
      + "2@com.tiggerbiggo.prima.play.node.implemented.BasicRenderNode@{}@963@111\n"
      + "3@com.tiggerbiggo.prima.play.node.implemented.io.GradientNode@{}@770@81\n"
      + "4@com.tiggerbiggo.prima.play.node.implemented.io.AnimationNode@{}@599@53\n"
      + "5@com.tiggerbiggo.prima.play.node.implemented.output.ColorNode@{\"r\":77,\"g\":128,\"b\":77}@418@405\n"
      + "6@com.tiggerbiggo.prima.play.node.implemented.output.ColorNode@{\"r\":255,\"g\":255,\"b\":102}@428@268\n"
      + "-\n"
      + "0@0@1@0\n"
      + "3@0@2@0\n"
      + "4@0@3@0\n"
      + "6@0@3@1\n"
      + "5@0@3@2\n"
      + "1@0@4@0\n";

  File currentFile = null;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    thisController = this;

    new ReflectorGrid().addChangeListener(this);

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

    setupMenuBar();
  }

  private void setCurrentFile(File f){
    currentFile = f;
    ViewMain.setTitleToFile(f);
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

  private void resetLayout(){
    resetLayout(new NodePane(this));
  }

  private void resetLayout(NodePane newPane) {
    if(newPane == null) return;
    nodeContainer.getChildren().clear();
    nodePane.clearNodes();
    nodePane = newPane;

    AnchorPane.setBottomAnchor(nodePane, 0.0);
    AnchorPane.setTopAnchor(nodePane, 0.0);
    AnchorPane.setLeftAnchor(nodePane, 0.0);
    AnchorPane.setRightAnchor(nodePane, 0.0);

    nodeContainer.getChildren().add(nodePane);
  }

  private void loadLayout(){
    try{
      //Open file dialogue
      File chosen = FileManager.showOpenDialogue();

      //NodeSerializer.parseNodes(txtSavedText.getText(), this);
      resetLayout(NodeSerializer.parseFromFile(chosen, this));

      setCurrentFile(chosen);
    }
    catch(NodeParseException | IOException ex){
      Alert a = new Alert(AlertType.ERROR, "Error loading file:\n\n" + ex.getMessage() + "\n\n" + ex.getCause(), ButtonType.OK);
      a.showAndWait();
      ex.printStackTrace();
    }
  }

  private void setupMenuBar(){
    //FILE MENU
    Menu file = new Menu("File");

    MenuItem newPane, save, saveAs, open, export;

    newPane = new MenuItem("New");
    newPane.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
    newPane.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Alert a = new Alert(AlertType.WARNING, "Are you sure? All changes will be lost!",
            ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = a.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.YES)) {
          resetLayout();
          nodePane.addNode(
              new GUINode(200, 200, new BasicRenderNode(), nodePane, MainController.this));

        }
      }
    });

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
    open.setOnAction(event -> {
      loadLayout();
    });

    export = new MenuItem("Export...");
    export.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN));
    export.setOnAction(event -> ViewMain.getExportStage().showAndWait());

    file.getItems().addAll(newPane, save, saveAs, open, export);


    //TOOLS MENU
    Menu tools = new Menu("Tools");

    MenuItem snapshot;

    snapshot = new MenuItem("Take snapshot...");

    snapshot.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          File f = FileManager.showSaveDialogue(ViewMain.getMainStage(), FileManager.IMGS);

          if(f != null){
            Image snap = nodePane.snapshot(null, null);
            if(snap != null){
              FileManager.writeImage(SwingFXUtils.fromFXImage(snap, null), f);
              Alert a = new Alert(AlertType.CONFIRMATION, "Snapshot exported.", ButtonType.OK);
              a.showAndWait();
            }
          }
        }
        catch(IOException ex){
          Alert a = new Alert(AlertType.ERROR, "Error while taking snapshot: " + ex.getMessage(), ButtonType.OK);
        }
      }
    });

    tools.getItems().addAll(snapshot);

    //VIEW

    Menu view = new Menu("View");

    MenuItem preview = new MenuItem("Preview Window");
    preview.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN));
    preview.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        ViewMain.getPreviewStage().show();
      }
    });

    view.getItems().addAll(preview);

    menuBar.getMenus().addAll(file, view, tools);
  }

  public NodePane getNodePane() {
    return nodePane;
  }

  @Override
  public void onObjectValueChanged(Field field, Object oldValue, Object newValue, Object object) {

  }
}

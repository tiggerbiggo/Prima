package com.tiggerbiggo.prima.view.sample.control;

import ch.hephaistos.utilities.loki.ReflectorGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.FileManager;
import com.tiggerbiggo.prima.play.core.calculation.GUITools;
import com.tiggerbiggo.prima.play.core.render.RenderCallback;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.core.INode;
import com.tiggerbiggo.prima.play.node.core.INodeHasOutput;
import com.tiggerbiggo.prima.play.node.core.NodeHasOutput;
import com.tiggerbiggo.prima.play.node.implemented.BasicRenderNode;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import com.tiggerbiggo.prima.view.guinode.GUINode;
import com.tiggerbiggo.prima.view.sample.NodePane;
import com.tiggerbiggo.prima.view.sample.NodeParseException;
import com.tiggerbiggo.prima.view.sample.NodeSerializer;
import com.tiggerbiggo.prima.view.sample.ViewMain;
import com.tiggerbiggo.prima.view.sample.components.AnimatedImageView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class MainController implements Initializable, ChangeListener {

  public NodePane nodePane;

  public static MainController thisController;

  @FXML
  public Pane nodeContainer;
  @FXML
  private AnchorPane previewPane;
  @FXML
  private AnchorPane pannableContainer;
  @FXML
  private ScrollPane scrollPane;
  @FXML
  private MenuBar menuBar;
  @FXML
  private VBox mainBox;

  private AnimatedImageView view;

  File currentFile = null;

  private ArrayDeque<String> undoQueue, redoQueue;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    thisController = this;

    new ReflectorGrid().addChangeListener(this);

    undoQueue = new ArrayDeque<>();
    redoQueue = new ArrayDeque<>();

    //Label progressLabel = new Label();
    //RenderBar renderBar = new RenderBar(progressLabel);
    //mainBox.getChildren().add(new HBox(renderBar, progressLabel));

    mainBox.getStyleClass().add("NodePane");

    pannableContainer.minWidthProperty().bind(scrollPane.widthProperty());
    pannableContainer.minHeightProperty().bind(scrollPane.heightProperty());

    resetLayout();

//    nodePane.setOnDragOver(e -> e.acceptTransferModes(TransferMode.ANY));
//    nodePane.setOnDragDropped(e -> {
//      Object gestureSource = e.getGestureSource();
//      if (gestureSource == null) {
//        return;
//      }
//      if (gestureSource instanceof GUILink) {
//      }
//    });

    view = new AnimatedImageView();
    previewPane.getChildren().add(view);

    previewPane.setOnMouseClicked(e -> {
      nodePane.renderAsync(200, 200, 60, "Preview Render", new RenderCallback() {
        @Override
        public void callback(SafeImage[] imgs) {
          view.setImgs(imgs);
          view.start();
        }
      });
    });

    view.setImgs(new SafeImage[]{new SafeImage(200, 200)});
    view.start();

    previewPane.toFront();

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
    if(nodePane == null) nodePane = new NodePane(this);

    NodePane tmp = new NodePane(this);
    tmp.addNode(new GUINode(200, 200, new BasicRenderNode(), tmp, MainController.this));
    resetLayout(tmp);
  }

  private void resetLayout(NodePane newPane) {
    if(newPane == null) return;
    nodeContainer.getChildren().clear();
    nodePane.clearNodes();
    nodePane = newPane;

    GUITools.setAllAnchors(nodePane, 0.0);

    nodeContainer.getChildren().add(nodePane);

    redoQueue.clear();
    undoQueue.clear();
    undoQueue.addFirst(NodeSerializer.SerializeNodePane(nodePane));
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

    //EDIT MENU
    Menu edit = new Menu("Menu");

    MenuItem undo, redo, selectAll;

    undo = new MenuItem("Undo");
    undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN));
    undo.setOnAction(e -> undo());

    redo = new MenuItem("Redo");
    redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN));
    redo.setOnAction(e -> redo());

    selectAll = new MenuItem("Select All");
    selectAll.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));
    selectAll.setOnAction(e -> nodePane.selectAll());

    edit.getItems().addAll(undo, redo, selectAll);

    //TOOLS MENU
    Menu tools = new Menu("Tools");

    MenuItem snapshot, glslExport;

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

    glslExport = new MenuItem("Export GLSL String");

    glslExport.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try{
          /*File f = FileManager.showSaveDialogue(ViewMain.getMainStage(), FileManager.TXT);
          if(f != null){
            FileWriter fw = new FileWriter(f);

            fw.write(generateGlslString(nodePane));
          }*/
          System.out.println(generateGlslString(nodePane));
        }
        catch (/*IOException*/ NullPointerException | NotImplementedException ex){
          Alert a = new Alert(AlertType.ERROR, "Error while generating code: " + ex.toString(), ButtonType.OK);
          a.showAndWait();
        }
      }
    });

    tools.getItems().addAll(snapshot, glslExport);

    //VIEW

    Menu view = new Menu("View");

    MenuItem preview = new MenuItem("Preview Window");
    preview.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN));
    preview.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        ViewMain.getPreviewStage().show();
        ViewMain.getPreviewStage().requestFocus();
      }
    });

    view.getItems().addAll(preview);

    menuBar.getMenus().addAll(file, edit, view, tools);
  }

  public NodePane getNodePane() {
    return nodePane;
  }

  @Override
  public void onObjectValueChanged(Field field, Object oldValue, Object newValue, Object object) {

    redoQueue.clear();
    undoQueue.addFirst(NodeSerializer.SerializeNodePane(nodePane));
    System.out.println("UNDO QUEUE LENGTH: " + undoQueue.size());
  }

  @FXML
  private void onMouseEnterPreview(){
    //TODO: show stuff
  }

  private void undo(){
    try{
      String s = undoQueue.remove();

      resetLayout(NodeSerializer.parseNodes(s, this));
      redoQueue.addFirst(s);
    }
    catch(NoSuchElementException ex){
      //no undo elements
    }
  }

  private void redo(){
    try{
      String s = redoQueue.remove();

      resetLayout(NodeSerializer.parseNodes(s, this));
      undoQueue.addFirst(s);
    }
    catch(NoSuchElementException ex){
      //no undo elements
    }
  }

  public String generateGlslString(NodePane p){
    StringBuilder build = new StringBuilder();


    List<OutputLink<?>> allOutputs = new ArrayList<>();

    for(GUINode gN : p.getReadOnlyNodeList()){
      INode n = gN.getNode();
      if(n instanceof INodeHasOutput){
        OutputLink<?>[] tmpLinks = ((INodeHasOutput)n).getOutputs();
        allOutputs.addAll(Arrays.asList(tmpLinks));
      }
    }

    for(OutputLink<?> link : allOutputs){
      build.append(link.getReturnType()+ " " + link.getMethodName()+";\n");
    }

    build.append("vec2 localUV = vec2(0,0);\n");
    build.append("float localTime = 0.;\n");

    build.append("void mainImage(out vec4 fragColor, in vec2 fragCoord){\n");

    build.append("  localUV = fragCoord/iResolution.xy;\n");
    build.append("  localTime = iTime;\n");

    build.append("  fragColor = " + p.getRenderNode().getInput().getMethodName()+";\n}");

    for(OutputLink<?> link : allOutputs) {
      link.generateGLSLMethod(build);
    }


    return build.toString();
  }
}

package sample;

import com.tiggerbiggo.prima.primaplay.core.FileManager;
import com.tiggerbiggo.prima.primaplay.node.implemented.BasicRenderNode;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser.ExtensionFilter;

public class ExportController implements Initializable {

  ExtensionFilter currentFilter = FileManager.GIF;

  ExportController thisController;

  @FXML
  RadioButton GIF, IMG;
  @FXML
  TextField filename;
  @FXML
  Slider frameNumSlider;
  @FXML
  Label frameNumLabel;
  @FXML
  Slider fpsSlider;
  @FXML
  Label fpsLabel;
  @FXML
  Button exportButton;
  @FXML
  Spinner<Integer> widthSpinner, heightSpinner;

  BasicRenderNode currentRender;

  File currentFile;

  int lastSelected = 0;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    thisController = this;
    exportButton.setDisable(true);

    frameNumSlider.valueProperty().addListener(
        o -> frameNumLabel.setText((int)frameNumSlider.getValue() + "")
    );

    fpsSlider.valueProperty().addListener(
        o -> fpsLabel.setText((int)fpsSlider.getValue() + "")
    );

    GIF.setOnMouseClicked(event -> {
      if(lastSelected != 0){
        //We have been newly selected, clear file box
        filename.setText("");
      }
      lastSelected = 0;
      exportButton.setDisable(true);
    });

    IMG.setOnMouseClicked(event -> {
      if(lastSelected != 1){
        //We have been newly selected, clear file box
        filename.setText("");
      }
      lastSelected = 1;
      exportButton.setDisable(true);
    });

    widthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 8000, 100, 100));
    heightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 8000, 100, 100));
  }

  public void doFileOpen(ExtensionFilter ... filters){
    try {
      currentFile = FileManager.showSaveDialogue(ViewMain.getExportStage(), "exports/", filters);

      filename.setText(currentFile.toString());
      exportButton.setDisable(false);
    }
    catch(IOException e){

      exportButton.setDisable(true);
    }
  }

  @FXML
  public void onFileOpen(){
    if (GIF.isSelected())
      doFileOpen(FileManager.GIF);
    else
      doFileOpen(FileManager.IMGS);
  }

  public void onExport(){
    currentRender = MainController.thisController.getNodePane().renderNode;
    if(currentRender == null){
      //Error
      return;
    }
    if(GIF.isSelected()){
      //export as GIF
      FileManager.writeGif(currentRender.render(widthSpinner.getValue(), heightSpinner.getValue(), (int)frameNumSlider.getValue()), currentFile);
    }
  }
}

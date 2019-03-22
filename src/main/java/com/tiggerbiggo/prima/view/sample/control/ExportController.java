package com.tiggerbiggo.prima.view.sample.control;

import com.tiggerbiggo.prima.play.core.FileManager;
import com.tiggerbiggo.prima.play.core.render.RenderCallback;
import com.tiggerbiggo.prima.play.graphics.ImageTools;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.implemented.BasicRenderNode;
import com.tiggerbiggo.prima.view.sample.ViewMain;
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

  @FXML
  RadioButton GIF, MP4, IMG;
  @FXML
  TextField filename;
  @FXML
  Slider frameNumSlider;
  @FXML
  Label frameNumLabel;
  @FXML
  Slider loopNumSlider;
  @FXML
  Label loopNumLabel;
  @FXML
  Button exportButton, mulResButton, divResButton;
  @FXML
  Spinner<Integer> widthSpinner, heightSpinner;

  BasicRenderNode currentRender;

  File currentFile;

  int lastSelected = 0;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    exportButton.setDisable(true);

    frameNumSlider.valueProperty().addListener(
        o -> frameNumLabel.setText((int)frameNumSlider.getValue() + "")
    );

    loopNumSlider.valueProperty().addListener(
        o -> loopNumLabel.setText((int)loopNumSlider.getValue() + "")
    );

    mulResButton.setOnAction(event -> {
      widthSpinner.getValueFactory().setValue(widthSpinner.getValue()*2);
      heightSpinner.getValueFactory().setValue(heightSpinner.getValue()*2);
    });

    divResButton.setOnAction(event -> {
      widthSpinner.getValueFactory().setValue(widthSpinner.getValue()/2);
      heightSpinner.getValueFactory().setValue(heightSpinner.getValue()/2);
    });

    GIF.setOnMouseClicked(e -> doSelectionLogic(0));
    MP4.setOnMouseClicked(e -> doSelectionLogic(1));
    IMG.setOnMouseClicked(e -> doSelectionLogic(2));

    widthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 8000, 100, 100));
    heightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 8000, 100, 100));
  }

  private void doSelectionLogic(int num){
    if(lastSelected != num){
      //We have been newly selected, clear file box
      filename.setText("");
    }
    lastSelected = num;
    exportButton.setDisable(true);
  }

  public void doFileOpen(ExtensionFilter ... filters){
    try {
      currentFile = FileManager.showSaveDialogue(ViewMain.getExportStage(), filters);

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
    else if(MP4.isSelected())
      doFileOpen(FileManager.MP4);
    else
      doFileOpen(FileManager.IMGS);
  }

  public void onExport(){
    currentRender = MainController.thisController.getNodePane().getRenderNode();
    if(currentRender == null){
      //Error
      return;
    }
    if(GIF.isSelected()){
      currentRender.renderAsync(
          widthSpinner.getValue(),
          heightSpinner.getValue(),
          (int) frameNumSlider.getValue(),
          "Rendering and Exporting GIF. Filename: " + currentFile.getName(),
          new RenderCallback() {
            File f = currentFile;
            @Override
            public void callback(SafeImage[] imgs) {
              FileManager.writeGif(ImageTools.toBufferedImage(imgs), f);
            }
          });
    }
    else if(MP4.isSelected()){
      currentRender.renderAsync(
          widthSpinner.getValue(),
          heightSpinner.getValue(),
          (int) frameNumSlider.getValue(),
          "Rendering and exporting MP4. Filename: " + currentFile.getName(),
          new RenderCallback() {
            File f = currentFile;
            @Override
            public void callback(SafeImage[] imgs) {
              FileManager.writeVideo(ImageTools.toBufferedImage(imgs), f, (int)loopNumSlider.getValue());
            }
          });
    }
    ViewMain.getExportStage().close();
  }
}

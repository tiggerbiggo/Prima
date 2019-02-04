package sample;

import com.tiggerbiggo.prima.primaplay.core.FileManager;
import com.tiggerbiggo.prima.primaplay.core.render.RenderCallback;
import com.tiggerbiggo.prima.primaplay.graphics.ImageTools;
import com.tiggerbiggo.prima.primaplay.graphics.SafeImage;
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

  ExportController thisController;

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

    loopNumSlider.valueProperty().addListener(
        o -> loopNumLabel.setText((int)loopNumSlider.getValue() + "")
    );

    GIF.setOnMouseClicked(event -> {
      if(lastSelected != 0){
        //We have been newly selected, clear file box
        filename.setText("");
      }
      lastSelected = 0;
      exportButton.setDisable(true);
    });

    MP4.setOnMouseClicked(event -> {
      if(lastSelected != 1){
        //We have been newly selected, clear file box
        filename.setText("");
      }
      lastSelected = 1;
      exportButton.setDisable(true);
    });

    IMG.setOnMouseClicked(event -> {
      if(lastSelected != 2){
        //We have been newly selected, clear file box
        filename.setText("");
      }
      lastSelected = 2;
      exportButton.setDisable(true);
    });

    widthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 8000, 100, 100));
    heightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 8000, 100, 100));
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
    currentRender = MainController.thisController.getNodePane().renderNode;
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

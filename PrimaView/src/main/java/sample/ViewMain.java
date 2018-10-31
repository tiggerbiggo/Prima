package sample;

import java.io.File;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewMain extends Application {

  private static Stage mainStage;
  private static Stage exportStage;

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/mainWindow.fxml"));
    mainStage = primaryStage;

    setTitleToFile(null);

    Scene s = new Scene(root, 600, 500);
    s.getStylesheets().add(getClass().getResource("/default.css").toString());
    primaryStage.setOnCloseRequest(event -> System.exit(0));
    primaryStage.setScene(s);

    root = FXMLLoader.load(getClass().getResource("/exportWindow.fxml"));
    exportStage = new Stage();
    s = new Scene(root, 500, 500);
    s.getStylesheets().add(getClass().getResource("/default.css").toString());
    exportStage.setScene(s);
    exportStage.initModality(Modality.APPLICATION_MODAL);
    primaryStage.setOnCloseRequest(event -> {
      exitPopup();
      event.consume();
    });

    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  public static void setTitleToFile(File f) {
    if (f != null) {
      mainStage.setTitle("Prima: " + f.getName());
    } else{
      mainStage.setTitle("Prima: No File Loaded");
    }
  }

  void exitPopup(){
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setContentText("Are you sure you want to exit Prima?");
    alert.setTitle("Exit?");
    Optional<ButtonType> res = alert.showAndWait();
    if(res.get().equals(ButtonType.OK))
      System.exit(0);
  }

  public static Stage getMainStage(){
    return mainStage;
  }
  public static Stage getExportStage(){return exportStage;}
}

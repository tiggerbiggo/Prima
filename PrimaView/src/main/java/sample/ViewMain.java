package sample;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

  public static Stage getMainStage(){
    return mainStage;
  }
  public static Stage getExportStage(){return exportStage;}
}

package sample;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewMain extends Application {

  private static Stage mainStage;

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
    primaryStage.setTitle("Prima: No file Loaded");
    mainStage = primaryStage;

    Scene s = new Scene(root, 600, 500);
    s.getStylesheets().add(getClass().getResource("/default.css").toString());

    primaryStage.setOnCloseRequest(event -> System.exit(0));

    primaryStage.setScene(s);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  public static void setTitleToFile(File f){
    if(f != null){
      mainStage.setTitle("Prima: " + f.getName());
    }
  }
}

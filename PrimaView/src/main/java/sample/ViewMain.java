package sample;

import java.io.File;
import java.net.URLDecoder;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

      @Override
      public void handle(WindowEvent event) {
        exitPopup();
        event.consume();
      }
    });

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

  /** pops up an alert to confirm the exit*/
  void exitPopup(){
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setContentText("Are you sure you want to exit prima?");
    alert.setTitle("exit prima?");
    Optional<ButtonType> res = alert.showAndWait();
    if(res.get().equals(ButtonType.OK))
      System.exit(0);
  }

  public static Stage getMainStage(){
    return mainStage;
  }
}

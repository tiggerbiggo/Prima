package com.tiggerbiggo.prima.view.sample;

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
  private static Stage previewStage;

  private static final String CSS_STRING = ViewMain.class.getResource("/default.css").toString();

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/mainWindow.fxml"));
    mainStage = primaryStage;

    setTitleToFile(null);
    Scene s = new Scene(root, 600, 500);
    s.getStylesheets().add(CSS_STRING);
    primaryStage.setOnCloseRequest(event -> System.exit(0));
    primaryStage.setScene(s);
    primaryStage.setOnCloseRequest(event -> {
      exitPopup();
      event.consume();
    });

    root = FXMLLoader.load(getClass().getResource("/exportWindow.fxml"));
    exportStage = new Stage();
    s = new Scene(root, 500, 500);
    s.getStylesheets().add(CSS_STRING);
    exportStage.setScene(s);
    exportStage.initModality(Modality.APPLICATION_MODAL);


    root = FXMLLoader.load(getClass().getResource("/bigPreviewWindow.fxml"));
    previewStage = new Stage();
    s = new Scene(root, 500, 500);
    s.getStylesheets().add(CSS_STRING);
    previewStage.setScene(s);

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

  private void exitPopup(){
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setContentText("Are you sure you want to exit Prima?");
    alert.setTitle("Exit?");
    Optional<ButtonType> res = alert.showAndWait();
    if(res.isPresent() && res.get().equals(ButtonType.OK))
      System.exit(0);
  }

  public static Stage getMainStage(){return mainStage;}
  public static Stage getExportStage(){return exportStage;}
  public static Stage getPreviewStage(){return previewStage;}
}
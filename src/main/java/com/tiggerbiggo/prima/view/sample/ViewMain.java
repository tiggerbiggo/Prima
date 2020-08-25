package com.tiggerbiggo.prima.view.sample;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Optional;

import com.tiggerbiggo.prima.play.core.FileManager;
import com.tiggerbiggo.prima.play.graphics.ImageTools;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.view.sample.control.flow.FlowController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;

public class ViewMain extends Application {

  private static Stage mainStage;
  private static Stage exportStage;
  private static Stage previewStage;
  private static Stage pointStage;
  private static Stage flowStage;

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
//    exportStage.initModality(Modality.APPLICATION_MODAL);


    root = FXMLLoader.load(getClass().getResource("/bigPreviewWindow.fxml"));
    previewStage = new Stage();
    s = new Scene(root, 500, 500);
    s.getStylesheets().add(CSS_STRING);
    previewStage.setScene(s);

    root = FXMLLoader.load(getClass().getResource("/pointWindow.fxml"));
    pointStage = new Stage();
    s = new Scene(root, 500, 500);
    s.getStylesheets().add(CSS_STRING);
    pointStage.setScene(s);

    root = FXMLLoader.load(getClass().getResource("/flowWindow.fxml"));
    flowStage = new Stage();
    s = new Scene(root, 500, 500);
    s.getStylesheets().add(CSS_STRING);
    flowStage.setScene(s);

    primaryStage.show();
    //flowStage.show();
    //pointStage.show();

    hackTooltipStartTiming();
  }

  private static void hackTooltipStartTiming() {
    Tooltip tooltip = new Tooltip("");
    try {
      Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
      fieldBehavior.setAccessible(true);
      Object objBehavior = fieldBehavior.get(tooltip);

      Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
      fieldTimer.setAccessible(true);
      Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

      objTimer.getKeyFrames().clear();
      objTimer.getKeyFrames().add(new KeyFrame(new Duration(200)));
    } catch (Exception e) {
      e.printStackTrace();
    }
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
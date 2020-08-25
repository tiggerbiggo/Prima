package com.tiggerbiggo.prima.view.sample.control.flow;

import com.tiggerbiggo.prima.play.core.FileManager;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


import javafx.scene.control.Label;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FlowController implements Initializable {

    @FXML
    ImageView imageView;

    @FXML
    private Button executeButton;

    private int w = 1;
    private int h = 1;

    private SafeImage seedImage;
    private SafeImage maskImage;

    @FXML
    private Label widthLabel, heightLabel;

    private static final String widthString = "Width: ";
    private static final String heightString = "Height: ";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        blankImages();
        updateLabels();
    }

    private void blankImages(){
        seedImage = null;
        maskImage = null;
    }

    private void updateLabels(){
        widthLabel.setText(widthString + w);
        heightLabel.setText(heightString + h);
    }

    @FXML
    private void loadSeed(){
        try {
            File f = FileManager.showOpenDialogue(FileManager.IMGS);
            SafeImage img = new SafeImage(ImageIO.read(f));
            if(maskImage == null || areSizesEqual(img, maskImage)){
                seedImage = img;
            }
        }
        catch(IOException e){
            //user declined
        }
    }

    @FXML
    private void loadMask(){
        try {
            File f = FileManager.showOpenDialogue(FileManager.IMGS);
            SafeImage img = new SafeImage(ImageIO.read(f));
            if(seedImage == null || areSizesEqual(img, seedImage)){
                maskImage = img;
            }
        }
        catch(IOException e){
            //user declined
        }
    }




    private boolean areSizesEqual(SafeImage A, SafeImage B){
        if(A == null || B == null) return false;

        return (A.getWidth() == B.getWidth()) && (A.getHeight() == B.getHeight());
    }

//  for(int i=0; i<w; i++){
//    for(int j=0; j<h; j++){
//
//    }
//  }



    private static int getLowestSurroundingColorDifference(SafeImage img, Color c, int x, int y) {

        int w = img.getWidth();

        int h = img.getHeight();


        int n = Integer.MAX_VALUE;


        for (int i = x - 1; i <= x + 1; i++) {

            for (int j = y - 1; j <= y + 1; j++) {

                if (i < 0 || j < 0 || i >= w || j >= h) {

                    continue;

                    //invalid

                }

                if (i == x && j == y) {

                    continue;

                }

                n = Math.min(n, ColorTools.absoluteDifference(c, img.getColor(i, j)));

            }

        }

        return n;
    }

}
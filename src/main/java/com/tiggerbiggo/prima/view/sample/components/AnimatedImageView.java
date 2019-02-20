package com.tiggerbiggo.prima.view.sample.components;

import com.tiggerbiggo.prima.play.graphics.ImageTools;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimatedImageView extends ImageView {
  Timeline timer;

  Image[] imgs;

  int currentImage;

  public AnimatedImageView(Image[] _imgs, int framerate){
    setImgs(_imgs);

    timer = new Timeline(new KeyFrame(Duration.seconds(1.0 / framerate), e -> cycleImage()));
    timer.setCycleCount(Animation.INDEFINITE);
  }

  public AnimatedImageView(){
    this(ImageTools.toFXImage(ImageTools.blankArray()), 60);
  }

  public void setImgs(Image[] _imgs){
    if(!isValidArray(_imgs)) throw new IllegalArgumentException("Image array must not be null, and must not contain any null values.");

    imgs = _imgs;
    currentImage = 0;
  }

  public void setImgs(SafeImage[] _imgs){
    setImgs(ImageTools.toFXImage(_imgs));
  }

  public void cycleImage(){
    if(imgs == null) return;

    currentImage++;
    if (currentImage >= imgs.length) {
      currentImage = 0;
    }
    setImage(imgs[currentImage]);
  }

  public void start(){
    timer.play();
  }

  public void stop(){
    timer.stop();
  }

  public boolean isValidArray(Image[] toCheck){
    if(toCheck != null){
      for(int i=0; i<toCheck.length; i++){
        if(toCheck[i] == null) return false;
      }
      return true;
    }
    return false;
  }

}

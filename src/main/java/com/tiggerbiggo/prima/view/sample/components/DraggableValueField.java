package com.tiggerbiggo.prima.view.sample.components;

import com.tiggerbiggo.prima.play.core.Callback;
import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;

public class DraggableValueField extends TextField {
  private List<Callback> callbacks;

  private double initialValue;
  private double value;
  private double min;
  private double max;
  private double increment;

  private boolean displayAsInteger;

  private double xDelta = 0;
  private double preDragValue = 0;

  public DraggableValueField(double init, double minVal, double maxVal, double incVal){
    initialValue = init;
    value = init;
    min = minVal;
    max = maxVal;
    increment = incVal;
    callbacks = new ArrayList<>();

    displayAsInteger = false;

    setCursor(Cursor.DEFAULT);

    setOnMousePressed(e -> {
      if(e.getButton().equals(MouseButton.PRIMARY)){
        if(e.getClickCount() == 2){
          requestFocus();
        }
        xDelta = e.getX();
        preDragValue = value;
      }
      else if(e.getButton().equals(MouseButton.SECONDARY)){
        value = initialValue;
        updateDisplay();
        doCallbacks();
      }
      e.consume();
    });

    setOnMouseDragged(e -> {
      if(e.getButton().equals(MouseButton.PRIMARY)) {
        setValue(preDragValue + ((e.getX() - xDelta) * increment));

        updateDisplay();
        e.consume();
      }
    });

    setOnMouseClicked(Event::consume);

    setOnMouseReleased(e -> {
      doCallbacks();
      e.consume();
    });

    setOnAction(e -> {
      try{
        setValue(Double.parseDouble(getText()));
      }
      catch(NumberFormatException ex){
        updateDisplay();
      }

      doCallbacks();
    });

    updateDisplay();
  }

  public DraggableValueField() {
    this(0, 0, 100, 1);
  }

  public void setValue(double val){
    value = val;
    value = Calculation.clamp(min, max, value);
    updateDisplay();
  }

  public double getValue() {
    return value;
  }

  private void doCallbacks(){
    for(Callback c : callbacks){
      c.call(value);
    }
  }

  private void updateDisplay(){
    if(displayAsInteger){
      setText("" + (int)value);
    }
    else {
      setText("" + value);
    }
  }

  public DraggableValueField setDisplayAsInteger(boolean val){
    displayAsInteger = val;
    return this;
  }


  public DraggableValueField addCallback(Callback c){
    if(c == null) return this;

    callbacks.add(c);
    return this;
  }
}

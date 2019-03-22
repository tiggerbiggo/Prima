package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.sun.javafx.collections.ObservableListWrapper;
import com.tiggerbiggo.prima.play.core.Callback;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorOutputLink;
import com.tiggerbiggo.prima.view.sample.components.Knob;
import java.util.Arrays;
import java.util.function.BiFunction;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class TransformNode extends NodeInOut {

  @TransferGrid
  private TransformFunctions function;

  private VectorInputLink input;
  private VectorOutputLink output;

  @TransferGrid
  private double lerpPercent = 1;

  private TransformNode(TransformFunctions _function) {
    this.function = _function;
    input = new VectorInputLink();
    addInput(input);

    output = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        Vector2 raw = input.get(p);
        Vector2 transformed = function.apply(raw.X(), raw.Y());
        return raw.lerp(transformed, lerpPercent);
      }
    };
    addOutput(output);
  }

  public TransformNode() {
    this(TransformFunctions.SINSIN);
  }

  @Override
  public String getName() {
    return "Transform Node";
  }

  @Override
  public String getDescription() {
    return "Transforms a given Vector using a given Function object.";
  }

  @Override
  public Node getFXNode(ChangeListener listener) {
    ComboBox<TransformFunctions> funcBox = new ComboBox<>();
    funcBox.setItems(new ObservableListWrapper<>(Arrays.asList(TransformFunctions.values())));
    funcBox.setOnAction(e -> function = funcBox.getValue());
    funcBox.setValue(function);

    Knob n = new Knob(0, 1);
    n.setValue(lerpPercent);
    n.setChangeListener(listener);
    n.setCallback(new Callback() {
      @Override
      public void call(double value) {
        lerpPercent = value;
      }
    });

    return new VBox(funcBox, n);
  }
}

enum TransformFunctions {
  SINSIN((x, y) -> new Vector2(Math.sin(x), Math.sin(y))),
  SINX((x, y) -> new Vector2(Math.sin(x), y)),
  SINY((x, y) -> new Vector2(x, Math.sin(y))),

  MAGNETISM((x, y) -> {
    y = Math.sin(Math.cosh(x) * y);
    return new Vector2(x, y);
  }),
  TANNY((x, y) -> {
    y = Math.sin(Math.tanh(x) * Math.tan(y));
    return new Vector2(x, y);
  }),
  CHOPPY((x, y) -> {
    if ((Math.abs(x) + Math.abs(y)) % 1 <= 0.5) {
      x *= -1;
      y *= -1;
    }
    if ((Math.abs(x) - Math.abs(y)) % 1 <= 0.5) {
      x *= -1;
      y *= -1;
    }
    return new Vector2(x, y);
  }),
  NEW((x, y) -> {
    if ((x - y) < 0.1) {
      return null;
    }
    return new Vector2(x, y);
  }),
  HARMONIC((x, y) -> {
    for (int i = 0; i < 100; i++) {
      x = Math.sin(x);
      y = Math.sin(y);
    }
    return new Vector2(x, y);
  }),
  NEGATE((x, y) -> {
    x *= -1;
    y *= -1;

    return new Vector2(x, y);
  }),
  TESTSUM(Vector2::new),
  SAMEX((x, y) -> {
    return new Vector2(0, y);
  }),
  SAMEY((x, y) -> {

    return new Vector2(x, 0);
  }),
  SQUAREXY((x, y) -> {
    return new Vector2(x * x, y * y);
  }),NORMALIZE((x, y) -> {
    return new Vector2(x, y).normalize();
  });

  BiFunction<Double, Double, Vector2> func;

  TransformFunctions(BiFunction<Double, Double, Vector2> func) {
    this.func = func;
  }

  public Vector2 apply(double a, double b) {
    return func.apply(a, b);
  }
}

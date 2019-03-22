package com.tiggerbiggo.prima.view.sample.control.flow;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import java.util.function.BiFunction;

public enum FlowMode{
  MANHATTAN("Manhattan", (a, b) -> (a.X() - b.X()) + (a.Y() - b.Y())),
  EUCLIDEAN("Euclidean", Vector2::distanceBetween);

  String name;
  BiFunction<Vector2, Vector2, Double> func;

  FlowMode(String _name, BiFunction<Vector2, Vector2, Double> _func) {
    name = _name;
    func = _func;
  }

  public double apply(Vector2 a, Vector2 b){
    return func.apply(a, b);
  }
}

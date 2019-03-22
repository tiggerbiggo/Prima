package com.tiggerbiggo.prima.play.core.calculation;

import java.util.function.BiFunction;

public enum CombineFunction {
  ADD(Vector2::add),
  SUB(Vector2::subtract),
  MUL(Vector2::multiply),
  DIV(Vector2::divide);

  private BiFunction<Vector2, Vector2, Vector2> func;

  CombineFunction(BiFunction<Vector2, Vector2, Vector2> func) {
    this.func = func;
  }

  public Vector2 apply(Vector2 A, Vector2 B) {
    return func.apply(A, B);
  }
}

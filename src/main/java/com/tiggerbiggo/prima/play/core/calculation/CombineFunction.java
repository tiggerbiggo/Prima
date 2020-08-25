package com.tiggerbiggo.prima.play.core.calculation;

import java.util.function.BiFunction;

public enum CombineFunction {
  ADD(Vector2::add, '+'),
  SUB(Vector2::subtract, '-'),
  MUL(Vector2::multiply, '*'),
  DIV(Vector2::divide, '/'),
  MAX(Vector2::max, ' '),//TODO: Work out this char
  MIN(Vector2::min, ' ');

  private BiFunction<Vector2, Vector2, Vector2> func;

  private char operatorChar;

  CombineFunction(BiFunction<Vector2, Vector2, Vector2> func, char operatorChar) {
    this.func = func;
    this.operatorChar = operatorChar;
  }

  public char getOperatorChar(){ return operatorChar;}

  public Vector2 apply(Vector2 A, Vector2 B) {
    return func.apply(A, B);
  }
}

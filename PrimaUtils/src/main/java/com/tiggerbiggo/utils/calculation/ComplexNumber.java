package com.tiggerbiggo.utils.calculation;

public class ComplexNumber {

  //Stored complex number in the form real + bi
  double real, imaginary;

  public ComplexNumber(double real, double imaginary) {
    this.real = real;
    this.imaginary = imaginary;
  }

  public ComplexNumber(Vector2 in) {
    this(in.X(), in.Y());
  }

  public ComplexNumber() {
    this(0, 0);
  }

  public Vector2 asVector() {
    return new Vector2(this);
  }

  public ComplexNumber add(ComplexNumber B) {
    return new ComplexNumber(real + B.real, imaginary + B.imaginary);
  }

  public ComplexNumber multiply(ComplexNumber B) {
    double a, b, c, d, r, i;

    a = real;
    b = imaginary;

    c = B.real;
    d = B.imaginary;

    r = (a * c) - (b * d);
    i = (a * d) + (b * c);

    return new ComplexNumber(r, i);
  }

  public ComplexNumber power(int powerOf) {
    ComplexNumber c = this.clone();
    ComplexNumber c2 = this.clone();
    for (int i = 0; i < powerOf; i++) {
      c2 = c.multiply(c2);
    }
    return this;
  }
  /*
  (real+bi)(c+di)
  ac + adi + bci + bdi^2
  ac + adi + bci - bd
  simple = ac-bd
  complex = ad + bc
  *
  * */

  public ComplexNumber clone() {
    return new ComplexNumber(real, imaginary);
  }
}

package com.tiggerbiggo.primaplay.calculation;

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

  public static ComplexNumber add(ComplexNumber A, ComplexNumber B) {
    return new ComplexNumber(A.real + B.real, A.imaginary + B.imaginary);
  }

  public static ComplexNumber multiply(ComplexNumber A, ComplexNumber B) {
    double a, b, c, d, r, i;

    a = A.real;
    b = A.imaginary;

    c = B.real;
    d = B.imaginary;

    r = (a * c) - (b * d);
    i = (a * d) + (b * c);

    return new ComplexNumber(r, i);
  }
  /*
  (real+bi)(c+di)
  ac + adi + bci + bdi^2
  ac + adi + bci - bd
  simple = ac-bd
  complex = ad + bc
  *
  * */
}

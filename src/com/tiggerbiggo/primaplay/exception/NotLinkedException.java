package com.tiggerbiggo.primaplay.exception;

public class NotLinkedException extends RuntimeException {
  public NotLinkedException(){
    super();
  }

  public NotLinkedException(String s){
    super(s);
  }
}

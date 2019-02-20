package com.tiggerbiggo.prima.view.sample;

public class NodeParseException extends RuntimeException {
  public NodeParseException(String message){super(message);}
  public NodeParseException(Throwable cause){super(cause);}
  public NodeParseException(String message, Throwable cause) {
    super(message, cause);
  }
}

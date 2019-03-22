package com.tiggerbiggo.prima.view.sample.control.flow;

public class FlowCommander {
  private static FlowCommander defaultInstance;



  public static FlowCommander commander(){
    if(defaultInstance == null){
      defaultInstance = new FlowCommander();
    }
    return defaultInstance;
  }


}

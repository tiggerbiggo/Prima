package com.tiggerbiggo.prima.play.core;

public class PrimaConfig {
    private static PrimaConfig instance;

    private static PrimaConfig i(){
        if(instance == null){
            instance = new PrimaConfig();
        }


        return instance;
    }

    private PrimaConfig(){
    }
}

package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.SimplexNoise;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.NumberOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.defaults.MapGenDefaultLink;
import make.some.noise.Noise;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class NoiseNode extends NodeInOut{

  @TransferGrid
  double z;

  @TransferGrid
  NoiseType type;

  Noise noise = new Noise();

  VectorInputLink mapIn;
  VectorInputLink zwIn;
  NumberOutputLink singleOut;

  public NoiseNode() {
    z = 0;
    type = NoiseType.SIMPLEX;

    mapIn = new MapGenDefaultLink("Position (Does not apply to white noise!)");

    addInput(mapIn);

    singleOut = new NumberOutputLink("Out") {
      @Override
      public Float get(RenderParams p) {
        Vector2 xy = mapIn.get(p);
        Vector2 zw = zwIn.get(p);

        switch(type){
          case WHITE:
            return (float) Math.random();
          case SIMPLEX:
            return noise.getSimplex(xy.fX(), xy.fY(), zw.fX(), zw.fY());
        }
        return (float)0;
      }

      @Override
      public void generateGLSLMethod(StringBuilder s) {
        //TODO
        throw new NotImplementedException();
      }

      @Override
      public String getMethodName() {
        //TODO
        throw new NotImplementedException();
      }
    };
    addOutput(singleOut);
  }

  @Override
  public String getName() {
    return "Noise Node";
  }

  @Override
  public String getDescription() {
    return "Generates different kinds of random noise";
  }
}
enum NoiseType{
  WHITE,
  SIMPLEX
}

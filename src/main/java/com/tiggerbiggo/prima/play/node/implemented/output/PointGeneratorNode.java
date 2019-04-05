package com.tiggerbiggo.prima.play.node.implemented.output;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderID;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeHasOutput;
import com.tiggerbiggo.prima.play.node.link.type.PointOutputLink;
import java.util.HashMap;
import java.util.function.Function;

public class PointGeneratorNode extends NodeHasOutput {
  transient HashMap<RenderID, Vector2[]> renderCache;

  @TransferGrid
  public int pointNumber;

  @TransferGrid
  public PointGenType type = PointGenType.SQUOGLE;

  private PointOutputLink out;

  public PointGeneratorNode(){
    renderCache = new HashMap<>();

    out = new PointOutputLink() {
      @Override
      public Vector2[] get(RenderParams pa) {
        Vector2[] points = new Vector2[pointNumber];

        for (int i=0; i<pointNumber; i++) {
          points[i] = type.apply(i);
        }

        return points;
      }
    };
    addOutput(out);
  }

  @Override
  public String getName() {
    return "Point Generator Node";
  }

  @Override
  public String getDescription() {
    return "Generates a set of points.";
  }
}

enum PointGenType{
  SQUOGLE(i -> {
    return new Vector2(i*Math.PI * 0.3, i*0.2).fromPolar();
  }),
  SPOINK(i -> {
    return new Vector2(((i*0.11) % 2)-1, ((i * 0.01) % 2)-1);
  }),
  SPOINK2(i -> {
    return new Vector2((i*0.11) % 1, (i * 0.01) % 1);
  });

  private Function<Integer, Vector2> func;
  PointGenType(Function <Integer, Vector2> func){
    this.func = func;
  }

  public Vector2 apply(int i){
    return func.apply(i);
  }
}
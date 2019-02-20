package com.tiggerbiggo.prima.play.node.link.type.defaults;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;

public class MapGenDefaultLink extends VectorInputLink {
  @Override
  public Vector2 defaultValue(RenderParams p) {
    return mapFromParams(p);
  }
  public static Vector2 mapFromParams(RenderParams p){
    return new Vector2(
        p.x() * (1d / p.width()),
        p.y() * (1d / p.height())
    );
  }
}

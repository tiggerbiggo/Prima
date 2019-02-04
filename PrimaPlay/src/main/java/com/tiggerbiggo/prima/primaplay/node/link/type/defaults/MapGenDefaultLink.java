package com.tiggerbiggo.prima.primaplay.node.link.type.defaults;

import com.tiggerbiggo.prima.primaplay.core.render.RenderParams;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.utils.calculation.Vector2;

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

package com.tiggerbiggo.prima.play.node.link.type.defaults;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;

public class MapGenDefaultLink extends VectorInputLink {
  double mX;
  double mY;

  double oX;
  double oY;

  public MapGenDefaultLink(String desc){
    super(desc);
    mX = 1;
    mY = 1;

    oX = 0;
    oY = 0;
  }

  public MapGenDefaultLink(double x, double y, double oX, double oY, String desc){
    super(desc);
    mX = x;
    mY = y;
    this.oX = oX;
    this.oY = oY;
  }

  @Override
  public Vector2 defaultValue(RenderParams p) {
    return mapFromParams(p, mX, mY, oX, oY);
  }

  public static Vector2 mapFromParams(RenderParams p){
    return mapFromParams(p, 1, 1, 1, 1);
  }

  public static Vector2 mapFromParams(RenderParams p, double mX, double mY, double oX, double oY){
    return new Vector2(
            (p.x() * (1d / p.width()) * mX) + oX,
            (p.y() * (1d / p.height())* mY) + oY
    );
  }
}

package com.tiggerbiggo.prima.primaplay.core.render;

import com.tiggerbiggo.prima.primaplay.node.link.InputLink;

public class Renderable<T> {
  T[][] buffer;
  InputLink<T> link;

  public Renderable(T[][] _buffer, InputLink<T> _link){
    buffer = _buffer;
    link = _link;
  }



  public T safeGet(int x, int y, int width, int height, int n){
    x = Math.abs(x);
    y = Math.abs(y);

    x = x%buffer.length;
    y = y%buffer[0].length;

    if(buffer[x][y] == null){
      buffer[x][y] = link.get(new RenderParams(width, height, x, y, n));
    }

    return buffer[x][y];
  }
}
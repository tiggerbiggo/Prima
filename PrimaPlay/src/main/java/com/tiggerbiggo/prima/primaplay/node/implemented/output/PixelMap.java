package com.tiggerbiggo.prima.primaplay.node.implemented.output;

import com.tiggerbiggo.utils.calculation.Vector2;

public class PixelMap {

  private Vector2[][] map;

  public PixelMap(int x, int y) {
    map = new Vector2[x][y];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        map[i][j] = new Vector2(0);
      }
    }
  }

  public Vector2 get(int x, int y) {
    try {
      return map[x][y];
    } catch (ArrayIndexOutOfBoundsException e) {
      return Vector2.ZERO;
    }
  }


  public void set(int x, int y, Vector2 v) {
    try {
      map[x][y] = v;
    } catch (ArrayIndexOutOfBoundsException ex) {
    }
  }

  public void line(int x0, int y0, int x1, int y1, Vector2 v) {
    int dx = Math.abs(x1 - x0);
    int dy = Math.abs(y1 - y0);

    int sx = x0 < x1 ? 1 : -1;
    int sy = y0 < y1 ? 1 : -1;

    int err = dx - dy;
    int e2;

    while (true) {
      set(x0, y0, v);

      if (x0 == x1 && y0 == y1) {
        break;
      }

      e2 = 2 * err;
      if (e2 > -dy) {
        err = err - dy;
        x0 = x0 + sx;
      }

      if (e2 < dx) {
        err = err + dx;
        y0 = y0 + sy;
      }
    }
  }

  public void line(Vector2 p1, Vector2 p2, Vector2 v) {
    line(p1.iX(), p1.iY(), p2.iX(), p2.iY(), v);
  }
}

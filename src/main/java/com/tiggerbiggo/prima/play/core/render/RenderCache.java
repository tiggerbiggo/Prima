package com.tiggerbiggo.prima.play.core.render;

import com.tiggerbiggo.prima.play.node.link.InputLink;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RenderCache<T> {

  final T[][] cache;
  final Lock[] lock;

  final InputLink<T> link; //where we get our data from, matches type of cache

  final int width, height;
  final AtomicInteger count;

  public RenderCache(T[][] _cache, InputLink<T> _link) {
    if (_cache == null) {
      throw new IllegalArgumentException("Cache array cannot be null");
    }
    if (_link == null) {
      throw new IllegalArgumentException("Input link cannot be null");
    }

    cache = _cache;
    width = cache.length;
    height = cache[0].length;

    lock = new Lock[width];
    for (int i = 0; i < width; i++) {
      lock[i] = new ReentrantLock();
    }

    link = _link;

    count = new AtomicInteger(width);
  }

  private boolean isDone = false; //Flag to determine if we enter the loop or return directly

  private void cacheLoop(RenderParams p) {
    for (int i = 0; i < width; i++) {
      if (lock[i].tryLock()) {
        for (int j = 0; j < height; j++) {
          cache[i][j] = link.get(new RenderParams(width, height, i, j, p.frameNum(), p.getId()));
        }
        if(count.decrementAndGet() == 1) isDone = true;
      }
    }
  }

  public T get(RenderParams p) {
    if(!isDone) {
      cacheLoop(p);
    }
    T tmp = cache[p.x()][p.y()];
    if(tmp != null) {
      return cache[p.x()][p.y()];
    }
    else{
      return link.get(new RenderParams(width, height, p.x(), p.y(), p.frameNum(), p.getId()));
    }
  }
}

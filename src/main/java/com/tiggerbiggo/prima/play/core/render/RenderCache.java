package com.tiggerbiggo.prima.play.core.render;

import com.tiggerbiggo.prima.play.node.link.InputLink;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RenderCache<T> {
  final T[][] cache;
  final Lock[][] lock;

  final Lock validLock;

  final InputLink<T> link; //where we get our data from, matches type of cache

  final int width, height;

  public RenderCache(T[][] _cache, InputLink<T> _link){
    if(_cache == null) throw new IllegalArgumentException("Cache array cannot be null");
    if(_link == null) throw new IllegalArgumentException("Input link cannot be null");

    cache = _cache;
    width = cache.length;
    height = cache[0].length;

    lock = new Lock[width][height];
    for(int i=0; i<width; i++){
      for(int j=0; j<height; j++){
        lock[i][j] = new ReentrantLock();
      }
    }

    validLock = new ReentrantLock();

    link = _link;
  }

  private boolean isDone = false; //Flag to determine if we enter the loop or return directly

  private synchronized void checkValidity(){
    if(isDone) return;
    for(int i=0; i<width; i++){
      for(int j=0; j<height; j++){
        if(cache[i][j] == null) return;
      }
    }
    isDone = true;
    validLock.notifyAll();
  }

  private void cacheLoop(RenderParams p){
    for(int i=0; i<width; i++){
      for(int j=0; j<height; j++){
        if(lock[i][j].tryLock()){
          if(cache[i][j] == null){
            cache[i][j] = link.get(new RenderParams(width, height, i, j, p.frameNum(), p.getId()));
          }
          lock[i][j].unlock();
        }
      }
    }

    synchronized(validLock){
      checkValidity();
      if(isDone) return;
      try {
        validLock.wait();
      }
      catch(InterruptedException ex){}
    }
  }

  public T get(RenderParams p){
    if(!isDone){
      cacheLoop(p);
    }
    return cache[p.x()][p.y()];
  }
}

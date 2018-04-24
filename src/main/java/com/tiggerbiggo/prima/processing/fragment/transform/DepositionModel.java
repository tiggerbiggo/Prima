package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.core.Vector2;
import java.io.Serializable;
import java.util.ArrayList;

public class DepositionModel implements Serializable {

  int xDim, yDim;
  private ArrayList<Particle> particles;
  Vector2 target;
  double searchDist, stickDist;

  public DepositionModel(int xDim, int yDim, double searchDist, double stickDist) {
    this.xDim = xDim;
    this.yDim = yDim;
    this.searchDist = searchDist;
    this.stickDist = stickDist;
    target = new Vector2(xDim / 2.0, yDim / 2.0);
    particles = new ArrayList<>();
  }

  public void simulate(int num) {
    for (int i = 0; i < num; i++) {
      simulate();
    }
  }

  public void simulate() {
    if (particles.size() == 0) {
      particles.add(new Particle(target, target));
    }

    Particle p = new Particle(
        Vector2.randomOnCircle(target, xDim * 2.0d),
        target);

    Particle collided;
    collided = checkCollision(p);

    int loopCount = 0;

    while (collided != null && loopCount < xDim + yDim) {
      p.next(1);
      collided = checkCollision(p);
      loopCount++;
    }
    //p.quantize();

    p.stickTo(collided, stickDist);
    particles.add(p);
  }

  private Particle checkCollision(Particle in) {
    Vector2 next = in.getNext(1);
    for (Particle comparison : particles) {
      if (Vector2.distanceBetween(next, comparison.pos()) <= searchDist) {
        return comparison;
      }
    }
    return null;
  }

  public ArrayList<Vector2> getPoints() {
    ArrayList<Vector2> toReturn = new ArrayList<>();
    for (Particle p : particles) {
      toReturn.add(p.pos());
    }
    return toReturn;
  }
}

class Particle implements Serializable {

  public Vector2 position, target;

  public Particle(Vector2 position, Vector2 target) {
    this.position = position;
    this.target = target;
  }

  public void next(double dist) {
    position = getNext(dist);
  }

  public Vector2 getNext(double dist) {
    Vector2 dir = Vector2.subtract(target, position);
    dir = Vector2.normalize(dir);
    dir = Vector2.multiply(dir, new Vector2(dist));
    return Vector2.add(position, dir);
  }

  public Vector2 pos() {
    return position;
  }

  public void stickTo(Particle p, double dist) {
    if (p == null) {
      return;
    }
    Vector2 dir = Vector2.subtract(p.pos(), position);
    dir = Vector2.normalize(dir);
    dir = Vector2.multiply(dir, new Vector2(dist));
    position = Vector2.add(p.pos(), dir);
  }

  public void quantize() {
    position = new Vector2(position.iX(), position.iY());
  }
}

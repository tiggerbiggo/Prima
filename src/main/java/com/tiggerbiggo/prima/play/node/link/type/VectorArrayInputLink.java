package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.link.InputLink;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;

public class VectorArrayInputLink extends InputLink<Vector2[]> {
  public VectorArrayInputLink(String desc){
    this.desc = desc;
  }

  private VectorOutputLink collapsed = null;

  @Override
  public boolean link(OutputLink toLink) {
    if(!canLink(toLink)) return false;
    if (toLink instanceof VectorArrayOutputLink) {
      collapsed = null;
      currentLink = (VectorArrayOutputLink) toLink;
      return true;
    }
    else if(toLink instanceof  VectorOutputLink){
      currentLink = null;
      collapsed = (VectorOutputLink) toLink;
      return true;
    }
    return false;
  }

  @Override
  public void unlink() {
    currentLink = null;
    collapsed = null;
  }

  @Override
  public boolean isLinked() {
    return currentLink != null || collapsed != null;
  }

  @Override
  public Vector2[] get(RenderParams p) {
    if(currentLink != null){
      return currentLink.get(p);
    }
    if(collapsed != null){
      return Vector2.makeArray(p.frameNum(), collapsed.get(p));
    }
    return defaultValue(p);
  }

  @Override
  public OutputLink getCurrentLink() {
    if(currentLink != null){
      return currentLink;
    }
    if(collapsed != null){
      return collapsed;
    }
    return null;
  }

  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof VectorArrayOutputLink || other instanceof VectorOutputLink;
  }

  @Override
  public Vector2[] defaultValue(RenderParams p) {
    return Vector2.blankArray(p.frameNum());
  }

  @Override
  public String getStyleClass() {
    return "VectorArrayLink";
  }


}



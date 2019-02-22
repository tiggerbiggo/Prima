package com.tiggerbiggo.prima.play.core.render;

import com.tiggerbiggo.prima.play.node.link.type.ColorArrayInputLink;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Renderer {
  private static final int DEFAULT_THREAD_NUM = 6;
  private static Renderer defaultRenderer;
  public static Renderer getDefaultRenderer() {
    if(defaultRenderer == null) defaultRenderer = new Renderer();
    return defaultRenderer;
  }

  private ExecutorService exec;
  private int threadNum;

  public Renderer(){
    this(DEFAULT_THREAD_NUM);
  }

  public Renderer(int threadNum) {
    this.threadNum = threadNum;
    exec = Executors.newFixedThreadPool(threadNum);
  }

  public static RenderTask queueToDefaultRenderer(int width, int height, int frameNum, ColorArrayInputLink link, RenderCallback ... callbacks){
    if(defaultRenderer == null){
      defaultRenderer = new Renderer();
    }

    return defaultRenderer.queueRender(width, height, frameNum, link, callbacks);
  }

  public RenderTask queueRender(int width, int height, int frameNum, ColorArrayInputLink link, RenderCallback ... callbacks) {
    RenderTask toReturn = new RenderTask(width, height, frameNum, link, callbacks);

    for (int i = 0; i < threadNum; i++) {
      exec.submit(toReturn);
    }

    return toReturn;
  }
}


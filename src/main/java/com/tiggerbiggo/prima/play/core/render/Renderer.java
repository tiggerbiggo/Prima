package com.tiggerbiggo.prima.play.core.render;

import com.tiggerbiggo.prima.play.graphics.SafeImage;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Renderer {
  private static Renderer inst;
  private ExecutorService exec;
  private int threadNum;
  private Queue<RenderTask> taskList;

  public Renderer(int _threadNum){
    if(inst == null)
      inst = this;
    threadNum = _threadNum;
    exec = Executors.newFixedThreadPool(threadNum);
    taskList = new ArrayDeque<>();
  }

  public void queue(RenderTask task){
    task.addCallback(new RenderCallback() {
      @Override
      public void callback(SafeImage[] imgs) {
        taskList.poll();
        submit();
      }
    });
    taskList.add(task);
    if(taskList.size() == 1) {

      submit();
    }
  }

  private void submit(){
    RenderTask task = taskList.peek();
    if(task != null) {
      for (int i = 0; i < threadNum; i++) {
        exec.submit(new RenderSlave(task));
      }
    }
  }

  public static Renderer getInstance(){
    if(inst == null){
      inst = new Renderer(6);
    }
    return inst;
  }

  public String getCurrentName(){
    if(taskList.size() == 0) return "No Render Tasks";
    return taskList.peek().getDescription();
  }

  public double getCurrentProgress(){
    if(taskList.size() == 0) return 0;
    return taskList.peek().getPercentage();
  }

  public int getQueueLength(){
    return taskList.size();
  }
}

class RenderSlave implements Runnable{
  RenderTask task;

  public RenderSlave(RenderTask _task){
    task = _task;
  }

  @Override
  public void run() {
    while(task.step());
  }
}

class RenderCoordinate{
  int x, y;

  public RenderCoordinate(int x, int y){
    this.x = x;
    this.y = y;
  }
}
package com.tiggerbiggo.prima.processing;

import com.tiggerbiggo.prima.processing.tasks.Task;

/**Processes Task objects in a separate thread,
 * Designed to be used with a ProcessManager,
 * iterates over a Task object in conjunction
 * with other parallel Processor objects.
 *
 * @see ProcessManager
 * @author tiggerbiggo
 */
public class Processor implements Runnable
{
    Task myTask;

    public Processor(Task t)
    {
        myTask = t;
    }

    @Override
    public synchronized void run()
    {
        while(!myTask.isDone())
        {
            myTask.doTask();
        }
        notifyAll();
    }
}

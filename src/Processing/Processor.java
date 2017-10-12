package Processing;

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
        Object current;
        while((current=myTask.getNext())!= null)
        {
            myTask.doTask(current);
            //System.out.println("Doing Op: " + current);
        }
        notifyAll();
    }
}

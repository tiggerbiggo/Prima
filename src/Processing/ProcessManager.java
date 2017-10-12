package Processing;

/**
 * Manages a set of Processor objects. Implements Runnable to enable multi threading,
 * but this is not required. Simply instantiate the class and call run to run in the current thread.
 *
 * @author tiggerbiggo
 */
public class ProcessManager implements Runnable
{
    Thread[] threads;
    Task toProcess;

    boolean isRunning = false;

    public ProcessManager(int numThreads, Task toProcess) throws NegativeArraySizeException
    {
        this.toProcess = toProcess;
        threads = new Thread[numThreads];

        for(int i=0; i<numThreads; i++)
        {
            threads[i] = new Thread(new Processor(toProcess));
        }
    }

    @Override
    public synchronized void run() throws IllegalThreadStateException
    {
        if(isRunning) throw new IllegalThreadStateException("Process is already running");

        isRunning = true;
        for(Thread t : threads)
        {
            t.run();
        }

        while(isAlive())
        {
            try
            {
                wait();
            }
            catch(Exception e){}
        }
        notifyAll();
    }

    private boolean isAlive()
    {
        for(Thread t : threads)
        {
            if(t.isAlive()) return true;
        }
        System.out.println("Threads are dead");
        return false;
    }
}

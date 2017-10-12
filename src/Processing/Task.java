package Processing;

import Core.float2;

public abstract class Task
{
    public abstract Object getNext();

    public abstract boolean isDone();

    public abstract void doTask(Object in);
}

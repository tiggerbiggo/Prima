package Processing;
import Core.float2;

public abstract class MapTask extends Task
{
    protected float2[][] map;
    private int current = 0;
    private int max;
    private int width, height;

    public MapTask(float2[][] map)
    {
        if(map == null) throw new IllegalArgumentException("Map must not be null!");
        this.map = map;
        width = map.length;
        height = map[0].length;
        max = width * height;
    }

    public abstract void doTask(Object in);

    @Override
    public Object getNext() {
        if(current <=-1 || isDone()) return null;

        if(current == 49)
        {
            System.out.println("doing a thing");
        }

        System.out.printf("Current: %d\ncurrent %%(width): %d \ncurrent/height: %d\n\n",
                current, current%(width), current/ height);
        float2 got = map[current%width][current/ width];
        current++;
        return got;
    }

    @Override
    public boolean isDone() {
        return current >= max;
    }

    float2 convertObject(Object in)
    {
        try
        {
            return (float2)in;
        }
        catch(Exception e)
        {
            return null;
        }
    }
}

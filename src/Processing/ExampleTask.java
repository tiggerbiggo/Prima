package Processing;
import Core.float2;

/**
 * An example implementation of Task which swaps the x and y components of each float2 object.
 * @author tiggerbiggo
 */
public class ExampleTask extends MapTask
{

    public ExampleTask(float2[][] map)
    {
        super(map);
    }

    @Override
    public void doTask(Object in) {
        float2 converted = convertObject(in);
        if(converted == null) return;
        //swap the x and y coords

        float tmp = converted.getX();
        converted.set(converted.getY(), tmp);
    }
}

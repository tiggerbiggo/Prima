package Processing;
import Core.float2;

public class FormulaTask extends MapTask
{
    public FormulaTask(float2[][] map)
    {
        super(map);
    }

    @Override
    public void doTask(Object in) {
        float2 converted = convertObject(in);

        if(converted == null) return;

        doFormula(converted);
    }

    public void doFormula(float2 in)
    {

    }

    public float2[][] getMap()
    {
        return map;
    }
}

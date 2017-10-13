package Presets;
import Core.float2;

public class MapGenerator
{

    public static float2[][] standard(int width, int height, float2 offset, float2 scale) throws IllegalArgumentException
    {
        if(width <=0 || height <=0 || offset == null || scale == null)
            throw new IllegalArgumentException("One or more arguments in constructor is invalid");

        float2[][] map = new float2[width][height];

        for(int i=0; i<width; i++)
        {
            for(int j=0; j<height; j++)
            {
                float x, y;
                x = i/(width/scale.getX());
                x += offset.getX();

                y = j/(height/scale.getY());
                y += offset.getY();

                map[i][j] = new float2(x, y);
            }
        }
        return map;
    }

    public static float2[][] standard(int width, int height)
    {
        return standard(width, height, new float2(0,0), new float2(1,1));
    }

}

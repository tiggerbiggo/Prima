package com.tiggerbiggo.prima.presets;
import com.tiggerbiggo.prima.core.float2;

public class MapGenerator
{

    public static float2[][] getMap(int width, int height, float2 offset, float2 scale, MapTypes type) throws IllegalArgumentException
    {
        if(width <=0 || height <=0 || offset == null || scale == null)
            throw new IllegalArgumentException("One or more arguments in constructor is invalid");

        float2[][] map = new float2[width][height];

        switch(type) {
            case REGULAR:
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        float x, y;
                        x = i / (width / scale.getX());
                        x += offset.getX();

                        y = j / (height / scale.getY());
                        y += offset.getY();

                        map[i][j] = new float2(x, y);
                    }
                }
                break;
        }
        return map;
    }
}

package com.tiggerbiggo.prima.presets;
import com.tiggerbiggo.prima.core.float2;
import com.tiggerbiggo.prima.processing.fragment.ConstFragment;

public class MapGenerator
{

    public static float2[][] getMap(int width, int height, float2 offset, float2 scale, MapTypes type) throws IllegalArgumentException
    {
        width = Math.max(1, width);
        height = Math.max(1, height);
        if(offset == null || scale == null)
            throw new IllegalArgumentException("Either Offset or Scale are null.");

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

    public static ConstFragment[][] getFragMap(int width, int height, float2 offset, float2 scale, MapTypes type) throws IllegalArgumentException
    {
        width = Math.max(1, width);
        height = Math.max(1, height);
        if(offset == null || scale == null)
            throw new IllegalArgumentException("Either Offset or Scale are null.");

        ConstFragment[][] map = new ConstFragment[width][height];

        switch(type) {
            case REGULAR:
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        float x, y;
                        x = i / (width / scale.getX());
                        x += offset.getX();

                        y = j / (height / scale.getY());
                        y += offset.getY();

                        map[i][j] = new ConstFragment(new float2(x, y));
                    }
                }
                break;
        }
        return map;
    }
}

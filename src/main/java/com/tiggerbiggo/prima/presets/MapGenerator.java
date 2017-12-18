package com.tiggerbiggo.prima.presets;

import com.tiggerbiggo.prima.core.float2;
import com.tiggerbiggo.prima.processing.fragment.ConstFragment;

public class MapGenerator {
    public static ConstFragment[][] getFragMap(
            int width,
            int height,
            float2 topLeft,
            float2 bottomRight)
            throws IllegalArgumentException
    {
        width = Math.max(1, width);
        height = Math.max(1, height);
        if (topLeft == null || bottomRight == null)
            throw new IllegalArgumentException("Either Offset or Scale are null.");

        ConstFragment[][] map = new ConstFragment[width][height];

        float x1, x2, y1, y2, dx, dy;

        x1=topLeft.X();
        x2=bottomRight.X();

        y1=topLeft.Y();
        y2=bottomRight.Y();

        dx = x2-x1;
        dy = y2-y1;

        dx/=width;
        dy/=height;

        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                if(i == 300 || j == 300)
                    System.out.println("Break here!");
                map[i][j] = new ConstFragment(
                        new float2(
                                x1+(i*dx),
                                y1+(j*dy)
                        )
                );
            }
        }

        return map;
    }
}

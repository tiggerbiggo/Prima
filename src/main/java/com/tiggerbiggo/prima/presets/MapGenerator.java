package com.tiggerbiggo.prima.presets;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.ConstFragment;

public class MapGenerator {
    public static ConstFragment[][] getFragMap(
            int width,
            int height,
            Vector2 A,
            Vector2 B)
            throws IllegalArgumentException
    {
        width = Math.max(1, width);
        height = Math.max(1, height);
        if (A == null || B == null)
            throw new IllegalArgumentException("Either Offset or Scale are null.");

        ConstFragment[][] map = new ConstFragment[width][height];

        double x1, x2, y1, y2, dx, dy;

        //      +-----------------------+
        //      |                (x2,y2)|
        //      |                     B |
        //      |                       |
        //      |                       |
        //      |                       |
        //      | A                     |
        //      |(x1,y1)                |
        //      +-----------------------+

        x1=A.X();
        x2=B.X();

        y1=A.Y();
        y2=B.Y();

        dx = x2-x1;
        dy = y2-y1;

        dx/=width;
        dy/=height;

        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                map[i][j] = new ConstFragment(
                        new Vector2(
                                x1+(i*dx),
                                y1+(j*dy)
                        )
                );
            }
        }

        return map;
    }
}

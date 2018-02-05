package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

import java.io.Serializable;

public class MandelFragment implements Fragment<Vector2>, Serializable
{

    private Fragment<Vector2> frag;
    Fragment<Vector2>[][] map;
    private int iter;

    public MandelFragment(Fragment<Vector2> c, int iter)
    {
        this.frag = c;
        this.iter = iter;
    }

    @Override
    public Vector2 get() {
        //Start z at (0,0i), treating the y component as coefficient of i
        Vector2 z = Vector2.ZERO;

        //get the point we are rendering
        Vector2 c = frag.get();

        if(c == null) return null;

        for(int i=0; i<iter; i++)
        {
            //temporary hold values for this iteration
            double a, b;

            //set a and b from the current z value
            a=z.X();
            b=z.Y();

            //perform calculation for this iteration
            z = new Vector2(
                    (a*a)-(b*b)+c.Y(),
                    (2*a*b) + c.X()
            );

            //check for out of bounds
            if(z.magnitude() >2)
            {
                double x = z.X();
                double y = z.Y();
                return new Vector2(x, y);
            }
        }
        //edge case to return zero if never escapes
        return Vector2.ZERO;
    }

    @Override
    public Fragment<Vector2>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {
        map = frag.build(xDim, yDim);
        return new MandelFragment[xDim][yDim];
    }

    @Override
    public Fragment<Vector2> getNew(int i, int j) {
        return new MandelFragment(map[i][j], iter);
    }
}

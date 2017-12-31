package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.core.int2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

public class MandelFragment implements Fragment<Vector2>
{

    private Fragment<Vector2> frag;
    private int iter;

    public MandelFragment(Fragment<Vector2> c, int iter)
    {
        this.frag = c;
        this.iter = iter;
    }

    @Override
    public Vector2 get() {
        Vector2 z = Vector2.ZERO;
        Vector2 c = frag.get();

        if(c == null) return null;

        for(int i=0; i<iter; i++)
        {
            float a, b;

            a=z.fX();
            b=z.fY();

            z = new Vector2(
                    (a*a)-(b*b)+c.fY(),
                    (2*a*b) + c.fX()
            );

            if(z.magnitude() >2)
            {
                return new Vector2(i, i);
            }
        }
        return Vector2.ZERO;
    }

    @Override
    public Fragment<Vector2>[][] build(Vector2 dims) throws IllegalMapSizeException {
        Fragment<Vector2>[][] map;
        try {
            map = frag.build(dims);
        }
        catch(IllegalMapSizeException ex)
        {
            throw ex;
        }

        if(Fragment.checkArrayDims(map, dims))
        {
            Fragment<Vector2>[][] meFragment = new Fragment[dims.iX()][dims.iY()];
            for(int i=0; i<dims.X(); i++)
            {
                for(int j=0; j<dims.Y(); j++)
                {
                    meFragment[i][j] = new MandelFragment(map[i][j],iter);
                }
            }
            return meFragment;
        }
        throw new IllegalMapSizeException();
    }
}

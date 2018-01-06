package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

public class SuperSampleFragment implements Fragment<Vector2>{

    private int factor;

    private boolean isPublic = false;

    private Fragment<Vector2>[] forSample;
    private Fragment<Vector2> nextFragment;

    public SuperSampleFragment(Fragment<Vector2> nextFragment, int factor) {
        if(factor <= 0) throw new IllegalArgumentException("Super Sampling requires a >=1 integer.");
        this.factor = factor;
        this.nextFragment = nextFragment;
        isPublic = true;
    }

    private SuperSampleFragment(Fragment<Vector2>[] forSample){
        this.forSample = forSample;
    }

    @Override
    public Vector2 get() {
        return null;
    }

    @Override
    public Fragment<Vector2>[][] build(Vector2 dims) throws IllegalMapSizeException {
        if(!isPublic) return null;

        Fragment<Vector2>[][] nextArray;
        try {
            nextArray = nextFragment.build(Vector2.multiply(dims, new Vector2(factor)));
        }
        catch (IllegalMapSizeException e) {
            throw e;
        }

        int width = nextArray.length;
        int height = nextArray[0].length;

        Fragment<Vector2>[][] thisArray = new Fragment[dims.iX()][dims.iY()];

        for(int i=0; i<width; i+= factor)
        {
            for(int j=0; j<height; j+= factor)
            {
                Fragment<Vector2>[] forSample = new Fragment[factor*factor];
                for(int x=0; x < factor; x++)
                {
                    for(int y=0; y < factor; y++)
                    {
                        int index = ((x+1)*factor)+(y+1);
                    }
                }
            }
        }

        return new Fragment[0][];
    }
}

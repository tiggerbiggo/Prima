package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.calculation.ColorTools;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

import java.awt.*;
import java.util.ArrayList;

public class SuperSampleFragment implements Fragment<Color[]>
{
    private int factor;
    Fragment<Color[]> in;
    ArrayList<Fragment<Color[]>> toCombine;

    private SuperSampleFragment(ArrayList<Fragment<Color[]>> toCombine) {
        this.toCombine = toCombine;
    }

    public SuperSampleFragment(int factor, Fragment<Color[]> in) {
        this.factor = factor;
        this.in = in;
    }

    @Override
    public Color[] get() {
        if(toCombine == null) return null;

        Color[][] colors =
                new Color
                        [toCombine.get(0).get().length]
                        [toCombine.size()];

        {
            int j=0;
            for(Fragment<Color[]> cA : toCombine) {
                int i=0;
                for(Color c : cA.get()) {
                    colors[i][j] = c;
                    i++;
                }
                j++;
            }
        }

        Color[] averages = new Color[colors.length];

        for(int i=0; i<colors.length; i++) {
            averages[i] = ColorTools.colorAvg(colors[i]);
        }

        return averages;
    }

    @Override
    public Fragment<Color[]>[][] build(int xDim, int yDim) throws IllegalMapSizeException {
        Fragment<Color[]>[][] map;
        map = in.build(xDim * factor, yDim * factor);

        SuperSampleFragment[][] toReturn = new SuperSampleFragment[xDim][yDim];

        for(int i=0; i<xDim; i++) {
            for(int j=0; j<yDim; j++) {
                ArrayList<Fragment<Color[]>> tmp = getSubset(i, j, map, factor);
                toReturn[i][j] = new SuperSampleFragment(tmp);
            }
        }

        return toReturn;
    }

    private ArrayList<Fragment<Color[]>> getSubset(int deltai, int deltaj, Fragment<Color[]>[][] map, int factor) {
        ArrayList<Fragment<Color[]>> toReturn = new ArrayList<>();

        deltai *= factor;
        deltaj *= factor;

        for(int i=0; i<factor; i++) {
            for(int j=0; j<factor; j++){
                toReturn.add(map[deltai+i][deltaj+j]);
            }
        }

        return toReturn;
    }

    @Override
    public Fragment<Color[]>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {return null;}

    @Override
    public Fragment<Color[]> getNew(int i, int j) {return null;}
}

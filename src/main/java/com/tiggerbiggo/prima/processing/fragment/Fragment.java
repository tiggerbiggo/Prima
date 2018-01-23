package com.tiggerbiggo.prima.processing.fragment;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

/**
 * The base interface for all fragments
 * @param <T> The type the fragment should return
 */
public interface Fragment<T>
{
    /**Get method, calculation should be done here and should return type T
     * @return The output of the fragment
     */
    T get();

    default Fragment<T>[][] build(int xDim, int yDim) throws IllegalMapSizeException {
        Fragment<T>[][]thisArray = getArray(xDim, yDim);
        if(!checkArrayDims(thisArray, xDim, yDim)) throw new IllegalMapSizeException();
        for(int i=0; i<xDim; i++) {
            for(int j=0; j<yDim; j++) {
                thisArray[i][j] = getNew(i, j);
            }
        }
        return thisArray;
    }

    Fragment<T>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException;

    Fragment<T> getNew(int i, int j);

    static boolean checkArrayDims(Fragment<?>[][] array, int xDim, int yDim) {
        if(array != null) {
            if(array.length == xDim && array[0].length == yDim) {
                return true;
            }
        }
        return false;
    }


}
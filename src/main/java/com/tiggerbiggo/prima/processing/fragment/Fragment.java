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
    public T get();

    public Fragment<T>[][] build(int xDim, int yDim) throws IllegalMapSizeException;

    public static boolean checkArrayDims(Fragment<?>[][] array, int xDim, int yDim)
    {
        if(array != null)
        {
            if(array.length == xDim && array[0].length == yDim)
            {
                return true;
            }
        }
        return false;
    }
}
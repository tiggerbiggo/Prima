package com.tiggerbiggo.prima.processing.fragment;

        import com.tiggerbiggo.prima.core.int2;
        import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

public interface Fragment<T>
{
    public T get();
    public Fragment<T>[][] build(int2 dims) throws IllegalMapSizeException;

    public static boolean checkArrayDims(Fragment<?>[][] array, int2 dims)
    {
        if(array != null && dims != null)
        {
            if(array.length == dims.X() && array[0].length == dims.Y())
            {
                return true;
            }
        }
        return false;
    }
}
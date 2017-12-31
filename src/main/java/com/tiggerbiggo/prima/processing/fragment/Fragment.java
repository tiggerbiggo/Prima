package com.tiggerbiggo.prima.processing.fragment;

        import com.tiggerbiggo.prima.core.Vector2;
        import com.tiggerbiggo.prima.core.int2;
        import com.tiggerbiggo.prima.exception.IllegalMapSizeException;

public interface Fragment<T>
{
    public T get();
    public Fragment<T>[][] build(Vector2 dims) throws IllegalMapSizeException;

    public static boolean checkArrayDims(Fragment<?>[][] array, Vector2 dims)
    {
        if(array != null && dims != null)
        {
            if(array.length == dims.iX() && array[0].length == dims.iY())
            {
                return true;
            }
        }
        return false;
    }
}
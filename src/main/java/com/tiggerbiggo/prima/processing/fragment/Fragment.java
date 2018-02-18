package com.tiggerbiggo.prima.processing.fragment;

/**
 * The base interface for all fragments
 * @param <T> The type the fragment should return
 */
public interface Fragment<T>
{
    /**Get method, calculation should be done here and should return type T
     * @return The output of the fragment
     */
    T get(int x, int y, int w, int h, int num);
}
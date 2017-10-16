package com.tiggerbiggo.prima.graphics;

import java.awt.*;

public class GradientPoint
{
    public Color c;
    private float point;

    public void setPoint(float point)
    {
        this.point = Math.max(point, 0); // Ensures that point is always >=0
    }

    public float getPoint()
    {
        return point;
    }

    public GradientPoint(Color c, float point)
    {
        this.c=c;
        setPoint(point); //Call method to do validation
    }
}

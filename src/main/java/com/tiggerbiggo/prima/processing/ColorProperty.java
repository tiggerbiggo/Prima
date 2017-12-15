package com.tiggerbiggo.prima.processing;

import java.awt.*;

public enum ColorProperty
{
    H {
        @Override
        public float convert(Color c) {
            c.
            return 0;
        }
    },
    S,
    V,
    R,
    G,
    B;

    public abstract float convert(Color c);
}

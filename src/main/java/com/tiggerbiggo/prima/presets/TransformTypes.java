package com.tiggerbiggo.prima.presets;

import com.tiggerbiggo.prima.core.float2;
import com.tiggerbiggo.prima.processing.Transform;

public enum TransformTypes
{
    SINSIN{
        @Override
        public Transform getPreset() {
            return new Transform(){
                @Override
                public float2 doFormula(float2 in)
                {
                    float x, y;
                    x = in.getX();
                    y = in.getY();

                    y=(float)Math.sin(y)/(float)Math.PI;
                    x=(float)Math.sin(x)/(float)Math.PI;

                    return new float2(x, y);
                }
            };
        }
    },
    SINCOS{
        @Override
        public Transform getPreset() {
            return new Transform(){
                @Override
                public float2 doFormula(float2 in)
                {
                    float x, y;
                    x = in.getX();
                    y = in.getY();

                    y=(float)Math.sin(y);
                    x=(float)Math.cos(x);

                    return new float2(x, y);
                }
            };
        }
    },
    MAGNETISM{
        @Override
        public Transform getPreset() {
            return new Transform(){
                @Override
                public float2 doFormula(float2 in)
                {
                    float x, y;
                    x = in.getX();
                    y = in.getY();

                    y=(float)Math.sin(Math.cosh(x) * y);

                    return new float2(x, y);
                }
            };
        }
    },
    TANNY {
        @Override
        public Transform getPreset() {
            return new Transform(){
                @Override
                public float2 doFormula(float2 in)
                {
                    float x, y;
                    x = in.getX();
                    y = in.getY();

                    y=(float)Math.sin(Math.tanh(x)*Math.tan(y));

                    return new float2(x, y);
                }
            };
        }
    },
    OTHER {
        @Override
        public Transform getPreset() {
            return new Transform() {
                @Override
                public float2 doFormula(float2 in) {
                    float x, y;
                    x = in.getX();
                    y = in.getY();

                    if ((Math.abs(x) + Math.abs(y)) % 1 <= 0.5)
                    {
                        x *= -1;
                        y *=-1;
                    }
                    if((Math.abs(x) - Math.abs(y)) % 1 <= 0.5)
                    {
                        x *= -1;
                        y *=-1;
                    }

                    return new float2(x, y);
                }
            };
        }
    };

    public Transform getPreset(){return null;}
}

package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.calculation.ColorTools;
import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.graphics.SafeImage;
import com.tiggerbiggo.prima.processing.fragment.Fragment;

import java.awt.*;

public class ImageFadeFragment implements Fragment<Color[]> {
    private SafeImage[] imgs;
    private Fragment<Vector2[]> position, fade;


    @Override
    public Color[] get(int x, int y, int w, int h, int num) {
        Vector2[] positions, fadeAmounts;

        //read in arrays from animators
        positions = position.get(x, y, w, h, num);
        fadeAmounts = fade.get(x, y, w, h, num);

        //length of image array
        double imageArrayMaxIndex = imgs.length-1;

        //init color array
        Color[] toReturn = new Color[num];

        //for every frame
        for(int i=0; i<num; i++) {
            double percent = (double)i/num;
            int imageIndex = (int)(percent*imageArrayMaxIndex);

            Color a, b;

            a = imgs[imageIndex].getColor(positions[i]);

            //if the first sampled color is from the last image in the array,
            //we sample the second color from the first image.
            if(imageIndex == imageArrayMaxIndex) {
                b = imgs[0].getColor(positions[0]);
            } else{
                b = imgs[imageIndex+1].getColor(positions[i+1]);
            }

            toReturn[i] = ColorTools.colorLerp(a, b, fadeAmounts[imageIndex].magnitude());
        }
        return toReturn;
    }
}

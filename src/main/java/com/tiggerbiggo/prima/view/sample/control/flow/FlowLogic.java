package com.tiggerbiggo.prima.view.sample.control.flow;


import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import javafx.util.Pair;

import java.awt.*;
import java.util.HashMap;

class FlowLogic {
    public static int[][] doFlow(SafeImage image, SafeImage flow, Color thresholdCol, int threshAmount) throws IllegalArgumentException {
        System.out.println("Beginning Flow...");


        int w = image.getWidth();
        int h = image.getHeight();

        if (w != flow.getWidth() || h != flow.getHeight())
            throw new IllegalArgumentException("Error in FlowLogic.doFlow method, Images are not the same size.");

        System.out.println("W: " + w);
        System.out.println("H: " + h);
        int[][] toReturn = new int[w][h];

        HashMap<Integer, Pair<Integer, Integer>> points = new HashMap<>();

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (flow.getColor(i, j).equals(Color.BLACK)) {
                    points.put(i+(w*j), new Pair<>(i, j));
                    toReturn[i][j] = 1;
                }
            }
        }

        int step = 2;
        HashMap<Integer, Pair<Integer, Integer>> newPoints;
        do {
            newPoints = new HashMap<>();
            for (Pair<Integer, Integer> p : points.values()) {
                int fst = p.getKey();
                int snd = p.getValue();
                for (int i = fst - 1; i <= fst + 1; i++) {
                    for (int j = snd - 1; j <= snd + 1; j++) {
                        if (i < 0 || i >= w || j < 0 || j >= h || (i == fst && j == snd)) {
                            continue;
                        }
                        else {
                            if (toReturn[i][j] <= 0) {
                                if (ColorTools.absoluteDifference(image.getColor(i,j), thresholdCol) <= threshAmount) {

                                    if (!newPoints.containsKey(i+(w*j))) newPoints.put(i+(w*j), new Pair<>(i, j));
                                }
                            }
                        }
                    }
                }
            }

            for (Pair<Integer, Integer> p : points.values()) {
                toReturn[p.getKey()][p.getValue()] = step;
            }
            step++;

            points = newPoints;
        } while (!newPoints.isEmpty());


        return toReturn;
    }
}

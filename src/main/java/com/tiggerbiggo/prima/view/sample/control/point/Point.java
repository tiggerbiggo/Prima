package com.tiggerbiggo.prima.view.sample.control.point;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.view.sample.components.timeline.TimePoint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class Point extends TimePoint {
    private Vector2 rotatePoint;
    private int rotateNum;
    private boolean isSelected = false;
    private Color selectedCol = Color.BLUE;

    public Point(double x, double y, double width, double height) {
        super(x, y, width, height);

        rotateNum = 5;
        rotatePoint = new Vector2(.5,.5);
    }

    @Override
    public void draw(double width, double height, GraphicsContext g) {
        if(!isSelected)
            super.draw(width, height, g);//draw the regular point
        else
        {
            double x = getX(width);
            double y = getY(height);
            g.setFill(selectedCol);
            g.fillOval(x-RADIUS, y-RADIUS, DIAMETER, DIAMETER);
        }

        if(mouseOver){
            drawGhosts(width, height, g);
        }
    }

    private void drawGhosts(double width, double height, GraphicsContext g){
        Vector2 pos = new Vector2(time, value);
        for (int i = 0; i < rotateNum; i++) {
            Vector2 rotated = pos.rotateAround(rotatePoint, ((2*Math.PI)*i)/rotateNum);
            //take result and convert to real x, y

            rotated.set(rotated.X() * width, height - (height*rotated.Y()));

            g.setFill(getOverColor().interpolate(Color.TRANSPARENT, 0.6));
            g.fillOval(rotated.X()-RADIUS, rotated.Y()-RADIUS, DIAMETER, DIAMETER);
        }
    }

    public void setSelected(boolean val){
        isSelected = val;
    }

    public List<Vector2> expandPoints() {
        Vector2 pos = new Vector2(time, value);
        if (rotateNum <= 1) return Collections.singletonList(pos);

        List<Vector2> toReturn = new ArrayList<>();

        for (int i = 0; i < rotateNum; i++) {
            toReturn.add(pos.rotateAround(rotatePoint, ((2 * Math.PI) * i) / rotateNum));
        }

        return toReturn;
    }

    @Override
    public String toString() {
        return "Point [" + hashCode() + "]";
    }

    public Vector2 getRotatePoint() {
        return rotatePoint;
    }

    public int getRotateNum() {
        return rotateNum;
    }

    public void setRotatePoint(Vector2 rotatePoint) {
        this.rotatePoint = rotatePoint;
    }

    public void setRotateNum(int rotateNum) {
        this.rotateNum = rotateNum;
    }
}

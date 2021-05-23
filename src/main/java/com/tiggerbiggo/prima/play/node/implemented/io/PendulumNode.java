package com.tiggerbiggo.prima.play.node.implemented.io;

import com.tiggerbiggo.prima.play.core.FileManager;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderCache;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.NumberInputLink;
import com.tiggerbiggo.prima.play.node.link.type.PointInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PendulumNode extends NodeInOut {

    VectorInputLink initialPosition = new VectorInputLink("Initial Position");
    VectorInputLink initialVelocity = new VectorInputLink("Initial Velocity");
    PointInputLink points = new PointInputLink("Attraction Points");
    NumberInputLink attractScale = new NumberInputLink("Attraction Scale");
    NumberInputLink drag = new NumberInputLink("Drag");

    VectorArrayOutputLink outPosition;
    VectorArrayOutputLink outVelocity;

    public PendulumNode(){
        outPosition = new VectorArrayOutputLink("Position output") {
            @Override
            public Vector2[] get(RenderParams p) {
                Pendulum pendulum = new Pendulum(initialVelocity.get(p), initialPosition.get(p));
                Vector2[] posList = new Vector2[p.frameNum()];
                Vector2[] attraction = points.get(p);
                Float scale = attractScale.get(p);
                Float dragF = drag.get(p);
                posList[0] = pendulum.position;
                for(int i=1; i<p.frameNum(); i++){
                    pendulum.attractStep(attraction, scale, dragF);
                    posList[i] = pendulum.position;
                }

                return posList;
            }

            @Override
            public void generateGLSLMethod(StringBuilder s) {
                throw new NotImplementedException();
            }

            @Override
            public String getMethodName() {
                return null;
            }
        };
        outVelocity = new VectorArrayOutputLink("Velocity output") {
            @Override
            public Vector2[] get(RenderParams p) {
                Pendulum pendulum = new Pendulum(initialVelocity.get(p), initialPosition.get(p));
                Vector2[] velList = new Vector2[p.frameNum()];
                Vector2[] attraction = points.get(p);
                Float scale = attractScale.get(p);
                Float dragF = drag.get(p);
                velList[0] = pendulum.velocity;
                for(int i=1; i<p.frameNum(); i++){
                    pendulum.attractStep(attraction, scale, dragF);
                    velList[i] = pendulum.velocity;
                }

                return velList;
            }

            @Override
            public void generateGLSLMethod(StringBuilder s) {
                throw new NotImplementedException();
            }

            @Override
            public String getMethodName() {
                return null;
            }
        };

        addOutput(outPosition, outVelocity);
        addInput(initialPosition, initialVelocity, points, attractScale, drag);
    }

    @Override
    public String getName() {
        return "Pendulum Node";
    }

    @Override
    public String getDescription() {
        return "Creates a Pendulum system";
    }


}


class Pendulum{

    public Vector2 velocity;
    public Vector2 position;

    public Pendulum(Vector2 velocity, Vector2 position){
        this.velocity = velocity;
        this.position = position;
    }

    public Pendulum(){
        this(Vector2.ZERO, Vector2.ZERO);
    }

    public void attractStep(Vector2[] points, double attractScale, double drag){
        Vector2 attraction = Vector2.ZERO;
        for(Vector2 point : points){
            //calculate distance
            double dist = point.distanceBetween(position);
            //add to attraction
            attraction = attraction.add(point.subtract(position).multiply(1/dist).multiply(attractScale));
        }
        velocity = velocity.add(attraction);
        velocity = velocity.multiply(drag);
        position = position.add(velocity);
    }
}

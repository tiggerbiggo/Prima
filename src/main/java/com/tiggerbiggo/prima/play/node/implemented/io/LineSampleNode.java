package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.SafeImage;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;

public class LineSampleNode extends NodeInOut {

    @TransferGrid
    Vector2 softPosition = Vector2.ZERO;
    @TransferGrid
    double softRotation = 0;

    ImageInputLink imgIn;
    VectorArrayInputLink pos;
    VectorArrayInputLink in;
    ColorArrayOutputLink out;
    NumberArrayInputLink rot;

    public LineSampleNode(){
        imgIn = new ImageInputLink("Image");

        pos = new VectorArrayInputLink("Sample Position");
        rot = new NumberArrayInputLink("Rotation");
        in = new VectorArrayInputLink("Main Input");

        addInput(imgIn, pos, rot, in);

        out = new ColorArrayOutputLink("Output") {
            @Override
            public Color[] get(RenderParams p) {
                SafeImage img = imgIn.get(p);

                Vector2[] position;
                if(pos.isLinked()){
                    position = pos.get(p);
                }
                else position = Vector2.makeArray(p.frameNum(), softPosition);

                double[] rotation;
                if(rot.isLinked()){
                    rotation = Calculation.fromDoubleArray(rot.get(p));
                }
                else rotation = Calculation.makeArray(p.frameNum(), softRotation);

                Color[] toReturn = new Color[p.frameNum()];
                Vector2[] got = in.get(p);

                for(int i=0; i<p.frameNum(); i++){
                    Vector2 transformed = position[i].add(Vector2.RIGHT.multiply(got[i].xy()));
                    transformed = transformed.rotateAround(position[i], rotation[i]);
                    toReturn[i] = img.getColor(img.denormVector(transformed.abs().mod(1)));
                }

                return toReturn;
            }

            @Override
            public void generateGLSLMethod(StringBuilder s) {
                throw new NotImplementedException();
            }

            @Override
            public String getMethodName() {
                throw new NotImplementedException();
            }
        };

        addOutput(out);
    }

    @Override
    public String getName() {
        return "Line Sample Node";
    }

    @Override
    public String getDescription() {
        return "Samples a loop of colour similar to Gradient Node, however uses an image as the sampler";
    }
}

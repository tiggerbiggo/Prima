package com.tiggerbiggo.prima.play.node.implemented.io;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.NumberArrayInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class VCANode extends NodeInOut {

    private VectorInputLink Av, Bv;
    private NumberArrayInputLink An, Bn;

    private VectorArrayOutputLink out;

    public VCANode() {
        Av = new VectorInputLink("In A");
        Bv = new VectorInputLink("In B");

        An = new NumberArrayInputLink("Amplitude A");
        Bn = new NumberArrayInputLink("Amplitude B");

        addInput(Av, An, Bv, Bn);

        out = new VectorArrayOutputLink("Out") {
            @Override
            public Vector2[] get(RenderParams p) {
                Vector2[] toReturn = new Vector2[p.frameNum()];

                Vector2 A = Av.get(p);
                Vector2 B = Bv.get(p);

                Double[] numA = An.get(p);
                Double[] numB = Bn.get(p);

                for (int i = 0; i < p.frameNum(); i++) {
                    toReturn[i] = A.multiply(numA[i]).add(B.multiply(numB[i]));
                }

                return toReturn;
            }

            @Override
            public void generateGLSLMethod(StringBuilder s) {
                //TODO
                throw new NotImplementedException();
            }

            @Override
            public String getMethodName() {
                //TODO
                throw new NotImplementedException();
            }
        };

        addOutput(out);
    }

    @Override
    public String getName() {
        return "VCA Node";
    }

    @Override
    public String getDescription() {
        return "Takes in multiple inputs, and mixes them together using the given number inputs as amplitudes";
    }
}
